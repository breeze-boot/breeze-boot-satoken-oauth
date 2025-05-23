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
import com.breeze.boot.bpm.model.vo.BpmDefinitionVO;
import com.breeze.boot.bpm.mapper.ActReDeploymentMapper;
import com.breeze.boot.bpm.model.entity.ActReDeployment;
import com.breeze.boot.bpm.model.query.BpmDefinitionQuery;
import com.breeze.boot.bpm.service.IActReDeploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 流程部署服务Impl
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Service
@RequiredArgsConstructor
public class ActReDeploymentServiceImpl extends ServiceImpl<ActReDeploymentMapper, ActReDeployment> implements IActReDeploymentService {

    /**
     * 列表页面
     *
     * @param query 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    @Override
    public Page<BpmDefinitionVO> listPage(BpmDefinitionQuery query) {
        Page<BpmDefinitionVO> page = new Page<>(query.getCurrent(), query.getSize());
        return this.baseMapper.listPage(page, query);
    }
    @Override
    public BpmDefinitionVO getInfo(String definitionId) {
        return this.baseMapper.getInfo(definitionId);
    }

}
