/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.mapper.SysAuditLogMapper;
import com.breeze.boot.system.model.converter.SysAuditLogConverter;
import com.breeze.boot.system.model.entity.SysAuditLog;
import com.breeze.boot.system.model.query.AuditLogQuery;
import com.breeze.boot.system.model.vo.AuditLogVO;
import com.breeze.boot.system.service.SysAuditLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统审计日志服务impl
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
@Service
@RequiredArgsConstructor
public class SysAuditLogServiceImpl extends ServiceImpl<SysAuditLogMapper, SysAuditLog> implements SysAuditLogService {

    private final SysAuditLogConverter sysAuditLogConverter;

    /**
     * 列表页面
     *
     * @param query 审计日志查询
     * @return {@link Page }<{@link AuditLogVO }>
     */
    @Override
    public Page<AuditLogVO> listPage(AuditLogQuery query) {
        Page<SysAuditLog> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysAuditLog> sysAuditLogPage = this.baseMapper.listPage(page, query);
        return this.sysAuditLogConverter.entityPage2VOPage(sysAuditLogPage);
    }

    /**
     * 保存审核日志
     *
     * @param source 来源
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAuditLog(Map<String, Map<String, Object>> source) {
        List<SysAuditLog> auditList = Lists.newArrayList();
        source.forEach((field, difMap) -> {
            SysAuditLog sysAudit = new SysAuditLog();
            sysAudit.setField(field);
            Object old = difMap.get("old");
            sysAudit.setPrevious(Objects.nonNull(old) ? old.toString() : null);
            Object aNew = difMap.get("new");
            sysAudit.setNow(Objects.nonNull(aNew) ? aNew.toString() : null);
            sysAudit.setTime(LocalDateTime.now());
            sysAudit.setBatch(RandomUtil.randomString(10));
            auditList.add(sysAudit);
        });
        this.saveBatch(auditList);
    }

    /**
     * 按id获取信息
     *
     * @param auditLogId 审计日志ID
     * @return {@link AuditLogVO }
     */
    @Override
    public AuditLogVO getInfoById(Long auditLogId) {
        return this.sysAuditLogConverter.entity2VO(this.getById(auditLogId));
    }

    @Override
    public void truncate() {
        this.baseMapper.truncate();
    }

}
