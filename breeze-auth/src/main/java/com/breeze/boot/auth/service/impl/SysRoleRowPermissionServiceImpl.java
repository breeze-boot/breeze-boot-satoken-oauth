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

package com.breeze.boot.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.mapper.SysRoleRowPermissionMapper;
import com.breeze.boot.auth.model.entity.SysRoleRowPermission;
import com.breeze.boot.auth.service.SysRoleRowPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 系统角色行级权限服务 impl
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysRoleRowPermissionServiceImpl extends ServiceImpl<SysRoleRowPermissionMapper, SysRoleRowPermission> implements SysRoleRowPermissionService {

    /**
     * 角色数据权限列表
     *
     * @param roleIdSet 角色ID Set
     * @return {@link Set }<{@link String }>
     */
    @Override
    public Set<String> listRowPermission(Set<Long> roleIdSet) {
        return this.baseMapper.listRowPermission(roleIdSet);
    }

}




