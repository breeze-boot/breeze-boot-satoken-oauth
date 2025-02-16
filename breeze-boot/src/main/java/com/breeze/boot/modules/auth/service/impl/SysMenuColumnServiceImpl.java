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
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysMenuColumnMapper;
import com.breeze.boot.modules.auth.model.entity.SysMenuColumn;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenuColumn;
import com.breeze.boot.modules.auth.model.form.MenuColumnForm;
import com.breeze.boot.modules.auth.model.mappers.SysMenuColumnMapStruct;
import com.breeze.boot.modules.auth.model.query.MenuColumnQuery;
import com.breeze.boot.modules.auth.model.vo.MenuColumnVO;
import com.breeze.boot.modules.auth.model.vo.RolesMenuColumnVO;
import com.breeze.boot.modules.auth.model.vo.RowPermissionVO;
import com.breeze.boot.modules.auth.service.SysMenuColumnService;
import com.breeze.boot.modules.auth.service.SysRoleMenuColumnService;
import com.breeze.boot.satoken.utils.BreezeStpUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统列数据权限服务 impl
 *
 * @author gaoweixuan
 * @since 2024-07-17
 */
@Service
@RequiredArgsConstructor
public class SysMenuColumnServiceImpl extends ServiceImpl<SysMenuColumnMapper, SysMenuColumn> implements SysMenuColumnService {

    private final SysMenuColumnMapStruct sysMenuColumnMapStruct;

    private final SysRoleMenuColumnService sysRoleMenuColumnService;

    /**
     * 获取所有列权限
     */
    @Override
    public List<RolesMenuColumnVO> getRolesMenuColumns() {
        List<SysMenuColumn> sysRowPermissionList = this.baseMapper.getRolesMenuColumns(BreezeStpUtil.getUser().getUserRoleIds());
        List<RolesMenuColumnVO> rolesMenuColumnList = Lists.newArrayList();
        Map<String, List<SysMenuColumn>> menuMapList = sysRowPermissionList.stream().collect(Collectors.groupingBy(SysMenuColumn::getMenu));
        menuMapList.forEach((menu, sysMenuColumnList) -> {
            RolesMenuColumnVO rolesMenuColumnVO = new RolesMenuColumnVO();
            rolesMenuColumnVO.setMenu(menu);
            List<String> columnList = sysMenuColumnList.stream()
                    .map(SysMenuColumn::getColumn)
                    .collect(Collectors.toList());
            rolesMenuColumnVO.setColumns(columnList);
            rolesMenuColumnList.add(rolesMenuColumnVO);
        });
        return rolesMenuColumnList;
    }

    /**
     * 列表页面
     *
     * @param permissionQuery 权限查询
     * @return {@link Page}<{@link RowPermissionVO}>
     */
    @Override
    public Page<MenuColumnVO> listPage(MenuColumnQuery permissionQuery) {
        Page<SysMenuColumn> page = new Page<>(permissionQuery.getCurrent(), permissionQuery.getSize());
        Page<SysMenuColumn> sysColumnPermissionPage = this.baseMapper.listPage(page, permissionQuery);
        return this.sysMenuColumnMapStruct.page2VOPage(sysColumnPermissionPage);
    }

    /**
     * 按id获取信息
     *
     * @param menuColumnId 权限id
     * @return {@link MenuColumnVO }
     */
    @Override
    public MenuColumnVO getInfoById(Long menuColumnId) {
        SysMenuColumn sysMenuColumn = this.getById(menuColumnId);
        return this.sysMenuColumnMapStruct.entity2VO(sysMenuColumn);
    }

    /**
     * 保存
     *
     * @param menuColumnForm 菜单栏表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveMenuColumn(MenuColumnForm menuColumnForm) {
        for (String column : menuColumnForm.getColumns()) {
            SysMenuColumn sysMenuColumn = new SysMenuColumn();
            sysMenuColumn.setMenu(menuColumnForm.getMenu());
            sysMenuColumn.setColumn(column);
            if (menuColumnForm.getVisible()) {
                List<SysMenuColumn> sysMenuColumnList = list(Wrappers.<SysMenuColumn>lambdaQuery().eq(SysMenuColumn::getColumn, column).eq(SysMenuColumn::getMenu, menuColumnForm.getMenu()));
                if (CollUtil.isNotEmpty(sysMenuColumnList) && sysMenuColumnList.size() == 1) {
                    this.sysRoleMenuColumnService.remove(Wrappers.<SysRoleMenuColumn>lambdaQuery().eq(SysRoleMenuColumn::getMenu, menuColumnForm.getMenu()));
                }
                this.remove(Wrappers.<SysMenuColumn>lambdaQuery().eq(SysMenuColumn::getColumn, column).eq(SysMenuColumn::getMenu, menuColumnForm.getMenu()));
            } else {
                this.save(sysMenuColumn);
            }
        }
        return Result.ok(Boolean.TRUE, "设置成功");
    }

    /**
     * 删除数据权限通过IDs
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeMenuColumnByIds(List<Long> ids) {
        return Result.ok(this.removeBatchByIds(ids));
    }

}
