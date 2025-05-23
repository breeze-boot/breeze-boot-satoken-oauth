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

package com.breeze.boot.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.breeze.boot.auth.model.bo.FlowUserBO;
import com.breeze.boot.auth.model.entity.SysRole;
import com.breeze.boot.auth.model.entity.SysUser;
import com.breeze.boot.auth.service.SysRoleService;
import com.breeze.boot.auth.service.SysUserRoleService;
import com.breeze.boot.auth.service.SysUserService;
import com.breeze.boot.manager.FlowableManager;
import com.breeze.boot.manager.service.FlowableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowableServiceImpl implements FlowableService {

    private final FlowableManager flowableManager;
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 同步审批流用户
     */
    @Override
    public void syncFlowableUser() {
        List<SysUser> sysUserList = this.sysUserService.list();
        List<SysRole> roles = this.sysRoleService.list();
        List<FlowUserBO> syncUser = sysUserList.stream().map(item -> {
            FlowUserBO flowUserBO = FlowUserBO.builder()
                    .userId(item.getId())
                    .username(item.getUsername())
                    .displayName(item.getDisplayName())
                    .email(item.getEmail())
                    .build();
            List<SysRole> userRoleList = this.sysUserRoleService.getSysRoleByUserId(item.getId());
            if (CollUtil.isNotEmpty(userRoleList)) {
                List<SysRole> sysRoleList = this.sysRoleService.listByIds(userRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
                flowUserBO.setRoleList(sysRoleList);
            }
            return flowUserBO;
        }).collect(Collectors.toList());
        this.flowableManager.syncUser(syncUser, roles);
    }

}
