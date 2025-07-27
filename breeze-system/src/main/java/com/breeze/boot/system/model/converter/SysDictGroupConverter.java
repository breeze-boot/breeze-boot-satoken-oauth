/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.system.model.entity.SysDictGroup;
import com.breeze.boot.system.model.form.SysDictGroupForm;
import com.breeze.boot.system.model.vo.SysDictGroupVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典分组 转换器
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Mapper(componentModel = "spring")
public interface SysDictGroupConverter {

    Page<SysDictGroupVO> page2VOPage(Page<SysDictGroup> sysDictGroupPage);

    SysDictGroup form2Entity(SysDictGroupForm form);

    SysDictGroupVO entity2VO(SysDictGroup sysDictGroup);

    List<SysDictGroupVO> list2VOList(List<SysDictGroup> sysDictGroupList);

}
