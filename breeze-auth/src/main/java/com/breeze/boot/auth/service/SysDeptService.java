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

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.auth.model.bo.SysDeptBO;
import com.breeze.boot.auth.model.entity.SysDept;
import com.breeze.boot.auth.model.form.DeptForm;
import com.breeze.boot.auth.model.query.DeptQuery;
import com.breeze.boot.core.utils.Result;

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
     * @param query 部门查询
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    List<?> listDept(DeptQuery query);

    /**
     * 保存dept
     *
     * @param form dept表单
     * @return {@link Boolean }
     */
    Boolean saveDept(DeptForm form);

    /**
     * 按id更新部门
     *
     * @param form dept表单
     * @return {@link Boolean }
     */
    Boolean modifyDept(Long id, DeptForm form);

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
     * @param deptId 部门id
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

