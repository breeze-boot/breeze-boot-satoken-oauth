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
import com.breeze.boot.quartz.domain.entity.SysQuartzJobLog;
import com.breeze.boot.quartz.mapper.SysQuartzJobLogMapper;
import com.breeze.boot.quartz.domain.query.JobQuery;
import com.breeze.boot.quartz.service.SysQuartzJobLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Quartz定时任务日志服务impl
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Service
public class SysQuartzJobLogServiceImpl extends ServiceImpl<SysQuartzJobLogMapper, SysQuartzJobLog> implements SysQuartzJobLogService {

    /**
     * 列表页面
     *
     * @param query 任务查询
     * @return {@link Page}<{@link SysQuartzJobLog}>
     */
    @Override
    public Page<SysQuartzJobLog> listPage(JobQuery query) {
        return this.baseMapper.listPage(new Page<>(query.getCurrent(), query.getSize()), query);
    }

    /**
     * 删除日志
     *
     * @param jobIds 日志Ids
     * @return boolean
     */
    @Override
    public boolean deleteLogs(List<Long> jobIds) {
        return this.removeBatchByIds(jobIds);
    }

    /**
     * 清空
     */
    @Override
    public void truncate() {
        this.baseMapper.truncate();
    }
}




