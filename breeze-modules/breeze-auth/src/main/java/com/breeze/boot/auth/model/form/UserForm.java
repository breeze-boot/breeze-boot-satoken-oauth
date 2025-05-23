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

package com.breeze.boot.auth.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户表单
 *
 * @author gaoweixuan
 * @since 2024-07-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户表单")
public class UserForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不可为空")
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
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 头像文件ID
     */
    @Schema(description = "头像文件ID")
    private Long avatarFileId;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long postId;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 性别 0 女性 1 男性
     */
    @Schema(description = "性别 0 女性 1 男性")
    private Integer sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 微信OpenID
     */
    @Schema(description = "微信OpenID")
    private String openId;

    /**
     * 邮件
     */
    @Schema(description = "邮件")
    private String email;

    /**
     * 锁定
     */
    @Schema(description = "锁定")
    private Integer isLock;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 用户角色ID
     * <p>
     * 查询用户详情返回使用
     */
    @Schema(description = "用户角色ID")
    private List<Long> roleIds;

}
