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

package com.breeze.boot.quartz.conf;

import cn.hutool.extra.spring.SpringUtil;
import com.breeze.boot.quartz.domain.entity.SysQuartzJob;
import com.breeze.boot.quartz.enums.QuartzEnum;
import com.breeze.boot.quartz.utils.JobInvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 抽象的定时任务执行类，包含公共的任务执行逻辑
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Slf4j
public abstract class AbstractQuartzJob extends QuartzJobBean {

    /**
     * 执行定时任务的公共逻辑
     *
     * @param context JobExecutionContext
     */
    protected void executeJob(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        SysQuartzJob quartzJob = (SysQuartzJob) jobDataMap.get(QuartzEnum.JOB_DATA_KEY);
        String clazzName = quartzJob.getClazzName();
        String beanName = JobInvokeUtils.getBeanName(clazzName);
        String methodName = JobInvokeUtils.getMethodName(clazzName, ".", "(");
        String params = JobInvokeUtils.getParams(clazzName);
        String[] paramArray = params.split(",");
        Class<?>[] parameterTypes = new Class[paramArray.length];
        Object[] parameters = new Object[paramArray.length];
        JobInvokeUtils.getParams(paramArray, parameterTypes, parameters);

        try {
            Object bean;
            if (clazzName.startsWith("com.")) {
                bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
            } else {
                bean = SpringUtil.getBean(beanName);
            }
            Method method = bean.getClass().getMethod(methodName, parameterTypes);
            method.invoke(bean, parameters);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("执行定时任务时发生异常", e);
            // 可以根据需要进行更具体的异常处理，例如记录到数据库或发送通知 TODO
        }
    }
}