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

package com.breeze.boot.doc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

/**
 * 开放api配置
 *
 * @author gaoweixuan
 * @since 2023/05/30
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenApiConfig {

    private final OpenApiProperties openApiProperties;

    private static License getLicense() {
        return new License().name("Apache 2.0").url("");
    }

    @Bean
    public GroupedOpenApi sysApi() {
        return GroupedOpenApi.builder()
                .group("系统服务")
                // 增加自定义装备，这儿增加了一个用户认证的 header，否则 knife4j 里会没有 header
                .addOperationCustomizer((operation, handlerMethod) -> operation.security(
                        Collections.singletonList(this.getSecurityItem()))
                )
                .build();
    }

    private Info getInfo() {
        // @formatter:off
        return new Info()
                .title(openApiProperties.getTitle())
                .description(openApiProperties.getDescription())
                .version(openApiProperties.getVersion())
                .license(getLicense());
        // @formatter:on
    }

    private Server getServer() {
        return new Server().url(openApiProperties.getServerUrl());
    }

    private ExternalDocumentation getExternalDocumentation() {
        // @formatter:off
        return new ExternalDocumentation()
                .description(openApiProperties.getExternalDocumentationDescription())
                .url(openApiProperties.getExternalDocumentationDescriptionUrl());
        // @formatter:on
    }

    @Bean
    public OpenAPI openAPI() {
        // @formatter:off
        OpenAPI openAPI = new OpenAPI()
                .info(getInfo())
                .addServersItem(getServer())
                .addSecurityItem(this.getSecurityItem())
                .externalDocs(getExternalDocumentation());
        openAPI.schemaRequirement("OAuth2 Password Flow", this.buildPasswordSecurityScheme());
        openAPI.schemaRequirement("Bearer access_token", this.buildBearerSecurityScheme());
        return openAPI;
        // @formatter:on
    }

    private SecurityRequirement getSecurityItem() {
        // @formatter:off
        return new SecurityRequirement()
                .addList("OAuth2 Password Flow")
                .addList("Bearer access_token");
        // @formatter:on
    }

    private SecurityScheme buildBearerSecurityScheme() {
        // @formatter:off
        return new SecurityScheme().name("Bearer access_token")
                .type(SecurityScheme.Type.HTTP)
                .in(HEADER)
                .scheme("bearer")
                .description("使用有效access_token [Bearer access_token]")
                .flows(new OAuthFlows().authorizationCode(geOAuthFlow()));
        // @formatter:on
    }


    private SecurityScheme buildPasswordSecurityScheme() {
        // @formatter:off
        return new SecurityScheme()
                .name("OAuth2 Password Flow")
                .type(SecurityScheme.Type.OAUTH2)
                .description("OAuth2密码认证")
                .flows(new OAuthFlows().password(geOAuthFlow()));
        // @formatter:on
    }

    private OAuthFlow geOAuthFlow() {
        // @formatter:off
        return new OAuthFlow().tokenUrl(openApiProperties.getOAuthFlow()
                        .getTokenUrl())
                .authorizationUrl(openApiProperties.getOAuthFlow()
                        .getAuthorizationUrl())
                .scopes( new Scopes()
                        .addString("user_info", "user_info")
                        .addString("phone", "手机号")
                        .addString("email", "电子邮件")
                        .addString("profile", "用户身份信息")
                        .addString("openid", "openid"));
        // @formatter:on
    }

}
