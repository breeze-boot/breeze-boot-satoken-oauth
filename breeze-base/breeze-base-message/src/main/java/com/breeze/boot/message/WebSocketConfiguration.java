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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
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
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        if (log.isDebugEnabled()) {
            log.debug("客户端断开连接 sessionId: {}", sessionId);
        }
        boolean deleted = rabbitAdmin.deleteQueue("userMsg-user" + sessionId);
        if (deleted) {
            log.info("删除队列成功 userMsg-user{}", sessionId);
            return;
        }
        log.info("删除队列失败 userMsg-user{}", sessionId);
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
        // registry.setApplicationDestinationPrefixes("/msg");

        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(mqProperties.getAddresses())       // rabbitmq-host服务器地址
                .setRelayPort(mqProperties.getStompPort())       // rabbitmq-stomp 服务器服务端口
                .setClientLogin(mqProperties.getUsername())      // 登陆账户
                .setClientPasscode(mqProperties.getPassword())   // 登陆密码
                .setSystemLogin(mqProperties.getUsername())      // 登陆账户
                .setSystemPasscode(mqProperties.getPassword())   // 登陆密码
                .setVirtualHost(mqProperties.getVirtualHost());
        //定义一对一推送的时候前缀
        registry.setUserDestinationPrefix("/user/");
        //客户端需要把消息发送到/message/xxx地址
        registry.setApplicationDestinationPrefixes("/message");
    }

    /**
     * 输入通道配置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new BreezeChannelInterceptorAdapter());
        registration.taskExecutor()    // 线程信息
                .corePoolSize(400)     // 核心线程池
                .maxPoolSize(800)      // 最多线程池数
                .keepAliveSeconds(60); // 超过核心线程数后，空闲线程超时60秒则杀死
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

}
