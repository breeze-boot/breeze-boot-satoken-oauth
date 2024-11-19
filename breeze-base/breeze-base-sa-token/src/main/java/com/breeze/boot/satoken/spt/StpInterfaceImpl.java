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

package com.breeze.boot.satoken.spt;

import cn.dev33.satoken.stp.StpInterface;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 自定义权限加载接口实现类
 * 注入容器 完成 Sa-Token 的自定义权限验证扩展
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final IUserDetailService userDetailService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return userDetailService.loadUserPermissionByUserId(loginId.toString());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return userDetailService.loadUserRoleByUserId(loginId.toString());
    }

}
