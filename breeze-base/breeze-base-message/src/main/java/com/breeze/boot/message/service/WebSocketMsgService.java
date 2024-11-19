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

package com.breeze.boot.message.service;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.message.params.BpmParam;
import com.breeze.boot.message.params.MsgParam;
import com.breeze.boot.message.vo.MsgVO;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

/**
 * 消息模块接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@Slf4j
public abstract class WebSocketMsgService {

    /**
     * 消息广播
     *
     * @param msgParam 广播消息参数
     * @return {@link Result}<{@link MsgVO}>
     */
    public abstract Result<MsgVO> asyncSendBroadcastMsg(MsgParam msgParam);

    /**
     * 发送消息给用户
     *
     * @param principal 主要
     * @param msgParam  消息参数
     * @return {@link Result}<{@link MsgVO}>
     */
    public abstract Result<MsgVO> asyncSendMsgToSingleUser(Principal principal, MsgParam msgParam);

    /**
     * 发送信息给指定用户
     *
     * @param principal 主要
     * @param msgParam  用户消息参数
     */
    public abstract void asyncSendMsgToUser(Principal principal, MsgParam msgParam);

    /**
     * 发送信息给指定用户
     *
     * @param bpmParam 用户消息参数
     */
    public abstract void asyncSendMsgToUser(BpmParam bpmParam);

    /**
     * 发送消息给部门以及子部门
     *
     * @param principal 主要
     * @param msgParam  消息参数
     * @return {@link Result}<{@link MsgVO}>
     */
    public abstract Result<MsgVO> syncSendMsgDeptUser(Principal principal, MsgParam msgParam);
}
