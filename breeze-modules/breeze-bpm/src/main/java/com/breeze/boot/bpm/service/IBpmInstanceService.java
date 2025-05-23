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
import com.breeze.boot.bpm.model.form.BpmApprovalForm;
import com.breeze.boot.bpm.model.form.BpmStartForm;
import com.breeze.boot.bpm.model.query.BpmInstanceQuery;
import com.breeze.boot.bpm.model.vo.BpmInstanceVO;
import com.breeze.boot.core.utils.Result;

import java.util.List;

/**
 * 流程实例管理服务接口
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
public interface IBpmInstanceService {

    /**
     * 发起
     *
     * @param startForm 流程启动参数
     * @return {@link Boolean}
     */
    Result<String> startProcess(BpmStartForm startForm);

    /**
     * 已暂停
     *
     * @param procInstId 流程实例ID
     * @return {@link Result }<{@link Boolean }>
     */
    Result<Boolean> suspendedInstance(String procInstId);

    /**
     * 列表页面
     *
     * @param query 流程实例查询
     * @return {@link Page}<{@link BpmInstanceVO}>
     */
    Page<BpmInstanceVO> listPage(BpmInstanceQuery query);

    /**
     * 作废
     *
     * @param form 流程审批参数
     */
    void voidProcess(BpmApprovalForm form);

    void remove(List<String> idList);

}
