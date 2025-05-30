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
import com.breeze.boot.bpm.model.form.BpmDefinitionDeleteForm;
import com.breeze.boot.bpm.model.form.BpmDesignXmlFileForm;
import com.breeze.boot.bpm.model.form.BpmDesignXmlStringForm;
import com.breeze.boot.bpm.model.vo.BpmDefinitionVO;
import com.breeze.boot.bpm.model.vo.XmlVO;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.bpm.model.query.BpmDefinitionQuery;

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
     * @param form 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    Result<String> deploy(BpmDesignXmlStringForm form);

    /**
     * 部署
     *
     * @param form 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    Result<String> deploy(BpmDesignXmlFileForm form);

    /**
     * 列表页面
     *
     * @param query 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    Page<BpmDefinitionVO> listPage(BpmDefinitionQuery query);

    /**
     * 挂起/激活
     *
     * @param definitionId 流程定义ID
     * @return {@link Result }<{@link Boolean }>
     */
    Result<Boolean> suspendedDefinition(String definitionId);

    /**
     * 获取流程定义png
     * <p>
     *  查看某版本的流程定义信息
     *
     * @param procDefKey 流程定义Key
     * @param version    版本
     * @return {@link Result}<{@link ?}>
     */
    String getBpmDefinitionPng(String procDefKey, Integer version);

    /**
     * 获取流程定义xml
     * <p>
     * 查看某版本的流程定义信息
     *
     * @param procDefKey 流程定义Key
     * @param version    版本
     * @return {@link XmlVO }
     */
    XmlVO getBpmDefinitionXml(String procDefKey, Integer version);

    /**
     * 版本列表页面
     *
     * @param query 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    Page<BpmDefinitionVO> listVersionPage(BpmDefinitionQuery query);

    /**
     * 删除
     *
     * @param formList 流定义删除参数列表
     * @return {@link Boolean}
     */
    Boolean delete(List<BpmDefinitionDeleteForm> formList);

    /**
     * 详情
     *
     * @param procDefId 定义id
     * @return {@link BpmDefinitionVO }
     */
    BpmDefinitionVO getInfo(String procDefId);

}
