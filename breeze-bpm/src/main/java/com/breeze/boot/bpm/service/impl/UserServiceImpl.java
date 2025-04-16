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
import com.breeze.boot.bpm.model.mappers.BpmUserMapStruct;
import com.breeze.boot.bpm.model.vo.BpmUserVO;
import com.breeze.boot.bpm.service.IUserService;
import com.breeze.boot.bpm.mapper.UserMapper;
import com.breeze.boot.bpm.model.entity.User;
import com.breeze.boot.bpm.model.query.BpmUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final BpmUserMapStruct bpmUserMapStruct;

    @Override
    public Page<BpmUserVO> listPage(BpmUserQuery userQuery) {
        Page<User> page = this.baseMapper.listPage(new Page<>(userQuery.getCurrent(), userQuery.getSize()), userQuery);
        return this.bpmUserMapStruct.entityPage2PageVO(page);
    }
}