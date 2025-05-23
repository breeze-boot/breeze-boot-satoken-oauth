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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.bpm.model.entity.ActRuExecution;
import com.breeze.boot.bpm.model.query.BpmInstanceQuery;
import com.breeze.boot.bpm.model.vo.BpmInstanceVO;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程执行实例映射器
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Mapper
public interface ActRuExecutionMapper extends BaseMapper<ActRuExecution> {

    /**
     * 列表页面
     *
     * @param page             页面
     * @param bpmInstanceQuery 流程实例查询
     * @return {@link Page}<{@link BpmInstanceVO}>
     */
    @DymicSql
    Page<BpmInstanceVO> listPage(Page<BpmInstanceVO> page, @ConditionParam @Param("processInstanceQuery") BpmInstanceQuery bpmInstanceQuery);

}
