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

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.bo.SysDeptBO;
import com.breeze.boot.modules.auth.model.entity.SysDept;
import com.breeze.boot.modules.auth.model.form.DeptForm;
import com.breeze.boot.modules.auth.model.query.DeptQuery;

import java.util.List;

/**
 * 系统部门服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 部门列表
     *
     * @param deptQuery 部门查询
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    List<?> listDept(DeptQuery deptQuery);

    /**
     * 保存dept
     *
     * @param deptForm dept表单
     * @return {@link Boolean }
     */
    Boolean saveDept(DeptForm deptForm);

    /**
     * 按id更新部门
     *
     * @param deptForm dept表单
     * @return {@link Boolean }
     */
    Boolean modifyDept(Long id, DeptForm deptForm);

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> deleteById(Long id);

    /**
     * 根据父id查询部门
     *
     * @param deptId deptId
     * @return {@link List}<{@link SysDept}>
     */
    List<Long> listDeptByParentId(Long deptId);

    /**
     * 列出子部门id
     *
     * @param deptId 深度id
     * @return {@link List }<{@link SysDeptBO }>
     */
    List<SysDeptBO> listSubDeptId(Long deptId);

    /**
     * 部门下拉框
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    Result<List<?>> selectDept(Long id);
}

