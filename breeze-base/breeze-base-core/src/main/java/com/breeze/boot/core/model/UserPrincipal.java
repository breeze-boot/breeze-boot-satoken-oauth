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

package com.breeze.boot.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 用户信息扩展（返回前端）
 *
 * @author gaoweixuan
 * @since 2021/10/1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Schema(description = "用户信息扩展")
@NoArgsConstructor
public class UserPrincipal implements Serializable {

    @Serial
    private static final long serialVersionUID = -4085833706868746460L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    private String username;

    private String password;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean enabled;

    private Boolean credentialsNonExpired;

    private Collection<GrantedAuthority> authorities;

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
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 数据权限编码
     */
    @Schema(description = "行数据权限编码")
    private Set<String> rowPermissionCode;

    /**
     * 数据权限规则
     */
    @Schema(description = "数据权限规则")
    private String permissionType;

    private Collection<Long> subDeptId;

    // @formatter:off
    /**
     * 用户主体
     *
     * @param username              用户名
     * @param password              密码
     * @param enabled               启用
     * @param accountNonExpired     账户不过期
     * @param credentialsNonExpired 凭证不过期
     * @param accountNonLocked      非锁定账户
     * @param authorities          权限集合
     * @param id                    用户Id
     * @param deptId                部门Id
     * @param deptName              部门名称
     * @param userCode              用户代码
     * @param displayName            登录显示的用户账户名
     * @param avatar                头像
     * @param phone                 电话
     * @param sex                   性
     * @param amountType            量类型
     * @param isLock                是否锁定
     * @param email                 电子邮件
     * @param userRoleCodes         用户角色代码
     * @param tenantId              租户Id
     * @param rowPermissionCode     行数据权限
     */
    public UserPrincipal(String username,
                         String password,
                         boolean enabled,
                         boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Set<GrantedAuthority> authorities,
                         Long id,
                         Long deptId,
                         String deptName,
                         String userCode,
                         String displayName,
                         String avatar,
                         String phone,
                         Integer sex,
                         Integer amountType,
                         Integer isLock,
                         String email,
                         Set<String> userRoleCodes,
                         Set<Long> userRoleIds,
                         Long tenantId,
                         String permissionType,
                         Set<String> rowPermissionCode,
                         Set<Long> subDeptId) {
        this.id = id;
        this.deptId = deptId;
        this.deptName = deptName;
        this.userCode = userCode;
        this.displayName = displayName;
        this.avatar = avatar;
        this.phone = phone;
        this.sex = sex;
        this.amountType = amountType;
        this.isLock = isLock;
        this.email = email;
        this.userRoleCodes = userRoleCodes;
        this.userRoleIds = userRoleIds;
        this.tenantId = tenantId;
        this.permissionType = permissionType;
        this.rowPermissionCode = rowPermissionCode;
        this.username =  username;
        this.password =  password;
        this.enabled =  enabled;
        this.accountNonExpired =  accountNonExpired;
        this.credentialsNonExpired =  credentialsNonExpired;
        this.accountNonLocked =  accountNonLocked;
        this.authorities = authorities;
        this.subDeptId = subDeptId;
    }

}