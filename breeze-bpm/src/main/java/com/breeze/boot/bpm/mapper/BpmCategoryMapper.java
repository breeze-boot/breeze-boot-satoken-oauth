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

package com.breeze.boot.bpm.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.bpm.model.entity.BpmCategory;
import com.breeze.boot.bpm.model.query.BpmCategoryQuery;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程分类映射器
 *
 * @author gaoweixuan
 * @since 2023-03-06
 */
@Mapper
public interface BpmCategoryMapper extends BreezeBaseMapper<BpmCategory> {

    /**
     * 列表页面
     *
     * @param page     页面
     * @param category 类别
     * @return {@link IPage}<{@link BpmCategory}>
     */
    @DymicSql
    Page<BpmCategory> listPage(Page<BpmCategory> page, @ConditionParam @Param("category") BpmCategoryQuery category);

}
