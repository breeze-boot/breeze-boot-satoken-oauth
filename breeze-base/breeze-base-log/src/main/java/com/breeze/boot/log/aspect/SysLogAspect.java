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

package com.breeze.boot.log.aspect;

import cn.hutool.core.date.StopWatch;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.breeze.boot.log.enums.LogEnum.LogType.SYSTEM;
import static com.breeze.boot.log.enums.LogEnum.Result.FAIL;
import static com.breeze.boot.log.enums.LogEnum.Result.SUCCESS;

/**
 * 系统日志方面
 *
 * @author gaoweixuan
 * @since 2023/05/07
 */
@Slf4j
@Aspect
public class SysLogAspect {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 发布保存系统的日志事件
     */
    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;


    public SysLogAspect(PublisherSaveSysLogEvent publisherSaveSysLogEvent) {
        this.publisherSaveSysLogEvent = publisherSaveSysLogEvent;
        this.mapper.registerModules(new JavaTimeModule());
    }

    /**
     * 环绕通知
     *
     * @param point  切点
     * @param sysLog 系统日志
     * @return {@link Object}
     */
    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, BreezeSysLog sysLog) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HttpServletRequest request = this.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 方法名称
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 入参
        Object[] param = point.getArgs();
        String className = point.getTarget().getClass().getName();
        log.debug("[类名]: {} , [方法]: {}", className, methodName);
        SysLogBO sysLogBO = this.buildLog(sysLog, request, param);
        Object obj;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            log.error("[业务异常信息]", e);
            sysLogBO.setResultMsg(e.getMessage());
            sysLogBO.setResult(FAIL.getCode());
            throw e;
        } finally {
            stopWatch.stop();
            sysLogBO.setTime(String.valueOf(stopWatch.getTotalTimeSeconds()));
            this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
            this.printLog(methodName, this.write(param), stopWatch);
        }
        return obj;
    }

    /**
     * 执行日志 业务
     *
     * @param breezeSysLog 日志
     * @param request      请求
     * @param param        参数
     * @return {@link SysLogBO }
     */
    @SneakyThrows
    private SysLogBO buildLog(BreezeSysLog breezeSysLog, HttpServletRequest request, Object[] param) {
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder()
                .system(userAgent)
                .logTitle(breezeSysLog.description())
                .doType(breezeSysLog.type().getCode())
                .logType(SYSTEM.getCode())
                .resultMsg("")
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .paramContent(this.write(param))
                .result(SUCCESS.getCode())
                .build();
    }

    /**
     * 打印日志
     *
     * @param stopWatch 时间监听
     */
    private void printLog(String methodName, String jsonString, StopWatch stopWatch) {
        log.info("[传入参数]：\n {}", jsonString);
        log.trace("[方法名称]: {} [执行时间]: {}", methodName, stopWatch.getTotalTimeMillis());
    }

    /**
     * 获取http servlet请求
     *
     * @return {@link HttpServletRequest}
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

    /**
     * 写
     *
     * @param data 数据
     * @return {@link String}
     */
    public String write(Object data) {
        try {
            return this.mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

}
