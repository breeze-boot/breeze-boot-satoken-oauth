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

package com.breeze.boot.system.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.mail.dto.MailDTO;
import com.breeze.boot.system.model.entity.SysEmailConfig;
import com.breeze.boot.system.model.form.EmailConfigForm;
import com.breeze.boot.system.model.vo.EmailConfigVO;
import org.mapstruct.Mapper;

/**
 * 邮箱转换器
 *
 * @author gaoweixuan
 * @since 2024-07-13
 */
@Mapper(componentModel = "spring")
public interface SysEmailConverter {

    Page<EmailConfigVO> page2PageVO(Page<SysEmailConfig> page);

    MailDTO entity2DTO(SysEmailConfig sysEmailConfig);

    EmailConfigVO entity2VO(SysEmailConfig sysEmailConfig);

    SysEmailConfig form2Entity(EmailConfigForm emailConfigForm);

}
