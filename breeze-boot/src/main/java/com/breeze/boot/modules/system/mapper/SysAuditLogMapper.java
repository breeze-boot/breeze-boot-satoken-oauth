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

package com.breeze.boot.modules.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.modules.system.model.entity.SysAuditLog;
import com.breeze.boot.modules.system.model.query.AuditLogQuery;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统审计日志映射器
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
@Mapper
public interface SysAuditLogMapper extends BreezeBaseMapper<SysAuditLog> {

    /**
     * 列表页面
     *
     * @param page          页面
     * @param auditLogQuery 审计日志查询
     * @return {@link Page }<{@link SysAuditLog }>
     */
    @DymicSql
    Page<SysAuditLog> listPage(@Param("page") Page<SysAuditLog> page, @ConditionParam @Param("auditLogQuery") AuditLogQuery auditLogQuery);

    void truncate();

}
