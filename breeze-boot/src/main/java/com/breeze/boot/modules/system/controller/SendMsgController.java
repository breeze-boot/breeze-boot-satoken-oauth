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

package com.breeze.boot.modules.system.controller;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.message.params.MsgParam;
import com.breeze.boot.message.service.WebSocketMsgService;
import com.breeze.boot.message.vo.MsgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 发送消息模块接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "发送消息模块", description = "SendMsgController")
public class SendMsgController {

    /**
     * 消息服务
     */
    private final WebSocketMsgService webSocketMsgService;

    /**
     * 消息广播
     *
     * @param msgParam 广播消息
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "广播消息")
    // 前端发送信息的路径
    @MessageMapping("/asyncSendBroadcastMsg")
    // 发送到websocket的路径，广播消息的路径/topic，发送的通道和订阅者确定后可通过这个路径接收消息/msg
    @SendTo("/topic/message")
    public Result<MsgVO> asyncSendBroadcastMsg(@Payload MsgParam msgParam) {
        return this.webSocketMsgService.asyncSendBroadcastMsg(msgParam);
    }

    /**
     * 发送消息给用户
     *
     * @param principal 主要
     * @param msgParam  用户消息
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "发送消息给用户")
    // 前端发送信息的路径
    @MessageMapping("/asyncSendMsgToSingleUser")
    // 发送到websocket的路径，/点对点发送的路径/queue，发送的通道和订阅者确定后可通过这个路径接收消息/message
    @SendToUser("/queue/message")
    public Result<MsgVO> asyncSendMsgToSingleUser(Principal principal, @Payload MsgParam msgParam) {
        return this.webSocketMsgService.asyncSendMsgToSingleUser(principal, msgParam);
    }

    /**
     * 发送消息给用户
     *
     * @param principal 主要
     * @param msgParam  用户消息
     * @return {@link Result}<{@link MsgVO}>
     */
    @Operation(summary = "发送消息给部门以及子部门")
    // 前端发送信息的路径
    @MessageMapping("/syncSendMsgDeptUser")
    // 发送到websocket的路径，/点对点发送的路径/queue，发送的通道和订阅者确定后可通过这个路径接收消息/message
    @SendToUser("/queue/message")
    public Result<MsgVO> syncSendMsgDeptUser(Principal principal, @Payload MsgParam msgParam) {
        return this.webSocketMsgService.syncSendMsgDeptUser(principal, msgParam);
    }

    /**
     * 发送信息给指定的用户
     * <p>
     * 使用另一种实现方式
     * </p>
     * @param principal 主要
     * @param msgParam  用户消息
     */
    @Operation(summary = "发送信息给指定的用户")
    // 前端发送信息的路径
    @MessageMapping("/asyncSendMsgToUser")
    public void asyncSendMsgToUser(Principal principal, @Payload MsgParam msgParam) {
        this.webSocketMsgService.asyncSendMsgToUser(principal, msgParam);
    }

}
