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
import com.breeze.boot.auth.model.entity.SysRole;
import com.breeze.boot.auth.model.entity.SysUserRole;

import java.util.List;

/**
 * 系统用户角色服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 使用 [userId] 查询用户角色
     *
     * @param userId 用户ID
     * @return {@link List}<{@link SysRole}>
     */
    List<SysRole> getSysRoleByUserId(Long userId);

}

