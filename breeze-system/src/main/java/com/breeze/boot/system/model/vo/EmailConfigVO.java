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

package com.breeze.boot.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 系统邮箱VO
 *
 * @author gaoweixuan
 * @since 2024-07-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统邮箱VO")
public class EmailConfigVO {

    private Long id;

    private String smtpHost;

    private Integer port;

    private String username;

    private String password;

    private String encoding;

    private String smtpDebug;

    private String smtpSocketFactoryClass;

    private String auth;

    private String protocol;

    private String ssl;

    private Integer status;
}
