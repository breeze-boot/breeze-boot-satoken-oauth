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

package com.breeze.boot.mybatis.events;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 本地日志保存事件
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
public class SysAuditLogSaveEvent extends ApplicationEvent {

    /**
     * 系统审计日志保存事件
     *
     * @param auditMap 系统审计日志BO
     */
    public SysAuditLogSaveEvent(Map<String, Map<String, Object>> auditMap) {
        super(auditMap);
    }

}
