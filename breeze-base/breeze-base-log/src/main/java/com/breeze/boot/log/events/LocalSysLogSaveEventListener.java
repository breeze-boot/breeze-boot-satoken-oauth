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

package com.breeze.boot.log.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * 系统日志保存事件监听器
 *
 * @author gaoweixuan
 * @since 2022-10-19
 */
@Slf4j
@RequiredArgsConstructor
public class LocalSysLogSaveEventListener {

    /**
     * 消费者
     * <p>
     * 去执行保存逻辑
     */
    private final Consumer<SysLogSaveEvent> consumer;

    /**
     * 应用程序事件
     *
     * @param sysLogSaveEvent 事件
     */
    @Async
    @EventListener(SysLogSaveEvent.class)
    public void onApplicationEvent(SysLogSaveEvent sysLogSaveEvent) {
        log.debug("本地日志消息投递保存");
        consumer.accept(sysLogSaveEvent);
    }

}
