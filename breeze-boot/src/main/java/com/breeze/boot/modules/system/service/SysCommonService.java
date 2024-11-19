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

package com.breeze.boot.modules.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.modules.system.model.form.FileForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
public interface SysCommonService {

    /**
     * 菜单树形下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<Tree<Long>>> selectMenu(Long id);

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectPlatform();

    /**
     * 部门下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<?>> selectDept(Long id);

    /**
     * 用户列表
     *
     * @param deptId 部门ID
     * @return {@link Result}<{@link List}<{@link SysUser}>>
     */
    Result<List<SysUser>> listUser(Long deptId);

    /**
     * 角色下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectRole();

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectTenant();

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectPost();

    /**
     * 表名下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectTable();

    /**
     * 字段下拉框
     *
     * @param tableName 表名
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectTableColumn(String tableName);

    /**
     * 数据权限类型下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectPermissionType();

    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectCustomizePermission();

    /**
     * 文件上传到minio
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<Map<String, Object>> uploadMinioS3(FileForm fileForm,
                                              HttpServletRequest request,
                                              HttpServletResponse response);

    /**
     * 文件上传到本地存储
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link ?}>
     */
    Result<Map<String, Object>> uploadLocalStorage(FileForm fileForm,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response);

    /**
     * 下载
     *
     * @param fileId     文件标识
     * @param response   响应
     */
    void download(Long fileId,  HttpServletResponse response);

    /**
     * 客户端下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link String }>>>
     */
    Result<List<Map<String, String>>> selectRegisteredClient();

}
