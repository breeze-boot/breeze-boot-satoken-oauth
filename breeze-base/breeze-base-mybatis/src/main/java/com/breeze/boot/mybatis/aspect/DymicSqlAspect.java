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

package com.breeze.boot.mybatis.aspect;

import cn.hutool.core.date.StopWatch;
import com.breeze.boot.core.base.PageQuery;
import com.breeze.boot.core.utils.QueryHolder;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * 动态参数获取切面
 *
 * @author gaoweixuan
 * @since 2025/02/02
 */
@Slf4j
@Aspect
public class DymicSqlAspect {

    /**
     * 环绕通知
     *
     * @param point    切点
     * @param dymicSql 动态sql
     * @return {@link Object}
     */
    @SneakyThrows
    @Around("@annotation(dymicSql)")
    public Object around(ProceedingJoinPoint point, DymicSql dymicSql) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object obj = null;
        try {
            String methodName = getMethodName(signature);
            log.debug("方法: {}", methodName);
            Object[] params = point.getArgs();
            if (isHasAnnotation(signature)) {
                this.processParams(params);
            }
            obj = point.proceed();
        } catch (Exception e) {
            handleException(e);
        } finally {
            QueryHolder.clear();
            stopWatch.stop();
            log.info("execution time: {} ms", stopWatch.getTotalTimeMillis());
        }
        return obj;
    }

    private String getMethodName(MethodSignature signature) {
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }
    private static boolean isHasAnnotation(MethodSignature signature) {
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        return Arrays.stream(parameterAnnotations)
                .flatMap(Arrays::stream)
                .anyMatch(annotation -> annotation instanceof ConditionParam);
    }

    private void processParams(Object[] params) {
        for (Object param : params) {
            if (param instanceof PageQuery pageQuery) {
                LinkedHashMap<String, Object> paramsMap = this.createParamsMap(pageQuery);
                QueryHolder.setQuery(paramsMap);
                break;
            }
        }
    }

    private LinkedHashMap<String, Object> createParamsMap(PageQuery pageQuery) {
        LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("conditions", pageQuery.getCondition());
        paramsMap.put("sort", pageQuery.getSort());
        return paramsMap;
    }

    private void handleException(Exception e) throws Exception {
        log.error("[业务异常信息]", e);
        throw e;
    }

}
