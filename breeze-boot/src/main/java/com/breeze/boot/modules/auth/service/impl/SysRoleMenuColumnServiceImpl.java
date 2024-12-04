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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysRoleMenuColumnMapper;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenuColumn;
import com.breeze.boot.modules.auth.model.form.RoleMenuColumnForm;
import com.breeze.boot.modules.auth.service.SysRoleMenuColumnService;
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
     * @param roleMenuColumnForm 角色菜单栏表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveRoleMenuColumn(RoleMenuColumnForm roleMenuColumnForm) {
        this.remove(Wrappers.<SysRoleMenuColumn>lambdaQuery().eq(SysRoleMenuColumn::getRoleId, roleMenuColumnForm.getRoleId()));
        return Result.ok(this.saveBatch(roleMenuColumnForm.getMenu().stream().map(menu -> {
            SysRoleMenuColumn sysRoleMenuColumn = new SysRoleMenuColumn();
            sysRoleMenuColumn.setMenu(menu);
            sysRoleMenuColumn.setRoleId(roleMenuColumnForm.getRoleId());
            return sysRoleMenuColumn;
        }).collect(Collectors.toList())));
    }
}
