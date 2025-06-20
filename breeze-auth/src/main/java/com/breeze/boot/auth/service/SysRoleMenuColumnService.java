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

package com.breeze.boot.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.auth.model.entity.SysRoleMenuColumn;
import com.breeze.boot.auth.model.form.RoleMenuColumnForm;
import com.breeze.boot.core.utils.Result;

/**
 * 系统角色菜单字段数据权限服务
 *
 * @author gaoweixuan
 * @since 2024-02-18
 */
public interface SysRoleMenuColumnService extends IService<SysRoleMenuColumn> {

    /**
     * 保存角色列权限
     *
     * @param form 角色菜单栏表单
     * @return {@link Result }<{@link Boolean }>
     */
    Result<Boolean> saveRoleMenuColumn(RoleMenuColumnForm form);

}
