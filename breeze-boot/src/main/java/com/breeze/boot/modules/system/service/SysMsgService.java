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

package com.breeze.boot.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.message.vo.MsgVO;
import com.breeze.boot.modules.system.model.entity.SysMsg;
import com.breeze.boot.modules.system.model.form.MsgForm;
import com.breeze.boot.modules.system.model.query.MsgQuery;

/**
 * 系统消息服务
 *
 * @author gaoweixuan
 * @since 2022-11-20
 */
public interface SysMsgService extends IService<SysMsg> {

    /**
     * 列表页面
     *
     * @param query 消息查询
     * @return {@link IPage}<{@link MsgVO}>
     */
    IPage<MsgVO> listPage(MsgQuery query);

    /**
     * 按id获取信息
     *
     * @param id ID
     * @return {@link MsgVO }
     */
    MsgVO getInfoById(Long id);

    /**
     * 保存消息
     *
     * @param form 消息表单
     * @return {@link Boolean }
     */
    Boolean saveMsg(MsgForm form);

    /**
     * 修改消息
     *
     * @param id      ID
     * @param form 消息表单
     * @return {@link Boolean }
     */
    Boolean modifyMsg(Long id, MsgForm form);

}
