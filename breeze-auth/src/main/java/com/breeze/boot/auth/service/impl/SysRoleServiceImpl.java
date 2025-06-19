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

import cn.dev33.satoken.SaManager;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.mapper.SysRoleMapper;
import com.breeze.boot.auth.model.bo.RoleBO;
import com.breeze.boot.auth.model.bo.UserRoleBO;
import com.breeze.boot.auth.model.converter.SysRoleConverter;
import com.breeze.boot.auth.model.entity.SysRole;
import com.breeze.boot.auth.model.entity.SysRoleMenu;
import com.breeze.boot.auth.model.entity.SysRoleRowPermission;
import com.breeze.boot.auth.model.form.MenuPermissionForm;
import com.breeze.boot.auth.model.form.RoleForm;
import com.breeze.boot.auth.model.query.RoleQuery;
import com.breeze.boot.auth.model.vo.RoleVO;
import com.breeze.boot.auth.service.SysMenuService;
import com.breeze.boot.auth.service.SysRoleMenuService;
import com.breeze.boot.auth.service.SysRoleRowPermissionService;
import com.breeze.boot.auth.service.SysRoleService;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CacheConstants.PERMISSIONS;
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

    private final SysRoleConverter sysRoleConverter;

    /**
     * 系统角色菜单服务
     */
    private final SysRoleMenuService sysRoleMenuService;

    private final SysMenuService sysMenuService;

    /**
     * 行数据权限服务系统作用
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    /**
     * 列表页面
     *
     * @param query 角色查询
     * @return {@link Page}<{@link RoleVO}>
     */
    @Override
    public Page<RoleVO> listPage(RoleQuery query) {
        Page<SysRole> page = new Page<>(query.getCurrent(), query.getSize());
        Page<RoleBO> sysRolePage = this.baseMapper.listPage(page, query);
        return this.sysRoleConverter.bo2VOPage(sysRolePage);
    }

    /**
     * 按id获取信息
     *
     * @param roleId 角色id
     * @return {@link RoleVO }
     */
    @Override
    public RoleVO getInfoById(Long roleId) {
        RoleVO roleVO = this.sysRoleConverter.entity2VO(this.getById(roleId));
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
     * @param id   ID
     * @param form 角色表单
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> modifyRole(Long id, RoleForm form) {
        SysRole sysRole = this.sysRoleConverter.form2Entity(form);
        sysRole.setId(id);
        boolean update = this.updateById(sysRole);
        if (update) {
            this.saveRoleRowPermission(id, form);
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
    public Result<Boolean> saveRole(RoleForm form) {
        SysRole sysRole = this.sysRoleConverter.form2Entity(form);
        boolean save = this.save(sysRole);
        if (save) {
            this.saveRoleRowPermission(sysRole.getId(), form);
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
        List<SysRole> roleList = this.listByIds(ids);
        AssertUtil.isTrue(CollUtil.isNotEmpty(roleList), ROLE_NOT_FOUND);
        boolean remove = this.removeByIds(ids);
        if (remove) {
            List<Long> roleIdList = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
            // 删除用户角色关系
            this.sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleIdList));
            // 删除角色数据权限关系
            this.sysRoleRowPermissionService.remove(Wrappers.<SysRoleRowPermission>lambdaQuery().in(SysRoleRowPermission::getRoleId, roleIdList));
            for (SysRole sysRole : roleList) {
                @SuppressWarnings("unchecked") List<String> permissionList = (List<String>) SaManager.getSaTokenDao().getObject(PERMISSIONS + sysRole.getRoleCode());
                if (permissionList != null) {
                    SaManager.getSaTokenDao().delete(PERMISSIONS + sysRole.getRoleCode());
                }
            }
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    /**
     * 获取用户角色列表
     *
     * @param userId 用户Id
     * @return {@link Result}<{@link List}<{@link RoleVO}>>
     */
    @Override
    public List<RoleVO> listUserRoles(Long userId) {
        return this.baseMapper.listUserRoles(userId);
    }

    /**
     * 角色下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectRole() {
        return Result.ok(this.list().stream().map(sysRole -> {
            Map<String, Object> roleMap = Maps.newHashMap();
            roleMap.put("value", sysRole.getId());
            roleMap.put("label", sysRole.getRoleName());
            return roleMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 编辑权限
     *
     * @param form 菜单权限表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> modifyMenuPermission(MenuPermissionForm form) {
        this.sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, form.getRoleId()));
        List<SysRoleMenu> sysRoleMenuList = form.getPermissionIds().stream().map(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(form.getRoleId());
            return sysRoleMenu;
        }).collect(Collectors.toList());
        boolean batch = this.sysRoleMenuService.saveBatch(sysRoleMenuList);
        if (batch) {
            SysRole sysRole = this.getById(form.getRoleId());
            @SuppressWarnings("unchecked")
            List<String> permissionList = (List<String>) SaManager.getSaTokenDao().getObject(PERMISSIONS + sysRole.getRoleCode());
            if (permissionList != null) {
                permissionList = this.sysMenuService.listUserPermissionByRoleCode(sysRole.getRoleCode());
                SaManager.getSaTokenDao().updateObject(PERMISSIONS + sysRole.getRoleCode(), permissionList);
            }
        }
        return Result.ok(batch);
    }
}
