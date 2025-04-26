package com.breeze.boot.core.utils;

import cn.hutool.core.util.ReUtil;
import com.breeze.boot.core.annotation.BaseFilter;
import com.breeze.boot.core.model.BaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 加载注解配置工具包
 *
 * @author gaoweixuan
 * @since 2023/04/16
 */
@Slf4j
public class LoadAnnotationUtils {
    /**
     * 正则
     */
    private static final String PATTERN = "\\{(.*?)\\}";

    /**
     * 负载控制器映射
     * 加载控制器映射
     *
     * @param properties                   属性
     * @param applicationContext           应用程序上下文
     */
    public static void loadControllerMapping(BaseProperties properties,
                                             ApplicationContext applicationContext,
                                             Map<RequestMappingInfo, HandlerMethod> methodMap ) {
        log.info("配置的过滤的地址：{}", properties.getIgnoreUrls());
        // 获取全部的请求方法
        methodMap.forEach((requestMappingInfo, method) -> {
            Class<?> clazz = applicationContext.getBean(method.getBean().toString()).getClass();
            // 根据类进行判断这个方法所在的类是否需要过滤
            if (Objects.nonNull(AnnotationUtils.findAnnotation(clazz, BaseFilter.class))) {
                Optional.of(requestMappingInfo.getPathPatternsCondition())
                        .ifPresent((condition) -> condition.getPatternValues().forEach(patternUrl -> setURl(properties, patternUrl)));
            } else {
                Optional.ofNullable(requestMappingInfo.getPathPatternsCondition())
                        .ifPresent((condition) -> setIgnoreUrl(method, condition.getPatternValues(), properties));
            }
        });
    }

    /**
     * 使用方法进行判断是否需要过滤
     *
     * @param method       方法
     * @param pathPatterns 路径模式
     */
    private static void setIgnoreUrl(HandlerMethod method, Set<String> pathPatterns, BaseProperties properties) {
        if (Objects.isNull(method.getMethodAnnotation(BaseFilter.class))) {
            return;
        }
        pathPatterns.forEach(patternUrl -> setURl(properties, patternUrl));
    }

    private static void setURl(BaseProperties properties, String patternUrl) {
        String url = ReUtil.replaceAll(patternUrl, PATTERN, "*");
        log.info("过滤的URL： {}", url);
        properties.getIgnoreUrls().add(url);
    }

}
