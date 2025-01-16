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

package com.breeze.boot.modules.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.satoken.model.BaseSysRegisteredClient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 系统注册客户端
 *
 * @author gaoweixuan
 * @since 2023/05/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_registered_client")
@Schema(description = "注册客户端实体")
public class SysRegisteredClient extends BaseSysRegisteredClient {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户id发布时间
     */
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户秘密
     */
    private String clientSecret;

    /**
     * 客户秘密到期时间
     */
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端身份验证方法
     */
    private String clientAuthenticationMethods;

    /**
     * 授权批准类型
     */
    private String authorizationGrantTypes;

    /**
     * 重定向uri
     */
    private String redirectUris;

    /**
     * 作用域
     */
    private String scopes;

}
