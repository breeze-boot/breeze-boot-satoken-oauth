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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.system.mapper.SysEmailConfigMapper;
import com.breeze.boot.system.model.converter.SysEmailConverter;
import com.breeze.boot.system.model.entity.SysEmailConfig;
import com.breeze.boot.system.model.form.EmailConfigForm;
import com.breeze.boot.system.model.form.EmailConfigOpenForm;
import com.breeze.boot.system.model.query.EmailConfigQuery;
import com.breeze.boot.system.model.vo.EmailConfigVO;
import com.breeze.boot.system.service.SysEmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统邮箱服务impl
 *
 * @author gaoweixuan
  * @since 2024-07-13
 */
@Service
@RequiredArgsConstructor
public class SysEmailConfigServiceImpl extends ServiceImpl<SysEmailConfigMapper, SysEmailConfig> implements SysEmailConfigService {

    private final SysEmailConverter sysEmailConverter;

    /**
     * 列表页
     *
     * @param query 电子邮件查询
     * @return {@link Page }<{@link EmailConfigVO }>
     */
    @Override
    @DymicSql
    public Page<EmailConfigVO> listPage(@ConditionParam EmailConfigQuery query) {
        Page<SysEmailConfig> emailPage = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper<SysEmailConfig> queryWrapper = new QueryWrapper<>();
        query.getSortQueryWrapper(queryWrapper);
        queryWrapper.like(StrUtil.isAllNotBlank(query.getUsername()), "username", query.getUsername());
        Page<SysEmailConfig> page = this.page(emailPage, queryWrapper);
        return this.sysEmailConverter.page2PageVO(page);
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
        return this.sysEmailConverter.entity2VO(sysEmailConfig);
    }

    /**
     * 保存
     *
     * @param form 电子邮件表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveEmail(EmailConfigForm form) {
        return this.save(this.sysEmailConverter.form2Entity(form));
    }

    /**
     * 修改
     *
     * @param id        ID
     * @param form 电子邮件表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyEmail(Long id, EmailConfigForm form) {
        SysEmailConfig sysEmailConfig = sysEmailConverter.form2Entity(form);
        sysEmailConfig.setId(id);
        return this.updateById(sysEmailConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean open(EmailConfigOpenForm form) {
        List<SysEmailConfig> allList = this.list();
        List<SysEmailConfig> sysEmailConfigList = allList.stream().peek(item -> {
            if (form.getStatus() == 1) {
                if (Objects.equals(item.getId(), form.getId())) {
                    item.setStatus(1);
                } else {
                    item.setStatus(0);
                }
            }
        }).collect(Collectors.toList());
        return this.updateBatchById(sysEmailConfigList);
    }

}
