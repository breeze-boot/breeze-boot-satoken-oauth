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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysMenuColumn;
import com.breeze.boot.modules.auth.model.form.MenuColumnForm;
import com.breeze.boot.modules.auth.model.query.MenuColumnQuery;
import com.breeze.boot.modules.auth.model.vo.MenuColumnVO;
import com.breeze.boot.modules.auth.model.vo.RolesMenuColumnVO;

import java.util.List;

/**
 * 系统列数据权限服务
 *
 * @author gaoweixuan
 * @since 2024-07-17
 */
public interface SysMenuColumnService extends IService<SysMenuColumn> {


    List<RolesMenuColumnVO> getRolesMenuColumns();

    /**
     * 列表页面
     *
     * @param query 权限查询
     * @return {@link Page}<{@link MenuColumnVO}>
     */
    Page<MenuColumnVO> listPage(MenuColumnQuery query);

    /**
     * 按id获取信息
     *
     * @param menuColumnId 权限id
     * @return {@link MenuColumnVO }
     */
    MenuColumnVO getInfoById(Long menuColumnId);

    /**
     * 保存
     *
     * @param form 菜单栏表单
     * @return {@link Result }<{@link Boolean }>
     */
    Result<Boolean> saveMenuColumn(MenuColumnForm form);

    /**
     * 删除数据权限通过IDS
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeMenuColumnByIds(List<Long> ids);

}
