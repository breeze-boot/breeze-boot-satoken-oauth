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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.message.vo.MsgVO;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.system.mapper.SysMsgMapper;
import com.breeze.boot.system.model.converter.SysMsgConverter;
import com.breeze.boot.system.model.entity.SysMsg;
import com.breeze.boot.system.model.form.MsgForm;
import com.breeze.boot.system.model.query.MsgQuery;
import com.breeze.boot.system.service.SysMsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统消息服务
 *
 * @author gaoweixuan
 * @since 2022-11-20
 */
@Service
@RequiredArgsConstructor
public class SysMsgServiceImpl extends ServiceImpl<SysMsgMapper, SysMsg> implements SysMsgService {

    private final SysMsgConverter sysMsgConverter;

    /**
     * 列表页面
     *
     * @param query 消息查询
     * @return {@link Page}<{@link MsgVO}>
     */
    @Override
    @DymicSql
    public Page<MsgVO> listPage(@ConditionParam MsgQuery query) {
        Page<SysMsg> msgPage = new Page<>(query.getCurrent(), query.getSize());
        Page<SysMsg> page = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(query.getTitle()), SysMsg::getTitle, query.getTitle())
                .like(StrUtil.isAllNotBlank(query.getCode()), SysMsg::getCode, query.getCode())
                .page(msgPage);
        return this.sysMsgConverter.entityPage2VOPage(page);
    }

    /**
     * 按id获取信息
     *
     * @param id ID
     * @return {@link MsgVO }
     */
    @Override
    public MsgVO getInfoById(Long id) {
        return this.sysMsgConverter.entity2VO(this.getById(id));
    }

    /**
     * 保存消息
     *
     * @param form 消息表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveMsg(MsgForm form) {
        SysMsg sysMsg = this.sysMsgConverter.form2Entity(form);
        return this.save(sysMsg);
    }

    /**
     * 修改消息
     *
     * @param id      ID
     * @param form 消息表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyMsg(Long id, MsgForm form) {
        SysMsg sysMsg = this.sysMsgConverter.form2Entity(form);
        sysMsg.setId(id);
        return this.updateById(sysMsg);
    }

}
