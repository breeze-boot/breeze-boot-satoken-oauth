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

package com.breeze.boot.quartz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.quartz.CronScheduleBuilder;

/**
 * Quartz常量
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
public class QuartzEnum {

    /**
     * 工作数据关键
     */
    public static final String JOB_DATA_KEY = "JOB_DATA";

    /**
     * 任务名
     */
    public static final String JOB_NAME = "JOB_NAME";

    /**
     * 触发器名字
     */
    public static final String TRIGGER_NAME = "TRIGGER_NAME";

    /**
     * * 失败策略
     * * <p>
     * * 1 立即执行 2 执行一次 3 放弃
     * *
     * * @author gaoweixuan
     * * @since 2023-03-16
     */
    @Getter
    public enum MisfirePolicy {

        /**
         * 以错过的第一个频率时间立刻开始执行
         * 重做错过的所有频率周期后
         * 当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
         */
        IGNORE_MISFIRE(-1) {
            @Override
            public CronScheduleBuilder getScheduleBuilder(String cronExpression) {
                return CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionIgnoreMisfires();
            }
        },

        /**
         * 以当前时间为触发频率立刻触发一次执行
         * 然后按照Cron频率依次执行
         */
        FIRE_AND_PROCEED(1) {
            @Override
            public CronScheduleBuilder getScheduleBuilder(String cronExpression) {
                return CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionFireAndProceed();
            }
        },
        /**
         * 不触发立即执行
         * 等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
         */
        DO_NOTHING(2) {
            @Override
            public CronScheduleBuilder getScheduleBuilder(String cronExpression) {
                return CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            }
        };

        private final int code;

        MisfirePolicy(int code) {
            this.code = code;
        }

        public abstract CronScheduleBuilder getScheduleBuilder(String cronExpression);
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {

        /**
         * 开启
         */
        START(1, "开启"),
        /**
         * 暂停
         */
        PAUSE(0, "暂停");

        /**
         * 状态
         */
        private final Integer value;

        /**
         * 描述
         */
        private final String label;

    }

}
