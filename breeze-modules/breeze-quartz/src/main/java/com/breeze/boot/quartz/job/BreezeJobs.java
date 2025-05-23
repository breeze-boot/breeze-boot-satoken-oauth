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

package com.breeze.boot.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 任务使用案例
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Slf4j
@Component
public class BreezeJobs {

    public void demoJob(String s) {
        log.info("String类型的值 {}", s);
        log.info("Job当前执行时间: " + LocalDateTime.now());
    }

    public void demoExceptionJob(String s) {
        log.info("String类型的值 {}", s);
        int i = 1 / 0;
        log.info("Job当前执行时间: " + LocalDateTime.now());
    }

    public void demoJob(String s, Integer integer, Double aDouble, Long aLong, Boolean aBoolean, Boolean aBoolean1) {
        log.info("String类型的值: {}", s);
        log.info("Integer类型的值: {}", integer);
        log.info("Double类型的值: {}", aDouble);
        log.info("Long类型的值:{}", aLong);
        log.info("Boolean类型的值{} {}", aBoolean, aBoolean1);
        log.info("Job当前执行时间: " + LocalDateTime.now());
    }
}
