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

package com.breeze.boot.modules.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统邮箱实体
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_email")
@Schema(description = "系统邮箱实体")
public class SysEmailConfig extends IdBaseModel<SysEmailConfig> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String smtpHost;

    @TableField("`port`")
    private Integer port;

    @TableField("`username`")
    private String username;

    @TableField("`password`")
    private String password;

    @TableField("`encoding`")
    private String encoding;

    private String smtpDebug;

    private String smtpSocketFactoryClass;

    @TableField("`auth`")
    private String auth;

    private String protocol;

    @TableField("`ssl`")
    private String ssl;

    @TableField("`status`")
    private Integer status;
}
