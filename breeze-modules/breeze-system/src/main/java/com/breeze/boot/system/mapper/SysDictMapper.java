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

package com.breeze.boot.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.system.model.entity.SysDict;
import com.breeze.boot.system.model.query.DictQuery;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统字典映射器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Mapper
public interface SysDictMapper extends BreezeBaseMapper<SysDict> {

    /**
     * 字典列表
     *
     * @param page      分页
     * @param dictQuery 字典查询
     * @return {@link Page}<{@link SysDict}>
     */
    @DymicSql
    Page<SysDict> listPage(Page<SysDict> page, @ConditionParam @Param("dictQuery") DictQuery dictQuery);

}
