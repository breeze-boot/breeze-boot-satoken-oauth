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

package com.breeze.boot.manager;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.bpm.service.IGroupService;
import com.breeze.boot.bpm.service.IUserService;
import com.breeze.boot.core.utils.BreezeTenantHolder;
import com.breeze.boot.modules.auth.model.bo.FlowUserBO;
import com.breeze.boot.modules.auth.model.entity.SysRole;
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.bpm.model.entity.Group;
import com.breeze.boot.bpm.model.entity.Membership;
import com.breeze.boot.bpm.model.entity.User;
import com.breeze.boot.bpm.service.IMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FlowableManager {

    private final IUserService userService;

    private final IGroupService groupService;

    private final IMembershipService membershipService;

    /**
     * 同步用户信息
     */
    public void syncUser(List<FlowUserBO> flowUserBOList, List<SysRole> roles) {
        this.groupService.saveOrUpdateBatch(roles.stream().map(sysRole ->
                Group.builder()
                        .id(sysRole.getRoleCode())
                        .name(sysRole.getRoleName())
                        .build())
                .collect(Collectors.toList()));
        List<User> userList = this.userService.list();
        List<User> deleteUser = userList.stream()
                .filter(item -> flowUserBOList.stream()
                        .noneMatch(userBO -> userBO.getUsername().equals(item.getId())))
                .toList();
        for (User user : deleteUser) {
            this.membershipService.remove(Wrappers.<Membership>lambdaQuery().eq(Membership::getUserId, user.getId()));
            this.userService.remove(Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        }
        for (FlowUserBO flowUserBO : flowUserBOList) {
            User user = this.userService.getById(flowUserBO.getUsername());
            if (Objects.nonNull(user)) {
                user.setId(flowUserBO.getUsername());
                user.setEmail(flowUserBO.getEmail());
                user.setDisplayName(flowUserBO.getDisplayName());
                userService.updateById(user);
            } else {
                userService.save(User.builder()
                        .id(flowUserBO.getUsername())
                        .displayName(flowUserBO.getDisplayName())
                        .first(flowUserBO.getUsername())
                        .rev(1)
                        .last(flowUserBO.getUsername())
                        .email(flowUserBO.getEmail())
                        .build());
            }
            syncGroupShip(flowUserBO);
        }
    }

    /**
     * 同步用户-角色信息
     */
    public void syncGroupShip(FlowUserBO flowUserBO) {
        List<SysRole> roleList = flowUserBO.getRoleList();
        for (SysRole sysRole : roleList) {
            Group group = groupService.getOne(Wrappers.<Group>lambdaQuery().eq(Group::getId, sysRole.getRoleCode()));
            if (Objects.nonNull(group)) {
                this.saveMembership(flowUserBO, sysRole);
                this.groupService.updateById(Group.builder().name(sysRole.getRoleName()).build());
            } else {
                this.groupService.save(Group.builder().id(sysRole.getRoleCode()).name(sysRole.getRoleName())
                        .rev(1).build());
                this.saveMembership(flowUserBO, sysRole);
            }
        }
    }

    private void saveMembership(FlowUserBO flowUserBO, SysRole sysRole) {
        Membership membership = this.membershipService.getOne(Wrappers.<Membership>lambdaQuery().eq(Membership::getUserId, flowUserBO.getUsername()).eq(Membership::getGroupId, sysRole.getRoleCode()));
        if (Objects.nonNull(membership)) {
            this.membershipService.remove(Wrappers.<Membership>lambdaQuery().eq(Membership::getUserId, flowUserBO.getUsername()).eq(Membership::getGroupId, sysRole.getRoleCode()));
        }
        this.membershipService.save(Membership.builder().groupId(sysRole.getRoleCode())
                .userId(flowUserBO.getUsername())
                .build());
    }

    public void createUser(SysUser sysUser, String password, List<SysRole> sysRoles) {
        this.userService.save(User.builder()
                .id(sysUser.getUsername())
                .displayName(sysUser.getDisplayName())
                .first(sysUser.getUsername())
                .last(sysUser.getUsername())
                .rev(1)
                .pwd(password)
                .tenantId(BreezeTenantHolder.getTenant().toString())
                .email(sysUser.getEmail()).build());
        this.membershipService.saveBatch(
                sysRoles.stream()
                        .map(sysRole -> Membership
                                .builder()
                                .userId(sysUser.getUsername())
                                .groupId(sysRole.getRoleCode())
                                .build())
                        .collect(Collectors.toList()));
    }

    public void createGroup(SysRole sysRole) {
        this.groupService.save(Group.builder().id(sysRole.getRoleCode()).name(sysRole.getRoleName()).build());
    }

    public void updateGroup(SysRole sysRole) {
        this.groupService.updateById(Group.builder().id(sysRole.getRoleCode()).name(sysRole.getRoleName()).build());
    }

    public void deleteGroup(String roleCode) {
        this.groupService.removeById(roleCode);
    }

    public void deleteMembership(String roleCode) {
        this.membershipService.remove(Wrappers.<Membership>lambdaQuery().eq(Membership::getGroupId, roleCode));
    }

    public void updatePwd(SysUser sysUser, String password) {
        this.userService.updateById(User.builder().id(sysUser.getUsername()).pwd(password).build());
    }

    public void deleteUser(String username) {
        this.membershipService.remove(Wrappers.<Membership>lambdaQuery().eq(Membership::getUserId, username));
        this.userService.remove(Wrappers.<User>lambdaQuery().eq(User::getId, username));
    }

    public void updateUser(SysUser sysUser, List<SysRole> sysRoles) {
        this.userService.updateById(User.builder()
                .id(sysUser.getUsername())
                .first(sysUser.getUsername())
                .last(sysUser.getUsername())
                .displayName(sysUser.getDisplayName())
                .email(sysUser.getEmail())
                .build());
        this.membershipService.remove(Wrappers.<Membership>lambdaQuery().eq(Membership::getUserId, sysUser.getUsername()));
        this.membershipService.saveBatch(
                sysRoles.stream()
                        .map(sysRole -> Membership
                                .builder()
                                .userId(sysUser.getUsername())
                                .groupId(sysRole.getRoleCode())
                                .build())
                        .collect(Collectors.toList()));
    }

    public void saveUserMembers(List<SysRole> sysUserRoleList, String username) {
        this.membershipService.saveBatch(
                sysUserRoleList.stream()
                        .map(role -> Membership
                                .builder()
                                .userId(username)
                                .groupId(role.getRoleCode())
                                .build())
                        .collect(Collectors.toList()));
    }
}
