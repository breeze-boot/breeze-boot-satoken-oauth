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

package com.breeze.boot.quartz.manager;

import com.breeze.boot.quartz.conf.AllowConcurrentExecutionJob;
import com.breeze.boot.quartz.conf.BreezeQuartzJobListener;
import com.breeze.boot.quartz.conf.DisallowConcurrentExecutionJob;
import com.breeze.boot.quartz.domain.entity.SysQuartzJob;
import com.breeze.boot.quartz.enums.QuartzEnum;
import com.breeze.boot.quartz.service.SysQuartzJobLogService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.breeze.boot.quartz.enums.QuartzEnum.*;

/**
 * quartz经理
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QuartzManager {

    private final SysQuartzJobLogService quartzJobLogService;
    private final Scheduler scheduler;

    /**
     * 根据类名获取QuartzJobBean的Class对象
     *
     * @param classname 类名
     * @return QuartzJobBean的Class对象
     * @throws ClassNotFoundException 如果类未找到
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends QuartzJobBean> getClass(String classname) throws ClassNotFoundException {
        return (Class<? extends QuartzJobBean>) Class.forName(classname);
    }

    /**
     * 根据并发标志获取QuartzJobBean的Class对象
     *
     * @param concurrent 并发标志
     * @return QuartzJobBean的Class对象
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends QuartzJobBean> getClass(Integer concurrent) {
        return concurrent == 1 ? AllowConcurrentExecutionJob.class : DisallowConcurrentExecutionJob.class;
    }

    /**
     * 添加或更新任务
     *
     * @param quartzJob quartz任务
     */
    public void addOrUpdateJob(SysQuartzJob quartzJob) {
        try {
            if (isJobExists(quartzJob)) {
                this.updateJob(quartzJob);
            } else {
                this.addJob(quartzJob);
            }
        } catch (ClassNotFoundException e) {
            log.error("任务类未找到", e);
        } catch (SchedulerException e) {
            log.error("调度器异常", e);
        }
    }

    /**
     * 添加任务
     *
     * @param quartzJob quartz任务
     * @throws ClassNotFoundException 如果任务类未找到
     * @throws SchedulerException     如果调度器出现异常
     */
    private void addJob(SysQuartzJob quartzJob) throws ClassNotFoundException, SchedulerException {
        Class<? extends QuartzJobBean> jobClass = getClass(quartzJob.getConcurrent());
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_DATA_KEY, quartzJob);
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName()).usingJobData(jobDataMap).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getId() + ":" + TRIGGER_NAME, quartzJob.getJobGroupName()).withSchedule(getScheduleBuilder(quartzJob)).build();

        BreezeQuartzJobListener listener = new BreezeQuartzJobListener(quartzJobLogService);
        scheduler.getListenerManager().addJobListener(listener);

        scheduler.scheduleJob(jobDetail, trigger);
        handleJobStatus(quartzJob, jobDetail.getKey());
    }

    /**
     * 更新任务
     *
     * @param quartzJob quartz任务
     * @throws SchedulerException 如果调度器出现异常
     */
    private void updateJob(SysQuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        this.scheduler.deleteJob(jobKey);
        try {
            this.addJob(quartzJob);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查任务是否存在
     *
     * @param quartzJob quartz任务
     * @return 如果任务存在返回true，否则返回false
     * @throws SchedulerException 如果调度器出现异常
     */
    private boolean isJobExists(SysQuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return scheduler.checkExists(jobKey);
    }

    /**
     * 处理任务状态
     *
     * @param quartzJob quartz任务
     * @param jobKey    任务键
     * @throws SchedulerException 如果调度器出现异常
     */
    private void handleJobStatus(SysQuartzJob quartzJob, JobKey jobKey) throws SchedulerException {
        if (Objects.equals(quartzJob.getStatus(), QuartzEnum.Status.PAUSE.getValue())) {
            scheduler.pauseJob(jobKey);
        } else {
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        }
    }

    /**
     * 根据任务的misfire策略获取CronScheduleBuilder
     *
     * @param quartzJob quartz任务
     * @return CronScheduleBuilder
     */
    private CronScheduleBuilder getScheduleBuilder(SysQuartzJob quartzJob) {
        MisfirePolicy misfirePolicy = getMisfirePolicy(quartzJob.getMisfirePolicy());
        return misfirePolicy.getScheduleBuilder(quartzJob.getCronExpression());
    }

    /**
     * 根据misfire策略代码获取MisfirePolicy枚举
     *
     * @param misfirePolicyCode misfire策略代码
     * @return MisfirePolicy枚举
     */
    private MisfirePolicy getMisfirePolicy(Integer misfirePolicyCode) {
        for (MisfirePolicy policy : MisfirePolicy.values()) {
            if (Objects.equals(policy.getCode(), misfirePolicyCode)) {
                return policy;
            }
        }
        throw new RuntimeException("策略不存在");
    }

    /**
     * 删除任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("删除任务失败", e);
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("暂停任务失败", e);
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("恢复任务失败", e);
        }
    }

    /**
     * 立即运行任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void runJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("运行任务失败", e);
        }
    }

    /**
     * 查询所有任务
     *
     * @return 任务列表
     */
    public List<Map<String, Object>> listAllJob() {
        List<Map<String, Object>> jobList = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeyList = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeyList) {
                List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggerList) {
                    Map<String, Object> jobDetailMaps = Maps.newHashMap();
                    if (trigger instanceof CronTrigger cronTrigger) {
                        jobDetailMaps.put("cronExpression", cronTrigger.getCronExpression());
                        jobDetailMaps.put("timeZone", cronTrigger.getTimeZone().getDisplayName());
                    }
                    jobDetailMaps.put("TriggerGroupName", trigger.getKey().getName());
                    jobDetailMaps.put("TriggerName", trigger.getKey().getGroup());
                    jobDetailMaps.put("JobGroupName", jobKey.getGroup());
                    jobDetailMaps.put("JobName", jobKey.getName());
                    jobDetailMaps.put("StartTime", trigger.getStartTime());
                    jobDetailMaps.put("JobClassName", scheduler.getJobDetail(jobKey).getJobClass().getName());
                    jobDetailMaps.put("NextFireTime", trigger.getNextFireTime());
                    jobDetailMaps.put("PreviousFireTime", trigger.getPreviousFireTime());
                    jobDetailMaps.put("Status", scheduler.getTriggerState(trigger.getKey()).name());
                    jobList.add(jobDetailMaps);
                }
            }
        } catch (SchedulerException e) {
            log.error("查询任务失败", e);
        }
        return jobList;
    }

}