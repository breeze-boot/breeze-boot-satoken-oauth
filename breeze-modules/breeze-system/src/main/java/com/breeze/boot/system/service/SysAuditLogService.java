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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.model.entity.SysAuditLog;
import com.breeze.boot.system.model.query.AuditLogQuery;
import com.breeze.boot.system.model.vo.AuditLogVO;

import java.util.Map;

/**
 * 系统审计日志服务
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
public interface SysAuditLogService extends IService<SysAuditLog> {

    /**
     * 列表页面
     *
     * @param query 审计查询
     * @return {@link Page }<{@link AuditLogVO }>
     */
    Page<AuditLogVO> listPage(AuditLogQuery query);

    /**
     * 保存审核日志
     *
     * @param source 来源
     */
    void saveAuditLog(Map<String, Map<String, Object>> source);

    /**
     * 按id获取信息
     *
     * @param auditId 审核id
     * @return {@link AuditLogVO }
     */
    AuditLogVO getInfoById(Long auditId);

    /**
     * 清空
     */
    void truncate();

}
