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

package com.breeze.boot.bpm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.bpm.model.vo.BpmInstanceVO;
import com.breeze.boot.bpm.service.IActRuExecutionService;
import com.breeze.boot.bpm.mapper.ActRuExecutionMapper;
import com.breeze.boot.bpm.model.entity.ActRuExecution;
import com.breeze.boot.bpm.model.query.BpmInstanceQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 流程执行实例服务Impl
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Service
@RequiredArgsConstructor
public class ActRuExecutionServiceImpl extends ServiceImpl<ActRuExecutionMapper, ActRuExecution> implements IActRuExecutionService {

    /**
     * 列表页面
     *
     * @param bpmInstanceQuery 流程实例查询
     * @return {@link Page}<{@link BpmInstanceVO}>
     */
    @Override
    public Page<BpmInstanceVO> listPage(BpmInstanceQuery bpmInstanceQuery) {
        Page<BpmInstanceVO> page = new Page<>(bpmInstanceQuery.getCurrent(), bpmInstanceQuery.getSize());
        return this.baseMapper.listPage(page, bpmInstanceQuery);
    }

}
