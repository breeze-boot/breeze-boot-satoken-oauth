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

package com.breeze.boot.modules.wo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.BUtils;
import com.breeze.boot.modules.wo.mapper.WoLeaveMapper;
import com.breeze.boot.modules.wo.model.entity.WoLeave;
import com.breeze.boot.modules.wo.model.query.WoLeaveQuery;
import com.breeze.boot.modules.wo.service.IWoLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * OA请假服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class WoLeaveServiceImpl extends ServiceImpl<WoLeaveMapper, WoLeave> implements IWoLeaveService {

    /**
     * 列表
     *
     * @param woLeaveQuery 请假工单查询
     * @return {@link Page }<{@link WoLeave }>
     */
    @Override
    public Page<WoLeave> listPage(WoLeaveQuery woLeaveQuery) {
        Page<WoLeave> platformPage = new Page<>(woLeaveQuery.getCurrent(), woLeaveQuery.getSize());
        QueryWrapper<WoLeave> queryWrapper = new QueryWrapper<>();
        woLeaveQuery.getSortQueryWrapper(queryWrapper);
        queryWrapper.like(StrUtil.isAllNotBlank(woLeaveQuery.getReason()), BUtils.toFieldName(WoLeave::getTitle), woLeaveQuery.getTitle());
        return this.page(platformPage, queryWrapper);
    }
}