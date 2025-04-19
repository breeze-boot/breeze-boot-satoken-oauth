/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.quartz.conf;

import cn.hutool.core.date.DateUtil;
import com.breeze.boot.quartz.domain.entity.SysQuartzJob;
import com.breeze.boot.quartz.domain.entity.SysQuartzJobLog;
import com.breeze.boot.quartz.enums.QuartzEnum;
import com.breeze.boot.quartz.service.SysQuartzJobLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * 监听器
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Slf4j
@RequiredArgsConstructor
public class BreezeQuartzJobListener implements JobListener {

    /**
     * 日志线程本地
     */
    private static final ThreadLocal<SysQuartzJobLog> logThreadLocal = ThreadLocal.withInitial(SysQuartzJobLog::new);

    /**
     * 任务日志服务
     */
    private final SysQuartzJobLogService quartzJobLogService;

    /**
     * 将异常信息转化成字符串
     *
     * @param t t
     * @return {@link String}
     */
    private static String exception(Throwable t) {
        if (t == null) {
            return null;
        }
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(stream)) {
            t.printStackTrace(printStream);
            return stream.toString();
        } catch (IOException e) {
            log.error("任务日志异常信息转换异常", e);
            return null;
        }
    }

    /**
     * @return JobListener 名称
     */
    @Override
    public String getName() {
        return "BREEZE_QUARTZ_JOB_LISTENER";
    }

    /**
     * 工作执行
     *
     * @param context 上下文
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        log.info("BreezeQuartzJobListener says :  {} Job Is about to be executed.", jobDetail.getKey());
        SysQuartzJobLog quartzRunningLog = SysQuartzJobLog
                .builder()
                .createTime(LocalDateTime.now())
                .build();
        logThreadLocal.set(quartzRunningLog);
    }

    /**
     * 作业执行否决了
     *
     * @param context 上下文
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        try {
            JobDetail jobDetail = context.getJobDetail();
            log.info("BreezeQuartzJobListener says :  {} Job Execution was vetoed.", jobDetail.getKey());
        } finally {
            logThreadLocal.remove();
        }
    }

    /**
     * 工作被执行
     *
     * @param context      上下文
     * @param jobException 工作异常
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
            JobDetail jobDetail = context.getJobDetail();
            log.info("BreezeQuartzJobListener says: {} Job was executed.", jobDetail.getKey());
            JobKey jobKey = jobDetail.getKey();
            TriggerKey triggerKey = context.getTrigger().getKey();
            Date fireTime = context.getFireTime();
            Class<? extends Job> jobClass = jobDetail.getJobClass();
            log.info("JobClass:{}, Job:{}, Trigger:{}, FireTime:{}", jobClass, jobKey, triggerKey, DateUtil.formatDateTime(fireTime));
            SysQuartzJob quartzJob = (SysQuartzJob) context.getMergedJobDataMap().get(QuartzEnum.JOB_DATA_KEY);
            SysQuartzJobLog quartzJobLog = logThreadLocal.get();
            quartzJobLog.setJobId(quartzJob.getId());
            quartzJobLog.setJobName(quartzJob.getJobName());
            quartzJobLog.setCronExpression(quartzJob.getCronExpression());
            quartzJobLog.setClazzName(quartzJob.getClazzName());
            quartzJobLog.setJobGroupName(quartzJob.getJobGroupName());

            boolean isSuccess = Objects.isNull(jobException);
            saveJobLog(quartzJobLog, isSuccess, jobException);
        } catch (Exception e) {
            log.error("记录任务执行日志时发生异常", e);
        } finally {
            log.info("释放 => logThreadLocal");
            logThreadLocal.remove();
        }
    }

    /**
     * 保存任务执行日志
     *
     * @param quartzJobLog 任务日志实体
     * @param isSuccess    是否执行成功
     * @param jobException 任务异常
     */
    private void saveJobLog(SysQuartzJobLog quartzJobLog, boolean isSuccess, JobExecutionException jobException) {
        quartzJobLog.setJobResult(isSuccess ? 1 : 0);
        quartzJobLog.setEndTime(LocalDateTime.now());
        long millis = quartzJobLog.getCreateTime().until(quartzJobLog.getEndTime(), ChronoUnit.MILLIS);
        log.info("花费时间: {} - {} = {}", quartzJobLog.getCreateTime(), quartzJobLog.getEndTime(), millis);
        quartzJobLog.setJobMessage("运行时间 " + millis + " 毫秒 ");

        if (!isSuccess) {
            quartzJobLog.setExceptionInfo(exception(jobException));
        }

        try {
            quartzJobLogService.save(quartzJobLog);
        } catch (Exception e) {
            log.error("保存任务执行日志失败", e);
        }
    }
}    