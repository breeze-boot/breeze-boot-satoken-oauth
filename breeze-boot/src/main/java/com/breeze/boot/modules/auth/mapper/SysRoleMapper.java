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

package com.breeze.boot.modules.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.modules.auth.model.bo.RoleBO;
import com.breeze.boot.modules.auth.model.bo.UserRoleBO;
import com.breeze.boot.modules.auth.model.entity.SysRole;
import com.breeze.boot.modules.auth.model.query.RoleQuery;
import com.breeze.boot.modules.auth.model.vo.RoleVO;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色映射器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Mapper
public interface SysRoleMapper extends BreezeBaseMapper<SysRole> {

    /**
     * 列表页面
     *
     * @param page      页面
     * @param roleQuery 角色查询
     * @return {@link Page}<{@link SysRole}>
     */
    Page<RoleBO> listPage(Page<SysRole> page, @Param("roleQuery") RoleQuery roleQuery);

    /**
     * 用户角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link SysRole}>
     */
    List<UserRoleBO> listRoleByUserId(@Param("userId") Long userId);

    /**
     * 用户角色ids列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> listRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 用户角色名称列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<String> listRoleNamesByUserId(@Param("userId") Long userId);

    /**
     * 获取用户角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link RoleVO}>
     */
    List<RoleVO> listUserRoles(@Param("userId") Long userId);

}
