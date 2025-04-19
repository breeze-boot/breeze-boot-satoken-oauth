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

package com.breeze.boot.modules.system.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.modules.system.model.entity.SysLog;
import com.breeze.boot.modules.system.model.vo.LogVO;
import org.mapstruct.Mapper;

/**
 * 日志转换器
 *
 * @author gaoweixuan
 * @since 2024-07-13
 */
@Mapper(componentModel = "spring")
public interface SysLogConverter {

    Page<LogVO> entityPage2VOPage(Page<SysLog> sysLogPage);

    LogVO entity2VO(SysLog byId);

    SysLog bo2Entity(SysLogBO sysLogBO);

}
