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

package com.breeze.boot.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.message.dto.UserMsgDTO;
import com.breeze.boot.modules.system.mapper.SysMsgUserMapper;
import com.breeze.boot.modules.system.model.entity.SysMsgUser;
import com.breeze.boot.modules.system.model.query.UserMsgQuery;
import com.breeze.boot.modules.system.model.vo.MsgUserVO;
import com.breeze.boot.modules.system.service.SysMsgUserService;
import com.breeze.boot.satoken.utils.BreezeStpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.NOT_FOUND;

/**
 * 系统用户消息服务impl
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
@Service
@RequiredArgsConstructor
public class SysMsgUserServiceImpl extends ServiceImpl<SysMsgUserMapper, SysMsgUser> implements SysMsgUserService {

    /**
     * 列表页面
     *
     * @param query 用户消息查询
     * @return {@link IPage}<{@link MsgUserVO}>
     */
    @Override
    public IPage<MsgUserVO> listPage(UserMsgQuery query) {
        return this.baseMapper.listPage(new Page<>(query.getCurrent(), query.getSize()), query);
    }

    /**
     * 获取用户的消息
     *
     * @param username 用户名
     * @return {@link List}<{@link MsgUserVO}>
     */
    @Override
    public List<MsgUserVO> listUsersMsg(String username) {
        return this.baseMapper.listUsersMsg(username);
    }

    /**
     * 保存接收用户消息
     *
     * @param dto 用户消息BO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserMsg(UserMsgDTO dto) {
        // 保存接收消息的用户
        List<SysMsgUser> sysMsgUserList = dto.getMsgBodyList().stream()
                .map(msgBody -> SysMsgUser.builder()
                        .msgId(msgBody.getMsgId())
                        .deptId(msgBody.getDeptId())
                        .userId(msgBody.getUserId())
                        .userId(msgBody.getUserId())
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(sysMsgUserList);
    }


    /**
     * 关闭
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> close(Long msgId) {
        return Result.ok(this.update(Wrappers.<SysMsgUser>lambdaUpdate().set(SysMsgUser::getIsClose, 1)
                .eq(SysMsgUser::getId, msgId)
                .eq(SysMsgUser::getIsClose, 0)
                .eq(SysMsgUser::getUserId, BreezeStpUtil.getUser().getId())));
    }

    /**
     * 标记已读
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> read(Long msgId) {
        return Result.ok(this.update(Wrappers.<SysMsgUser>lambdaUpdate().set(SysMsgUser::getIsRead, 1)
                .eq(SysMsgUser::getId, msgId)
                .eq(SysMsgUser::getIsRead, 0)
                .eq(SysMsgUser::getUserId, BreezeStpUtil.getUser().getId())));

    }

    /**
     * 删除用户的消息通过ids
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeUserMsgByIds(List<Long> ids) {
        List<SysMsgUser> sysMsgUserList = this.listByIds(ids);
        AssertUtil.isTrue(CollUtil.isNotEmpty(sysMsgUserList), NOT_FOUND);
        boolean remove = this.removeByIds(ids);
        AssertUtil.isTrue(remove, ResultCode.FAIL);
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
