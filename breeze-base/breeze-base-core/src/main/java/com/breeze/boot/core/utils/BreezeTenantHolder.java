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

package com.breeze.boot.core.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程本地
 *
 * @author gaoweixuan
 * @since 2022-11-08
 */
@Slf4j
public class BreezeTenantHolder {

    private static final TransmittableThreadLocal<Long> threadLocal = new TransmittableThreadLocal<>();

    public static Long getTenant() {
        log.info(Thread.currentThread().getName());
        return threadLocal.get();
    }

    public static void clean() {
        log.info(Thread.currentThread().getName());
        threadLocal.remove();
    }

    public static void setTenant(Long value) {
        log.info(Thread.currentThread().getName());
        threadLocal.set(value);
    }

}
