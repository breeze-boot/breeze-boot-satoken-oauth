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
import com.breeze.boot.modules.auth.model.entity.SysMenuColumn;
import com.breeze.boot.modules.auth.model.query.MenuColumnQuery;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 系统列数据权限映射器
 *
 * @author gaoweixuan
 * @since 2024-07-17
 */
@Mapper
public interface SysMenuColumnMapper extends BreezeBaseMapper<SysMenuColumn> {

    /**
     * 列表分页
     *
     * @param permissionQuery 数据权限查询
     * @param page            页面
     * @return {@link Page}<{@link SysMenuColumn}>
     */
    @DymicSql
    Page<SysMenuColumn> listPage(Page<SysMenuColumn> page,@ConditionParam @Param("permissionQuery") MenuColumnQuery permissionQuery);

    /**
     * 获取角色的菜单列
     *
     * @param userRoleIds 用户角色ID
     * @return {@link List }<{@link SysMenuColumn }>
     */
    List<SysMenuColumn> getRolesMenuColumns(@Param("userRoleIds") Set<Long> userRoleIds);

}
