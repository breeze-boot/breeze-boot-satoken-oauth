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

package com.breeze.boot.modules.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.modules.bpm.model.entity.ActReDeployment;
import com.breeze.boot.modules.bpm.model.query.BpmDefinitionQuery;
import com.breeze.boot.modules.bpm.model.vo.BpmDefinitionVO;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程部署映射器
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Mapper
public interface ActReDeploymentMapper extends BaseMapper<ActReDeployment> {

    /**
     * 列表页面
     *
     * @param page               页面
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    @DymicSql
    Page<BpmDefinitionVO> listPage(Page<BpmDefinitionVO> page, @ConditionParam @Param("processDeploymentQuery") BpmDefinitionQuery bpmDefinitionQuery);

    /**
     * 详情
     *
     * @param definitionId 定义id
     * @return {@link BpmDefinitionVO }
     */
    BpmDefinitionVO getInfo(String definitionId);

}
