package com.breeze.boot.message.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class RabbitTemplateConfig {

    private final ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 开启确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到交换机，消息ID: " + correlationData.getId());
            } else {
                log.error("消息发送到交换机失败，原因: " + cause);
            }
        });
        // 开启返回回调
        rabbitTemplate.setReturnsCallback(returned -> {
           log.error("消息从交换机路由到队列失败，消息: " + new String(returned.getMessage().getBody()) +
                    ", 回复码: " + returned.getReplyCode() +
                    ", 回复文本: " + returned.getReplyText() +
                    ", 交换机: " + returned.getExchange() +
                    ", 路由键: " + returned.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
