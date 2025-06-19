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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.mapper.SysRoleMenuColumnMapper;
import com.breeze.boot.auth.model.entity.SysRoleMenuColumn;
import com.breeze.boot.auth.model.form.RoleMenuColumnForm;
import com.breeze.boot.auth.service.SysRoleMenuColumnService;
import com.breeze.boot.core.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * 系统角色菜单字段数据权限服务 impl
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuColumnServiceImpl extends ServiceImpl<SysRoleMenuColumnMapper, SysRoleMenuColumn> implements SysRoleMenuColumnService {

    /**
     * 保存角色列权限
     *
     * @param form 角色菜单栏表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveRoleMenuColumn(RoleMenuColumnForm form) {
        this.remove(Wrappers.<SysRoleMenuColumn>lambdaQuery().eq(SysRoleMenuColumn::getRoleId, form.getRoleId()));
        return Result.ok(this.saveBatch(form.getMenu().stream().map(menu -> {
            SysRoleMenuColumn sysRoleMenuColumn = new SysRoleMenuColumn();
            sysRoleMenuColumn.setMenu(menu);
            sysRoleMenuColumn.setRoleId(form.getRoleId());
            return sysRoleMenuColumn;
        }).collect(Collectors.toList())));
    }
}
