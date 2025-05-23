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

package com.breeze.boot.bpm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.bpm.model.entity.BpmCategory;
import com.breeze.boot.bpm.model.form.BpmCategoryForm;
import com.breeze.boot.bpm.model.query.BpmCategoryQuery;
import com.breeze.boot.bpm.model.vo.BpmCategoryVO;
import com.breeze.boot.core.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 流程分类服务
 *
 * @author gaoweixuan
 * @since 2023-03-06
 */
public interface IBpmCategoryService extends IService<BpmCategory> {

    /**
     * 列表页面
     *
     * @param query 流程分类
     * @return {@link Page}<{@link BpmCategoryVO}>
     */
    Page<BpmCategoryVO> listPage(BpmCategoryQuery query);

    /**
     * 按id获取信息
     *
     * @param categoryId 类别id
     * @return {@link BpmCategory }
     */
    BpmCategoryVO getInfoById(Long categoryId);

    /**
     * 保存流类别
     *
     * @param form 流程分类表单
     * @return {@link Boolean }
     */
    Boolean saveFlowCategory(BpmCategoryForm form);

    /**
     * 修改流类别
     *
     * @param id   ID
     * @param form 流程分类表单
     * @return {@link Boolean }
     */
    Boolean modifyFlowCategory(Long id, BpmCategoryForm form);

    /**
     * 类别下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link Object }>>>
     */
    Result<List<Map<String, Object>>> selectCategory();

}
