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

package com.breeze.boot.message.config;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 握手拦截器
 *
 * @author gaoweixuan
 * @since 2022-11-16
 */
@Slf4j
public class BreezeHandShakeInterceptor implements HandshakeInterceptor {

    /**
     * 握手前
     *
     * @param request    请求
     * @param response   响应
     * @param wsHandler  ws处理程序
     * @param attributes 属性
     * @return boolean
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) {
        log.info("握手前：{}", request.getURI());
        return true;
    }

    /**
     * 握手后
     *
     * @param request   请求
     * @param response  响应
     * @param wsHandler ws处理程序
     * @param ex        异常
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, Exception ex) {
        log.info("握手后：{}", request.getURI());
    }

}
