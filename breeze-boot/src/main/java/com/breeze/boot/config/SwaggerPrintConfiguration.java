package com.breeze.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Slf4j
@Configuration
public class SwaggerPrintConfiguration implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            //获取IP
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            //获取应用名
            String applicationName = event.getApplicationContext().getApplicationName();
            log.info("swagger 接口文档地址: \n http://{}:{}{}/swagger-ui.html",hostAddress, event.getWebServer().getPort(), applicationName);
            log.info("knife4j 接口文档地址: \n http://{}:{}{}/doc.html\n", hostAddress, event.getWebServer().getPort(), applicationName);
        } catch (UnknownHostException e) {
            log.error("获取地址失败", e);
        }
    }
}
