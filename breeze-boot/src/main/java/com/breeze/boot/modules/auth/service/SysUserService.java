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
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.modules.auth.model.form.UserForm;
import com.breeze.boot.modules.auth.model.form.UserOpenForm;
import com.breeze.boot.modules.auth.model.form.UserResetForm;
import com.breeze.boot.modules.auth.model.form.UserRolesForm;
import com.breeze.boot.modules.auth.model.query.UserQuery;
import com.breeze.boot.modules.auth.model.vo.UserVO;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 系统用户服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysUserService extends IService<SysUser>, IUserDetailService {

    /**
     * 列表页面
     *
     * @param query 用户查询
     * @return {@link Page}<{@link UserVO}>
     */
    Page<UserVO> listPage(UserQuery query);

    /**
     * 通过ID查询用户
     *
     * @param id id
     * @return {@link UserVO }
     */
    UserVO getInfoById(Long id);

    /**
     * 保存用户
     *
     * @param form 系统用户
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> saveUser(UserForm form);

    /**
     * 通过id更新用户
     *
     * @param userForm 用户表单
     * @return {@link Boolean}
     */
    Boolean modifyUser(Long id, UserForm userForm);

    /**
     * 开关
     *
     * @param form 用户打开表单
     * @return {@link Boolean}
     */
    Boolean open(UserOpenForm form);

    /**
     * 重置密码
     *
     * @param form 用户重置密码表单
     * @return {@link Boolean}
     */
    Boolean reset(UserResetForm form);

    /**
     * 删除用户
     *
     * @param ids 用户ID
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeUser(List<Long> ids);

    /**
     * 用户分配角色
     *
     * @param form 用户角色表单
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> setRole(UserRolesForm form);

    /**
     * 注册用户
     *
     * @param sysUser  系统用户
     * @param roleCode 角色代码
     * @return {@link SysUser}
     */
    SysUser registerUser(SysUser sysUser, String roleCode);

    /**
     * 查询用户通过部门id
     *
     * @param deptIds 部门IDS
     * @return {@link List}<{@link SysUser}>
     */
    List<UserVO> listUserByDeptId(List<Long> deptIds);

    /**
     * 导出
     *
     * @param userQuery 用户查询
     * @param response  响应
     */
    void export(UserQuery userQuery, HttpServletResponse response);

    /**
     * 获取部门用户
     *
     * @param deptId 部门ID
     * @return {@link List}<{@link SysUser}>
     */
    List<SysUser> listDeptsUser(Long deptId);

    /**
     * 获取用户通过角色
     *
     * @param roleCode 角色编码
     * @return {@link List }<{@link SysUser }>
     */
    List<SysUser> listUserByRole(String roleCode);

    /**
     * 同步用户角色和用户角色关系
     */
    void syncFlowableUser();

    /**
     * 用户列表
     *
     * @param deptId 部门ID
     * @return {@link Result }<{@link List }<{@link SysUser }>>
     */
    Result<List<SysUser>> listDeptUser(Long deptId);

}
