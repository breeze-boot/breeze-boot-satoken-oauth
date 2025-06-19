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

package com.breeze.boot.auth.model.vo;

import cn.dev33.satoken.session.TokenSign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 在线用户VO
 *
 * @author gaoweixuan
 * @since 2024-11-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "在线用户VO")
public class OnlineUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 会话创建时间
     */
    @Schema(description = "会话创建时间")
    private LocalDateTime sessionCreateTime;
    /**
     * 会话失效时间
     */
    @Schema(description = "会话失效时间")
    private LocalDateTime sessionExpirationTime;

    /**
     * 用户名
     */
    @Schema(description = "用户名称")
    private String username;

    /**
     * 用户编码
     */
    @Schema(description = "用户编码")
    private String userCode;

    /**
     * 登录账户名称
     */
    @Schema(description = "登录账户名称")
    private String displayName;

    /**
     * 登录设备数量
     */
    @Schema(description = "登录设备数量")
    private Integer loginDeviceCount;

    /**
     * 登录设备
     */
    @Schema(description = "登录设备")
    private List<TokenSign> loginDevice;

}
