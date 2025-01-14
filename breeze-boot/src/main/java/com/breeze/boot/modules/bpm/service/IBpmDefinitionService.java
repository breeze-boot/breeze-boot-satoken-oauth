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

package com.breeze.boot.modules.bpm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.bpm.model.form.BpmDefinitionDeleteForm;
import com.breeze.boot.modules.bpm.model.form.BpmDesignXmlFileForm;
import com.breeze.boot.modules.bpm.model.form.BpmDesignXmlStringForm;
import com.breeze.boot.modules.bpm.model.query.BpmDefinitionQuery;
import com.breeze.boot.modules.bpm.model.vo.BpmDefinitionVO;
import com.breeze.boot.modules.bpm.model.vo.XmlVO;

import java.util.List;


/**
 * 流程资源管理服务接口
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
public interface IBpmDefinitionService {

    /**
     * 部署
     *
     * @param bpmDesignXmlStringForm 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    Result<String> deploy(BpmDesignXmlStringForm bpmDesignXmlStringForm);

    /**
     * 部署
     *
     * @param bpmDesignXmlFileForm 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    Result<String> deploy(BpmDesignXmlFileForm bpmDesignXmlFileForm);

    /**
     * 列表页面
     *
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    Page<BpmDefinitionVO> listPage(BpmDefinitionQuery bpmDefinitionQuery);

    /**
     * 挂起/激活
     *
     * @param definitionId 流程定义ID
     * @return {@link Result }<{@link Boolean }>
     */
    Result<Boolean> suspendedDefinition(String definitionId);

    /**
     * 获得版本流程定义png
     *
     * @param procInstId 流程实例ID
     * @return {@link Result}<{@link ?}>
     */
    String getBpmDefinitionPng(String procInstId);

    /**
     * 获得版本流程定义xml
     *
     * @param procInstId 流程实例ID
     * @return {@link XmlVO }
     */
    XmlVO getBpmDefinitionXml(String procInstId);

    /**
     * 版本列表页面
     *
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    Page<BpmDefinitionVO> listVersionPage(BpmDefinitionQuery bpmDefinitionQuery);

    /**
     * 删除
     *
     * @param bpmDefinitionDeleteFormList 流定义删除参数列表
     * @return {@link Boolean}
     */
    Boolean delete(List<BpmDefinitionDeleteForm> bpmDefinitionDeleteFormList);

    /**
     * 详情
     *
     * @param procDefId 定义id
     * @return {@link BpmDefinitionVO }
     */
    BpmDefinitionVO getInfo(String procDefId);

}
