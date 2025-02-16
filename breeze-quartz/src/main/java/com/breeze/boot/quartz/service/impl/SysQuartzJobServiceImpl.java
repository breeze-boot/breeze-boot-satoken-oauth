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

package com.breeze.boot.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.domain.form.JobOpenForm;
import com.breeze.boot.quartz.domain.form.SysQuartzJobForm;
import com.breeze.boot.quartz.domain.mappers.SysQuartzJobMapStruct;
import com.breeze.boot.quartz.domain.query.JobQuery;
import com.breeze.boot.quartz.manager.QuartzManager;
import com.breeze.boot.quartz.mapper.SysQuartzJobMapper;
import com.breeze.boot.quartz.service.SysQuartzJobService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.breeze.boot.core.constants.QuartzConstants.JOB_NAME;
import static com.breeze.boot.core.enums.ResultCode.JOB_NOT_FOUND;

/**
 * Quartz任务调度Impl
 *
 * @author gaoweixuan
 */
@Service
@RequiredArgsConstructor
public class SysQuartzJobServiceImpl extends ServiceImpl<SysQuartzJobMapper, SysQuartzJob> implements SysQuartzJobService {

    /**
     * quartz 管理器
     */
    private final QuartzManager quartzManager;

    private final SysQuartzJobMapStruct sysQuartzJobMapStruct;

    /**
     * 负载任务
     */
    @PostConstruct
    private void loadJobs() {
        List<SysQuartzJob> quartzJobs = this.list();
        for (SysQuartzJob quartzJob : quartzJobs) {
            this.quartzManager.addOrUpdateJob(quartzJob);
        }
    }

    /**
     * 列表页面
     *
     * @param jobQuery 任务查询
     * @return {@link Page}<{@link SysQuartzJob}>
     */
    @Override
    public Page<SysQuartzJob> listPage(JobQuery jobQuery) {
        return this.baseMapper.listPage(new Page<>(jobQuery.getCurrent(), jobQuery.getSize()), jobQuery);
    }

    /**
     * 保存任务
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveJob(SysQuartzJob sysQuartzJob) {
        sysQuartzJob.insert();
        this.quartzManager.addOrUpdateJob(sysQuartzJob);
        return Result.ok(Boolean.TRUE, "保存成功");
    }

    /**
     * 更新任务通过id
     *
     * @param id            ID
     * @param quartzJobForm quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> modifyJob(Long id, SysQuartzJobForm quartzJobForm) {
        SysQuartzJob sysQuartzJob = sysQuartzJobMapStruct.form2Entity(quartzJobForm);
        sysQuartzJob.setId(id);
        AssertUtil.isNotNull(this.getById(id), JOB_NOT_FOUND);
        sysQuartzJob.updateById();
        this.quartzManager.addOrUpdateJob(sysQuartzJob);
        return Result.ok(Boolean.TRUE, "修改成功");
    }

    /**
     * 暂停任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> pauseJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        AssertUtil.isNotNull(quartzJob, JOB_NOT_FOUND);

        quartzJob.setStatus(0);
        quartzJob.updateById();
        this.quartzManager.pauseJob(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE, "暂停成功");
    }

    /**
     * 恢复任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> resumeJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        AssertUtil.isNotNull(quartzJob, JOB_NOT_FOUND);

        quartzJob.setStatus(1);
        quartzJob.updateById();
        this.quartzManager.resumeJob(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE, "开启成功");
    }

    /**
     * 删除任务
     *
     * @param jobIds 任务ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteJob(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            SysQuartzJob quartzJob = this.getById(jobId);
            if (Objects.isNull(quartzJob)) {
                continue;
            }
            quartzJob.deleteById();
            this.quartzManager.deleteJob(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        }
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 立刻运行
     *
     * @param jobId 任务ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> runJobNow(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        this.quartzManager.runJobNow(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE, "运行成功");
    }

    /**
     * 打开
     *
     * @param jobOpenForm Job开启表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    public Result<Boolean> open(JobOpenForm jobOpenForm) {
        try {
            return Objects.equals(1, jobOpenForm.getStatus()) ? this.resumeJob(jobOpenForm.getId()) : this.pauseJob(jobOpenForm.getId());
        } catch (Exception e) {
            log.error("任务状态修改失败", e);
        }
        throw new BreezeBizException(ResultCode.FAIL);
    }

}




