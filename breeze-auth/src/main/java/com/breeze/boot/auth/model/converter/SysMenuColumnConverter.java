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

package com.breeze.boot.auth.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.auth.model.entity.SysMenuColumn;
import com.breeze.boot.auth.model.form.MenuColumnForm;
import com.breeze.boot.auth.model.vo.MenuColumnVO;
import org.mapstruct.Mapper;

/**
 * 行权限转换器
 *
 * @author gaoweixuan
 * @since 2024-07-14
 */
@Mapper(componentModel = "spring")
public interface SysMenuColumnConverter {

    Page<MenuColumnVO> page2VOPage(Page<SysMenuColumn> sysColumnPermissionPage);

    SysMenuColumn form2Entity(MenuColumnForm menuColumnForm);

    MenuColumnVO entity2VO(SysMenuColumn sysMenuColumn);

}
