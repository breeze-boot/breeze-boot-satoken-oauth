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

package com.breeze.boot.satoken.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 登录的用户信息
 *
 * @author gaoweixuan
 * @since 2022-01-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "登录的用户信息")
public class UserInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 用户编码
     */
    @Schema(description = "用户编码")
    private String userCode;

    /**
     * 登录后显示的账户名称
     */
    @Schema(description = "登录显示的用户账户名")
    private String displayName;

    /**
     * 用户名
     */
    @Schema(description = "登录用户名")
    private String username;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    private String password;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private Integer sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 类型
     */
    @Schema(description = "账户类型")
    private Integer amountType;

    /**
     * 锁定
     */
    @Schema(description = "锁定")
    private Integer isLock;

    /**
     * 微信OpenId
     */
    @Schema(description = "微信OpenId")
    private String openId;

    /**
     * 邮件
     */
    @Schema(description = "邮件")
    private String email;

    /**
     * 用户角色代码集合
     */
    @Schema(description = "用户角色编码集合")
    private Set<String> userRoleCodes;

    /**
     * 用户角色Id集合
     */
    @Schema(description = "用户角色Id集合")
    private Set<Long> userRoleIds;

    /**
     * 权限
     */
    @Schema(description = "权限")
    private Set<String> authorities;

    /**
     * 数据权限编码
     */
    @Schema(description = "数据权限编码")
    private Set<String> rowPermissionCode;

    /**
     * 数据权限规则
     */
    @Schema(description = "数据权限规则")
    private String permissionType;

    /**
     * 子部门
     */
    @Schema(description = "子部门")
    private Set<Long> subDeptId;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
