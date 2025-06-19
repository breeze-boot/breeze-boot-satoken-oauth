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

package com.breeze.boot.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.enums.DataRole;
import com.breeze.boot.mybatis.annotation.BreezeDataPermission;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import com.breeze.boot.system.model.entity.SysMsgUser;
import com.breeze.boot.system.model.query.UserMsgQuery;
import com.breeze.boot.system.model.vo.MsgUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户消息映射器
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
@Mapper
public interface SysMsgUserMapper extends BreezeBaseMapper<SysMsgUser> {
    /**
     * 列表页面
     *
     * @param page         页面
     * @param userMsgQuery 用户消息查询
     * @return {@link IPage}<{@link MsgUserVO}>
     */
    @BreezeDataPermission(dept = DataRole.DEPT_ID)
    @DymicSql
    IPage<MsgUserVO> listPage(Page<SysMsgUser> page,@ConditionParam @Param("userMsgQuery") UserMsgQuery userMsgQuery);

    /**
     * 获取消息列表通过用户名
     *
     * @param username 用户名
     * @return {@link List}<{@link MsgUserVO}>
     */
    List<MsgUserVO> listUsersMsg(String username);

}
