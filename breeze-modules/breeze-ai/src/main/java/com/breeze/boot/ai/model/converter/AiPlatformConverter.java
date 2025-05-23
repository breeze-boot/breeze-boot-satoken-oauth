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

package com.breeze.boot.ai.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.ai.model.entity.AiPlatform;
import com.breeze.boot.ai.model.form.AiPlatformForm;
import com.breeze.boot.ai.model.vo.AiPlatformVO;
import org.mapstruct.Mapper;

/**
 * AI平台 转换器
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@Mapper(componentModel = "spring" )
public interface AiPlatformConverter {

    Page<AiPlatformVO> page2VOPage(Page<AiPlatform> aiPlatformPage);

    AiPlatform form2Entity(AiPlatformForm form);

    AiPlatformVO entity2VO(AiPlatform aiPlatform);

}
