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
import com.breeze.boot.bpm.model.converter.BpmGroupConverter;
import com.breeze.boot.bpm.service.IGroupService;
import com.breeze.boot.bpm.mapper.GroupMapper;
import com.breeze.boot.bpm.model.entity.Group;
import com.breeze.boot.bpm.model.query.BpmGroupQuery;
import com.breeze.boot.bpm.model.vo.BpmGroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    private final BpmGroupConverter bpmGroupConverter;

    @Override
    public Page<BpmGroupVO> listPage(BpmGroupQuery query) {
        Page<Group> page = this.baseMapper.listPage(new Page<>(query.getCurrent(), query.getSize()), query);
        return this.bpmGroupConverter.entityPage2PageVO(page);
    }

}