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

package com.breeze.boot.sys.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.sys.domain.SysRole;
import com.breeze.boot.sys.dto.RoleSearchDTO;
import com.breeze.core.utils.Result;
import com.breeze.database.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色映射器
 *
 * @author gaoweixuan
 * @date 2021-12-06 22:03:39
 */
@Mapper
public interface SysRoleMapper extends BreezeBaseMapper<SysRole> {

    /**
     * 用户角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link SysRole}>
     */
    List<SysRole> listRoleByUserId(@Param("userId") Long userId);

    /**
     * 用户角色ids列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> listRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 列表页面
     *
     * @param rolePage      角色页面
     * @param roleSearchDTO 角色搜索dto
     * @return {@link Page}<{@link SysRole}>
     */
    Page<SysRole> listPage(Page<SysRole> rolePage, @Param("roleSearchDTO") RoleSearchDTO roleSearchDTO);

    /**
     * 获取用户角色列表
     *
     * @param userId 用户id
     * @return {@link Result}<{@link List}<{@link Long}>>
     */
    List<Long> listUserRoles(@Param("userId") Long userId);

}