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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.model.entity.SysUser;
import com.breeze.boot.auth.service.SysUserService;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.mail.dto.MailDTO;
import com.breeze.boot.mail.service.CustomJavaMailSender;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.system.mapper.SysEmailSubjectMapper;
import com.breeze.boot.system.model.converter.SysMSubjectConverter;
import com.breeze.boot.system.model.entity.SysEmailConfig;
import com.breeze.boot.system.model.entity.SysEmailSubject;
import com.breeze.boot.system.model.form.MSubjectForm;
import com.breeze.boot.system.model.form.MSubjectOpenForm;
import com.breeze.boot.system.model.form.MSubjectSetUserForm;
import com.breeze.boot.system.model.query.MSubjectQuery;
import com.breeze.boot.system.model.vo.EmailConfigVO;
import com.breeze.boot.system.model.vo.MSubjectEmailVO;
import com.breeze.boot.system.model.vo.MSubjectVO;
import com.breeze.boot.system.service.SysEmailConfigService;
import com.breeze.boot.system.service.SysMSubjectService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.EMAIL_NOT_FOUND;

/**
 * 系统邮箱主题服务impl
 *
 * @author gaoweixuan
 * @since 2024-07-13 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysMSubjectServiceImpl extends ServiceImpl<SysEmailSubjectMapper, SysEmailSubject> implements SysMSubjectService {

    private final SysMSubjectConverter sysMSubjectConverter;

    private final SysUserService sysUserService;

    private final SysEmailConfigService sysEmailConfigService;

    /**
     * 列表页
     *
     * @param query 邮箱主题查询
     * @return {@link Page }<{@link MSubjectVO }>
     */
    @Override
    @DymicSql
    public Page<MSubjectVO> listPage(@ConditionParam MSubjectQuery query) {
        Page<SysEmailSubject> emailPage = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper<SysEmailSubject> queryWrapper = new QueryWrapper<>();
        query.getSortQueryWrapper(queryWrapper);
        queryWrapper.like(StrUtil.isAllNotBlank(query.getUsername()), "username", query.getUsername());
        Page<SysEmailSubject> page = this.page(emailPage, queryWrapper);
        return this.sysMSubjectConverter.page2PageVO(page);
    }

    /**
     * 按id获取信息
     *
     * @param subjectId subjectId
     * @return {@link EmailConfigVO }
     */
    @Override
    public MSubjectVO getInfoById(Long subjectId) {
        SysEmailSubject sysEmailSubject = this.getById(subjectId);
        MSubjectVO mSubjectVO = this.sysMSubjectConverter.entity2VO(sysEmailSubject);
        List<SysUser> ccUserList = this.sysUserService.listByIds(List.of(Optional.ofNullable(sysEmailSubject.getCc()).orElse("").split(",")));
        mSubjectVO.setCcUserId(Optional.ofNullable(ccUserList).orElseGet(Lists::newArrayList).stream().map(SysUser::getId).collect(Collectors.toList()));
        List<SysUser> toUserList = this.sysUserService.listByIds(Arrays.asList(Optional.ofNullable(sysEmailSubject.getTo()).orElse("").split(",")));
        mSubjectVO.setToUserId(Optional.ofNullable(toUserList).orElseGet(Lists::newArrayList).stream().map(SysUser::getId).collect(Collectors.toList()));
        return mSubjectVO;
    }

    /**
     * 保存
     *
     * @param form 邮箱主题表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveEmailSubject(MSubjectForm form) {
        return this.save(this.sysMSubjectConverter.form2Entity(form));
    }

    /**
     * 修改
     *
     * @param id           ID
     * @param form 邮箱主题表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyEmailSubject(Long id, MSubjectForm form) {
        SysEmailSubject sysEmailSubject = sysMSubjectConverter.form2Entity(form);
        sysEmailSubject.setId(id);
        return this.updateById(sysEmailSubject);
    }

    /**
     * 邮寄
     *
     * @param id ID
     * @return {@link Boolean }
     */
    @Override
    public Boolean send(Long id) {
        CustomJavaMailSender customJavaMailSender = SpringUtil.getBean(CustomJavaMailSender.class);
        SysEmailSubject sysEmailSubject = this.getSysEmailSubject(id);
        SysEmailConfig sysEmailConfig = sysEmailConfigService.getOne(Wrappers.<SysEmailConfig>lambdaQuery().eq(SysEmailConfig::getStatus, 1));
        MailDTO mailDTO = getMailDTO(sysEmailConfig);
        customJavaMailSender.sendMessage(mailDTO, sysEmailSubject.getSubject(), sysEmailSubject.getContent(), sysEmailSubject.getTo().split(","), sysEmailSubject.getCc().split(","));
        return Boolean.TRUE;
    }

    private static MailDTO getMailDTO(SysEmailConfig sysEmailConfig) {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setSmtpHost(sysEmailConfig.getSmtpHost());
        mailDTO.setSmtpSocketFactoryClass(sysEmailConfig.getSmtpSocketFactoryClass());
        mailDTO.setPort(sysEmailConfig.getPort());
        mailDTO.setUsername(sysEmailConfig.getUsername());
        mailDTO.setPassword(sysEmailConfig.getPassword());
        mailDTO.setEncoding(sysEmailConfig.getEncoding());
        mailDTO.setProtocol(sysEmailConfig.getProtocol());
        mailDTO.setSsl(sysEmailConfig.getSsl());
        mailDTO.setAuth(sysEmailConfig.getAuth());
        mailDTO.setSmtpSocketFactoryClass(sysEmailConfig.getSmtpSocketFactoryClass());
        return mailDTO;
    }

    /**
     * 打开
     *
     * @param form 邮箱主题开关表单
     * @return {@link Boolean }
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean open(MSubjectOpenForm form) {
        List<SysEmailSubject> allList = this.list();
        List<SysEmailSubject> sysEmailList = allList.stream().peek(item -> {
            if (form.getStatus() == 1) {
                if (Objects.equals(item.getId(), form.getId())) {
                    item.setStatus(1);
                } else {
                    item.setStatus(0);
                }
            }
        }).collect(Collectors.toList());
        return this.updateBatchById(sysEmailList);
    }

    /**
     * 设置电子邮件用户
     *
     * @param id                  ID
     * @param form m主题集用户表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean setEmailUser(Long id, MSubjectSetUserForm form) {
        SysEmailSubject sysEmailSubject = getSysEmailSubject(id);
        if (CollUtil.isNotEmpty(form.getCcUserId())) {
            sysEmailSubject.setCc(String.join(",", form.getCcUserId()));
        }
        if (CollUtil.isEmpty(form.getCcUserId())) {
            sysEmailSubject.setTo(String.join(",", form.getToUserId()));
        }
        return sysEmailSubject.updateById();
    }

    /**
     * 查看邮箱接收人
     *
     * @param id ID
     * @return {@link List }<{@link MSubjectEmailVO }>
     */
    @Override
    public List<MSubjectEmailVO> listCcEmailUser(Long id) {
        SysEmailSubject sysEmailSubject = getSysEmailSubject(id);
        List<MSubjectEmailVO> resultList = Lists.newArrayList();
        List<SysUser> ccUserList = this.sysUserService.listByIds(Arrays.asList(Optional.ofNullable(sysEmailSubject.getCc()).orElse("").split(",")));
        for (SysUser sysUser : Optional.ofNullable(ccUserList).orElseGet(Lists::newArrayList)) {
            MSubjectEmailVO mSubjectEmailVO = new MSubjectEmailVO();
            mSubjectEmailVO.setEmail(sysUser.getEmail());
            resultList.add(mSubjectEmailVO);
        }
        return resultList;
    }

    /**
     * 查看邮箱抄送人
     *
     * @param id ID
     * @return {@link List }<{@link MSubjectEmailVO }>
     */
    @Override
    public List<MSubjectEmailVO> listToEmailUser(Long id) {
        SysEmailSubject sysEmailSubject = getSysEmailSubject(id);
        List<MSubjectEmailVO> resultList = Lists.newArrayList();
        List<SysUser> toUserList = this.sysUserService.listByIds(Arrays.asList(Optional.ofNullable(sysEmailSubject.getTo()).orElse("").split(",")));
        for (SysUser sysUser : Optional.ofNullable(toUserList).orElseGet(Lists::newArrayList)) {
            MSubjectEmailVO mSubjectEmailVO = new MSubjectEmailVO();
            mSubjectEmailVO.setEmail(sysUser.getEmail());
            resultList.add(mSubjectEmailVO);
        }
        return resultList;
    }

    private SysEmailSubject getSysEmailSubject(Long id) {
        SysEmailSubject sysEmailSubject = this.getById(id);
        AssertUtil.isNotNull(sysEmailSubject, EMAIL_NOT_FOUND);
        return sysEmailSubject;
    }

}
