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
import com.breeze.boot.modules.auth.model.bo.UserBO;
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.modules.auth.model.query.UserQuery;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户映射器
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Mapper
public interface SysUserMapper extends BreezeBaseMapper<SysUser> {

    /**
     * 列表页面
     *
     * @param page      分页
     * @param userQuery 用户查询
     * @return {@link Page}<{@link UserBO}>
     */
    @DymicSql
    Page<UserBO> listPage(Page<SysUser> page, @ConditionParam @Param("userQuery") UserQuery userQuery);

    /**
     * 用户通过部门id列表
     *
     * @param deptIds 部门id
     * @return {@link List}<{@link SysUser}>
     */
    List<UserBO> listUserByDeptId(@Param("deptIds") List<Long> deptIds);

    /**
     * 获取用户通过角色
     *
     * @param roleCode 角色编码
     * @return {@link List }<{@link SysUser }>
     */
    List<SysUser> listUserByRole(String roleCode);

}
