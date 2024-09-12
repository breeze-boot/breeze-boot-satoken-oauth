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

package com.breeze.boot.modules.system.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.enums.DataPermissionType;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysMenu;
import com.breeze.boot.modules.auth.model.entity.SysPlatform;
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.modules.auth.model.query.DeptQuery;
import com.breeze.boot.modules.auth.service.*;
import com.breeze.boot.modules.dev.service.SysDbMateService;
import com.breeze.boot.modules.system.model.form.FileForm;
import com.breeze.boot.modules.system.service.SysCommonService;
import com.breeze.boot.modules.system.service.SysFileService;
import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CoreConstants.ROOT;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@Service
@RequiredArgsConstructor
public class SysCommonServiceImpl implements SysCommonService {

    /**
     * 菜单服务
     */
    private final SysMenuService menuService;

    /**
     * 平台服务
     */
    private final SysPlatformService platformService;

    /**
     * 用户服务
     */
    private final SysUserService userService;

    /**
     * 平台服务
     */
    private final SysDeptService deptService;

    /**
     * 角色服务
     */
    private final SysRoleService roleService;

    /**
     * 租户服务
     */
    private final SysTenantService tenantService;

    /**
     * 岗位服务
     */
    private final SysPostService postService;

    /**
     * 元数据服务
     */
    private final SysDbMateService sysMateService;

    /**
     * 行数据权限服务
     */
    private final SysRowPermissionService sysRowPermissionService;

    /**
     * 文件服务
     */
    private final SysFileService sysFileService;

    /**
     * 菜单树形下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<Tree<Long>>> selectMenu(Long id) {
        List<SysMenu> menuList = this.menuService.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getType, 2));
        List<TreeNode<Long>> treeNodeList = menuList.stream().map(
                menu -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(menu.getId());
                    treeNode.setParentId(menu.getParentId());
                    treeNode.setName(menu.getName());
                    Map<String, Object> leafMap = Maps.newHashMap();
                    if (Objects.equals(menu.getId(), id)) {
                        leafMap.put("disabled", Boolean.TRUE);
                    }
                    leafMap.put("label", menu.getTitle());
                    leafMap.put("value", menu.getId());
                    treeNode.setExtra(leafMap);
                    return treeNode;
                }
        ).collect(Collectors.toList());
        return Result.ok(TreeUtil.build(treeNodeList, ROOT));
    }

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPlatform() {
        List<SysPlatform> platformList = this.platformService.list();
        List<Map<String, Object>> collect = platformList.stream().map(sysPlatform -> {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("value", sysPlatform.getId());
            map.put("label", sysPlatform.getPlatformName());
            return map;
        }).collect(Collectors.toList());
        return Result.ok(collect);
    }

    /**
     * 部门下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<?>> selectDept(Long id) {
        return Result.ok(this.deptService.listDept(DeptQuery.builder().id(id).build()));
    }

    /**
     * 用户列表
     *
     * @param deptId 部门ID
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @Override
    public Result<List<SysUser>> listUser(Long deptId) {
        return Result.ok(this.userService.list(Wrappers.<SysUser>lambdaQuery().eq(Objects.nonNull(deptId), SysUser::getDeptId, deptId)));
    }

    /**
     * 角色下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectRole() {
        return Result.ok(this.roleService.list().stream().map(sysRole -> {
            Map<@Nullable String, @Nullable Object> roleMap = Maps.newHashMap();
            roleMap.put("value", sysRole.getId());
            roleMap.put("label", sysRole.getRoleName());
            return roleMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectTenant() {
        return Result.ok(this.tenantService.list().stream().map(tenant -> {
            Map<@Nullable String, @Nullable Object> tenantMap = Maps.newHashMap();
            tenantMap.put("value", String.valueOf(tenant.getId()));
            tenantMap.put("label", tenant.getTenantName());
            return tenantMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPost() {
        return Result.ok(this.postService.list().stream().map(post -> {
            Map<@Nullable String, @Nullable Object> postMap = Maps.newHashMap();
            postMap.put("value", post.getId());
            postMap.put("label", post.getPostName());
            return postMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 表名下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectTable() {
        return Result.ok(this.sysMateService.selectTable());
    }

    /**
     * 字段下拉框
     *
     * @param tableName 表名
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectTableColumn(String tableName) {
        return Result.ok(this.sysMateService.selectTableColumn(tableName));
    }

    /**
     * 数据权限类型下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPermissionType() {
        return Result.ok(Arrays.stream(DataPermissionType.values()).map(permission -> {
            Map<@Nullable String, @Nullable Object> permissionMap = Maps.newHashMap();
            permissionMap.put("value", permission.getType());
            permissionMap.put("label", permission.getDesc());
            if (StrUtil.equals(DataPermissionType.CUSTOMIZES.getType(), permission.getType())) {
                permissionMap.put("flag", Boolean.TRUE);
            }
            return permissionMap;
        }).collect(Collectors.toList()));
    }

    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectCustomizePermission() {
        return Result.ok(this.sysRowPermissionService.list().stream().map(permission -> {
            Map<@Nullable String, @Nullable Object> customizePermissionMap = Maps.newHashMap();
            customizePermissionMap.put("value", permission.getId());
            customizePermissionMap.put("label", permission.getPermissionName());
            return customizePermissionMap;
        }).collect(Collectors.toList()));
    }
    /**
     * 上传minio s3
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public Result<Map<String, Object>> uploadMinioS3(FileForm fileForm,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.sysFileService.uploadMinioS3(fileForm, request, response);
    }

    /**
     * 文件上传到本地存储
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public Result<Map<String, Object>> uploadLocalStorage(FileForm fileForm,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        return this.sysFileService.uploadLocalStorage(fileForm, request, response);
    }

    /**
     * 下载
     *
     * @param fileId   文件id
     * @param response 响应
     */
    @Override
    public void download(Long fileId, HttpServletResponse response) {
        this.sysFileService.download(fileId, response);
    }

}
