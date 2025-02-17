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

package com.breeze.boot.modules.auth.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.bo.UserRoleBO;
import com.breeze.boot.modules.auth.model.entity.SysMenu;
import com.breeze.boot.modules.auth.model.form.MenuForm;
import com.breeze.boot.modules.auth.model.query.MenuQuery;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 用户菜单权限列表
     *
     * @param userRoleBOList 角色列表
     * @return {@link List}<{@link String}>
     */
    Set<String> listUserMenuPermission(List<UserRoleBO> userRoleBOList);

    /**
     * 树菜单列表
     *
     * @param platformCode 平台代码
     * @param i18n         国际化
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<Tree<Long>>> listTreeMenu(String platformCode, String i18n);

    /**
     * 菜单列表
     *
     * @param menuQuery 菜单查询
     * @return {@link Result}<{@link ?}>
     */
    Result<?> listMenu(MenuQuery menuQuery);

    /**
     * 树形权限列表
     *
     * @param type 类型
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<Tree<Long>>> listTreePermission(List<Integer> type);


    /**
     * 保存菜单
     *
     * @param menuForm 菜单表单
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> saveMenu(MenuForm menuForm);

    /**
     * 修改菜单
     *
     * @param id          id
     * @param menuForm 菜单表单
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> modifyMenu(Long id, MenuForm menuForm);

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deleteMenu(Long id);

    /**
     * 菜单树形下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<Tree<Long>>> selectMenu(Long id);

    /**
     * 按角色编码列出用户菜单权限
     *
     * @param roleCode 角色代码
     * @return {@link List }<{@link String }>
     */
    List<String> listUserPermissionByRoleCode(String roleCode);

}

