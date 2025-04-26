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

package com.breeze.boot.satoken.oauth2;

import com.breeze.boot.core.model.GrantedAuthority;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.satoken.model.UserInfoDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户服务接口
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
public interface IUserDetailService {

    /**
     * 加载用户通过用户ID
     *
     * @param userId 用户Id
     * @return {@link UserPrincipal}
     */
    UserPrincipal loadUserByUserId(String userId);

    /**
     * 加载用户通过角色编码
     *
     * @param roleCode 角色编码
     * @return {@link UserPrincipal}
     */
    List<String> loadUserPermissionByRoleCode(String roleCode);

    /**
     * 加载用户通过电话
     *
     * @param phone 电话
     * @return {@link UserPrincipal}
     */
    UserPrincipal loadUserByPhone(String phone);

    /**
     * 加载用户通过用户名
     *
     * @param username 用户名
     * @return {@link UserPrincipal}
     */
    UserPrincipal loadUserByUsername(String username);

    /**
     * 加载用户通过电子邮件
     *
     * @param email 电子邮件
     * @return {@link UserPrincipal}
     */
    UserPrincipal loadUserByEmail(String email);

    /**
     * 获取登录用户
     *
     * @param userInfo 登录用户
     * @return {@link UserPrincipal}
     */
    default UserPrincipal convertResponseUserInfo(UserInfoDTO userInfo) {
        return new UserPrincipal(userInfo.getUsername(), userInfo.getPassword(), Objects.equals(userInfo.getIsLock(), 0),
                true, true, Objects.equals(userInfo.getIsLock(), 0),
                userInfo.getAuthorities().stream().map(GrantedAuthority::new).collect(Collectors.toSet()),
                userInfo.getUserId(), userInfo.getDeptId(), userInfo.getDeptName(), userInfo.getUserCode(), userInfo.getDisplayName(),
                userInfo.getAvatar(), userInfo.getPhone(), userInfo.getSex(), userInfo.getAmountType(), userInfo.getIsLock(), userInfo.getEmail(),
                userInfo.getUserRoleCodes(), userInfo.getUserRoleIds(), userInfo.getTenantId(), userInfo.getPermissionType(),
                userInfo.getRowPermissionCode(), userInfo.getSubDeptId());
    }

    List<String> loadUserRoleByUserId(String userId);

}
