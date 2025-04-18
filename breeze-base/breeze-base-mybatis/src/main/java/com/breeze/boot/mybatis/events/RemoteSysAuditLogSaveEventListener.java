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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * 调用接口保存系统日志保存事件监听器
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
@Slf4j
@RequiredArgsConstructor
public class RemoteSysAuditLogSaveEventListener {

    /**
     * 消费者
     * <p>
     * 去执行保存逻辑
     */
    private Consumer<SysAuditLogSaveEvent> consumer;

    /**
     * 应用程序事件
     *
     * @param sysLogSaveEvent 事件
     */
    @Async
    @EventListener(SysAuditLogSaveEvent.class)
    public void onApplicationEvent(SysAuditLogSaveEvent sysLogSaveEvent) {
        log.info("远程接口审计日志消息投递保存");
        consumer.accept(sysLogSaveEvent);
    }

}
