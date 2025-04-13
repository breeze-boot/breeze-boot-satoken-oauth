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

package com.breeze.boot.config;

import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.events.LocalSysLogSaveEventListener;
import com.breeze.boot.modules.system.service.SysAuditLogService;
import com.breeze.boot.modules.system.service.SysMsgUserService;
import com.breeze.boot.modules.system.service.SysLogService;
import com.breeze.boot.message.dto.UserMsgDTO;
import com.breeze.boot.message.events.MsgSaveEventListener;
import com.breeze.boot.mybatis.events.LocalSysAuditLogSaveEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 系统日志收集的配置
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Configuration
@RequiredArgsConstructor
public class ListenerConfiguration {

    /**
     * 系统日志服务
     */
    private final SysAuditLogService sysAuditLogService;

    /**
     * 系统日志服务
     */
    private final SysLogService sysLogService;

    /**
     * 用户消息服务
     */
    private final SysMsgUserService sysMsgUserService;

    /**
     * 日志保存侦听器
     *
     * @return {@link LocalSysLogSaveEventListener}
     */
    @Bean
    public LocalSysLogSaveEventListener logSaveEventListener() {
        return new LocalSysLogSaveEventListener((source) -> this.sysLogService.saveSysLog((SysLogBO) source.getSource()));
    }

    /**
     * 消息快照保存侦听器
     *
     * @return {@link MsgSaveEventListener}
     */
    @Bean
    public MsgSaveEventListener msgSaveMsgSnappedEventListener() {
        return new MsgSaveEventListener((source) -> this.sysMsgUserService.saveUserMsg((UserMsgDTO) source.getSource()));
    }


    /**
     * 审计日志消息快照保存侦听器
     *
     * @return {@link MsgSaveEventListener}
     */
    @Bean
    public LocalSysAuditLogSaveEventListener localSysAuditLogSaveEventListener() {
        return new LocalSysAuditLogSaveEventListener((source) -> this.sysAuditLogService.saveAuditLog((Map<String,Map<String,Object>>) source.getSource()));
    }

}
