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
import com.breeze.boot.system.model.entity.SysConfigCateg;
import com.breeze.boot.system.model.form.SysConfigCategForm;
import com.breeze.boot.system.model.vo.SysConfigCategVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 系统参数分类表 转换器
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Mapper(componentModel = "spring")
public interface SysConfigCategConverter {

    Page<SysConfigCategVO> page2VOPage(Page<SysConfigCateg> sysConfigCategPage);

    SysConfigCateg form2Entity(SysConfigCategForm form);

    SysConfigCategVO entity2VO(SysConfigCateg sysConfigCateg);

    List<SysConfigCategVO> list2VOList(List<SysConfigCateg> sysConfigCategList);
}
