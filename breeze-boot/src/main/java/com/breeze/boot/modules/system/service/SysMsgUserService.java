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
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.message.dto.UserMsgDTO;
import com.breeze.boot.modules.system.model.entity.SysMsgUser;
import com.breeze.boot.modules.system.model.query.UserMsgQuery;
import com.breeze.boot.modules.system.model.vo.MsgUserVO;

import java.util.List;

/**
 * 系统用户消息服务
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
public interface SysMsgUserService extends IService<SysMsgUser> {

    /**
     * 列表页面
     *
     * @param userMsgQuery 用户消息查询
     * @return {@link IPage}<{@link MsgUserVO}>
     */
    IPage<MsgUserVO> listPage(UserMsgQuery userMsgQuery);

    /**
     * 获取用户的消息
     *
     * @param username 用户名
     * @return {@link List}<{@link MsgUserVO}>
     */
    List<MsgUserVO> listUsersMsg(String username);

    /**
     * 保存用户的消息
     *
     * @param userMsgDTO 用户味精dto
     */
    void saveUserMsg(UserMsgDTO userMsgDTO);

    /**
     * 关闭
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> close(Long msgId);

    /**
     * 标记已读
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> read(Long msgId);

    /**
     * 删除用户的消息通过ids
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeUserMsgByIds(List<Long> ids);

}
