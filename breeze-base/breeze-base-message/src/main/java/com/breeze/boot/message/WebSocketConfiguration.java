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

package com.breeze.boot.message;

import com.breeze.boot.message.config.BreezeChannelInterceptorAdapter;
import com.breeze.boot.message.config.BreezeHandShakeInterceptor;
import com.breeze.boot.message.config.BreezeRabbitMqProperties;
import com.breeze.boot.message.events.PublisherSaveMsgEvent;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompBrokerRelayMessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * websocket 配置
 *
 * @author gaoweixuan
 * @since 2022-11-16
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSocketMessageBroker
@Import(PublisherSaveMsgEvent.class)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final BreezeRabbitMqProperties mqProperties;

    private final AmqpAdmin rabbitAdmin;

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private final ApplicationContext applicationContext;

    /**
     * websocket端点接收客户端的连接
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new BreezeHandShakeInterceptor())
                .setAllowedOriginPatterns("*");
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        String destination = SimpMessageHeaderAccessor.wrap(event.getMessage()).getDestination();
        log.info("客户端订阅了目标地址: {}", destination);
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        if (log.isDebugEnabled()) {
            log.debug("客户端断开连接 sessionId: {}", sessionId);
        }
        try {
            boolean deleted = rabbitAdmin.deleteQueue("userMsg-user" + sessionId);
            if (deleted) {
                log.info("删除队列成功 userMsg-user{}", sessionId);
            } else {
                log.warn("队列 userMsg-user{} 不存在，无需删除", sessionId);
            }
        } catch (Exception e) {
            log.error("删除队列 userMsg-user{} 时出现异常: {}", sessionId, e.getMessage(), e);
        }
    }

    /**
     * 定义消息代理，消息连接的规范信息
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 开启简单基于内存的消息代理
        // registry.enableSimpleBroker("/queue", "/topic");
        // 点对点消息的订阅前缀
        // registry.setUserDestinationPrefix("/user");
        // 客户端发送消息的前缀
        // registry.setApplicationDestinationPrefixes("/message");
        int maxRetries = 3;
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                registry.enableStompBrokerRelay("/topic", "/queue")
                        .setRelayHost(mqProperties.getAddresses())       // rabbitmq-host服务器地址
                        .setRelayPort(mqProperties.getStompPort())       // rabbitmq-stomp 服务器服务端口
                        .setClientLogin(mqProperties.getUsername())      // 登陆账户
                        .setClientPasscode(mqProperties.getPassword())   // 登陆密码
                        .setSystemLogin(mqProperties.getUsername())      // 登陆账户
                        .setSystemPasscode(mqProperties.getPassword())   // 登陆密码
                        .setVirtualHost(mqProperties.getVirtualHost());
                break; // 连接成功，退出重试循环
            } catch (Exception e) {
                retryCount++;
                log.error("连接RabbitMQ消息代理失败（第 {} 次重试）: {}", retryCount, e.getMessage(), e);
                try {
                    Thread.sleep(2000); // 等待 2 秒后重试
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (retryCount == maxRetries) {
            log.error("连接RabbitMQ消息代理失败，达到最大重试次数");
        }
        //定义一对一推送的时候前缀
        registry.setUserDestinationPrefix("/user/");
        //客户端需要把消息发送到/message/xxx地址
        registry.setApplicationDestinationPrefixes("/send");
    }

    /**
     * 输入通道配置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new BreezeChannelInterceptorAdapter());
        registration.taskExecutor(threadPoolTaskExecutor);
    }

    /**
     * 消息传输参数配置
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(15 * 1000)    // 超时时间
                .setSendBufferSizeLimit(512 * 1024) // 缓存空间
                .setMessageSizeLimit(128 * 1024);   // 消息大小
    }

    @PreDestroy
    public void destroy() {
        StompBrokerRelayMessageHandler stompBrokerRelay = applicationContext.getBean(StompBrokerRelayMessageHandler.class);
        stompBrokerRelay.stop();
    }
}
