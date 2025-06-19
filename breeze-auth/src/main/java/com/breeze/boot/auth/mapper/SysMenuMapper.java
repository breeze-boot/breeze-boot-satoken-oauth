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

package com.breeze.boot.auth.mapper;

import com.breeze.boot.auth.model.bo.SysMenuBO;
import com.breeze.boot.auth.model.bo.UserRoleBO;
import com.breeze.boot.auth.model.entity.SysMenu;
import com.breeze.boot.auth.model.query.MenuQuery;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单映射器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Mapper
public interface SysMenuMapper extends BreezeBaseMapper<SysMenu> {

    /**
     * 用户菜单权限列表
     *
     * @param userRoleList 角色列表
     * @return {@link List}<{@link String}>
     */
    Set<String> listUserMenuPermission(@Param("userRoleList") List<UserRoleBO> userRoleList);

    /**
     * 菜单列表
     *
     * @param menuQuery 菜单查询
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenuBO> listMenu(@Param("menuQuery") MenuQuery menuQuery);

    /**
     * 查询菜单
     *
     * @param roleIds      用户角色id
     * @param platformCode 平台编码
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenuBO> selectMenusByRoleId(@Param("roleIds") Set<Long> roleIds,
                                        @Param("platformCode") String platformCode);

    /**
     * 按角色编码列出用户权限
     *
     * @param roleCode 角色代码
     * @return {@link List }<{@link String }>
     */
    List<String> listUserPermissionByRoleCode(String roleCode);

}
