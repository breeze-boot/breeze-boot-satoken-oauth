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

package com.breeze.boot.modules.auth.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.base.CustomizePermission;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.form.RowPermissionForm;
import com.breeze.boot.modules.auth.model.vo.RowPermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 行权限转换器
 *
 * @author gaoweixuan
 * @since 2024-07-14
 */
@Mapper(componentModel = "spring")
public interface SysRowPermissionConverter {

    Page<RowPermissionVO> page2VOPage(Page<SysRowPermission> rowPermissionPage);

    SysRowPermission form2Entity(RowPermissionForm rowPermissionForm);

    RowPermissionVO entity2VO(SysRowPermission sysRowPermission);

    @Mappings({
            @Mapping(target = "permissions", ignore = true)
    })
    CustomizePermission entity2Cache(SysRowPermission rowPermission);

}
