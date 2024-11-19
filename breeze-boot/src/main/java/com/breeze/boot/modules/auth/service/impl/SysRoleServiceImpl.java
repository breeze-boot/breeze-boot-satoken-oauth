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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysRoleMapper;
import com.breeze.boot.modules.auth.model.bo.RoleBO;
import com.breeze.boot.modules.auth.model.bo.UserRoleBO;
import com.breeze.boot.modules.auth.model.entity.SysRole;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenu;
import com.breeze.boot.modules.auth.model.entity.SysRoleRowPermission;
import com.breeze.boot.modules.auth.model.form.RoleForm;
import com.breeze.boot.modules.auth.model.mappers.SysRoleMapStruct;
import com.breeze.boot.modules.auth.model.query.RoleQuery;
import com.breeze.boot.modules.auth.model.vo.RoleVO;
import com.breeze.boot.modules.auth.service.SysRoleMenuService;
import com.breeze.boot.modules.auth.service.SysRoleRowPermissionService;
import com.breeze.boot.modules.auth.service.SysRoleService;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.ROLE_NOT_FOUND;

/**
 * 系统角色服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapStruct sysRoleMapStruct;

    /**
     * 系统角色菜单服务
     */
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 行数据权限服务系统作用
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    /**
     * 列表页面
     *
     * @param roleQuery 角色查询
     * @return {@link Page}<{@link RoleVO}>
     */
    @Override
    public Page<RoleVO> listPage(RoleQuery roleQuery) {
        Page<RoleBO> sysRolePage = this.baseMapper.listPage(new Page<>(roleQuery.getCurrent(), roleQuery.getSize()), roleQuery);
        return this.sysRoleMapStruct.bo2VOPage(sysRolePage);
    }

    /**
     * 按id获取信息
     *
     * @param roleId 角色id
     * @return {@link RoleVO }
     */
    @Override
    public RoleVO getInfoById(Long roleId) {
        RoleVO roleVO = this.sysRoleMapStruct.entity2VO(this.getById(roleId));
        List<SysRoleRowPermission> roleRowPermissionList = this.sysRoleRowPermissionService.list(Wrappers.<SysRoleRowPermission>lambdaQuery().eq(SysRoleRowPermission::getRoleId, roleId));
        roleVO.setRowPermissionIds(roleRowPermissionList.stream().map(SysRoleRowPermission::getPermissionId).collect(Collectors.toList()));
        return roleVO;
    }

    /**
     * 用户角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link UserRoleBO}>
     */
    @Override
    public List<UserRoleBO> listRoleByUserId(Long userId) {
        return this.baseMapper.listRoleByUserId(userId);
    }

    /**
     * 修改角色
     *
     * @param id       ID
     * @param roleForm 角色表单
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> modifyRole(Long id, RoleForm roleForm) {
        SysRole sysRole = sysRoleMapStruct.form2Entity(roleForm);
        sysRole.setId(id);
        boolean update = this.updateById(sysRole);
        if (update) {
            this.saveRoleRowPermission(id, roleForm);
        }
        return Result.ok(Boolean.FALSE);
    }

    private void saveRoleRowPermission(Long id, RoleForm roleForm) {
        Set<Long> permissionIds = Optional.ofNullable(roleForm.getRowPermissionIds()).orElse(Sets.newHashSet());
        Set<SysRoleRowPermission> sysRoleRowPermissionSet = permissionIds.stream().map(item -> SysRoleRowPermission.builder().roleId(id).permissionId(item).build()).collect(Collectors.toSet());
        this.sysRoleRowPermissionService.remove(Wrappers.<SysRoleRowPermission>lambdaQuery().in(SysRoleRowPermission::getRoleId, id));
        this.sysRoleRowPermissionService.saveBatch(sysRoleRowPermissionSet);
    }

    @Override
    public Result<Boolean> saveRole(RoleForm roleForm) {
        SysRole sysRole = sysRoleMapStruct.form2Entity(roleForm);
        boolean save = this.save(sysRole);
        if (save) {
            this.saveRoleRowPermission(sysRole.getId(), roleForm);
        }
        return Result.ok(Boolean.FALSE);
    }

    /**
     * 通过IDS删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteByIds(List<Long> ids) {
        List<SysRole> roleEntityList = this.listByIds(ids);
        AssertUtil.isTrue(CollUtil.isNotEmpty(roleEntityList), ROLE_NOT_FOUND);
        boolean remove = this.removeByIds(ids);
        if (remove) {
            List<Long> collect = roleEntityList.stream().map(SysRole::getId).collect(Collectors.toList());
            // 删除用户角色关系
            this.sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, collect));
            // 删除角色数据权限关系
            this.sysRoleRowPermissionService.remove(Wrappers.<SysRoleRowPermission>lambdaQuery().in(SysRoleRowPermission::getRoleId, collect));
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 获取用户角色列表
     *
     * @param userId 用户Id
     * @return {@link Result}<{@link List}<{@link Long}>>
     */
    @Override
    public List<Long> listUserRoles(Long userId) {
        return this.baseMapper.listUserRoles(userId);
    }

}
