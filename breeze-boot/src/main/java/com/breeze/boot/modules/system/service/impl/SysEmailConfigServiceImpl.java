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

package com.breeze.boot.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.mail.dto.MailDTO;
import com.breeze.boot.mail.service.CustomJavaMailSender;
import com.breeze.boot.modules.system.mapper.SysEmailConfigMapper;
import com.breeze.boot.modules.system.model.entity.SysEmailConfig;
import com.breeze.boot.modules.system.model.form.EmailConfigForm;
import com.breeze.boot.modules.system.model.form.EmailConfigOpenForm;
import com.breeze.boot.modules.system.model.mappers.SysEmailMapStruct;
import com.breeze.boot.modules.system.model.query.EmailConfigQuery;
import com.breeze.boot.modules.system.model.vo.EmailConfigVO;
import com.breeze.boot.modules.system.service.SysEmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.EMAIL_CONFIG_NOT_FOUND;

/**
 * 系统邮箱服务impl
 *
 * @author gaoweixuan
  * @since 2024-07-13
 */
@Service
@RequiredArgsConstructor
public class SysEmailConfigServiceImpl extends ServiceImpl<SysEmailConfigMapper, SysEmailConfig> implements SysEmailConfigService, InitializingBean {

    private final TemplateEngine templateEngine;

    private final SysEmailMapStruct sysEmailMapStruct;

    /**
     * 列表页
     *
     * @param emailConfigQuery 电子邮件查询
     * @return {@link Page }<{@link EmailConfigVO }>
     */
    @Override
    public Page<EmailConfigVO> listPage(EmailConfigQuery emailConfigQuery) {
        Page<SysEmailConfig> emailPage = new Page<>(emailConfigQuery.getCurrent(), emailConfigQuery.getSize());
        QueryWrapper<SysEmailConfig> queryWrapper = new QueryWrapper<>();
        emailConfigQuery.getSortQueryWrapper(queryWrapper);
        queryWrapper.like(StrUtil.isAllNotBlank(emailConfigQuery.getUsername()), "username", emailConfigQuery.getUsername());
        Page<SysEmailConfig> page = this.page(emailPage, queryWrapper);
        return this.sysEmailMapStruct.page2PageVO(page);
    }

    /**
     * 按id获取信息
     *
     * @param emailId 电子邮件id
     * @return {@link EmailConfigVO }
     */
    @Override
    public EmailConfigVO getInfoById(Long emailId) {
        SysEmailConfig sysEmailConfig = this.getById(emailId);
        return this.sysEmailMapStruct.entity2VO(sysEmailConfig);
    }

    /**
     * 保存
     *
     * @param emailConfigForm 电子邮件表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveEmail(EmailConfigForm emailConfigForm) {
        return this.save(this.sysEmailMapStruct.form2Entity(emailConfigForm));
    }

    /**
     * 修改
     *
     * @param id        ID
     * @param emailConfigForm 电子邮件表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyEmail(Long id, EmailConfigForm emailConfigForm) {
        SysEmailConfig sysEmailConfig = sysEmailMapStruct.form2Entity(emailConfigForm);
        sysEmailConfig.setId(id);
        return this.updateById(sysEmailConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean open(EmailConfigOpenForm emailConfigOpenForm) {
        List<SysEmailConfig> allList = this.list();
        List<SysEmailConfig> sysEmailConfigList = allList.stream().peek(item -> {
            if (emailConfigOpenForm.getStatus() == 1) {
                if (Objects.equals(item.getId(), emailConfigOpenForm.getId())) {
                    item.setStatus(1);
                    CustomJavaMailSender customJavaMailSender = SpringUtil.getBean(CustomJavaMailSender.class);
                    MailDTO mailDTO = this.sysEmailMapStruct.entity2DTO(item);
                    customJavaMailSender.initMailConfig(mailDTO);
                } else {
                    item.setStatus(0);
                }
            }
        }).collect(Collectors.toList());
        return this.updateBatchById(sysEmailConfigList);
    }

    @Override
    public void afterPropertiesSet() {
        SysEmailConfig sysEmailConfig = this.getOne(Wrappers.<SysEmailConfig>lambdaQuery().eq(SysEmailConfig::getStatus, 1));
        AssertUtil.isNotNull(sysEmailConfig, EMAIL_CONFIG_NOT_FOUND);
        MailDTO mailDTO = this.sysEmailMapStruct.entity2DTO(sysEmailConfig);
        CustomJavaMailSender customJavaMailSender = new CustomJavaMailSender(templateEngine);
        customJavaMailSender.initMailConfig(mailDTO);
        SpringUtil.unregisterBean("com.breeze.boot.mail.service.CustomJavaMailSender");
        SpringUtil.registerBean("com.breeze.boot.mail.service.CustomJavaMailSender", customJavaMailSender);
    }
}
