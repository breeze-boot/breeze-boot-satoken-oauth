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

package com.breeze.boot.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.mapper.SysTenantMapper;
import com.breeze.boot.auth.model.converter.SysTenantConverter;
import com.breeze.boot.auth.model.entity.SysTenant;
import com.breeze.boot.auth.model.entity.SysUser;
import com.breeze.boot.auth.model.form.TenantForm;
import com.breeze.boot.auth.model.query.TenantQuery;
import com.breeze.boot.auth.model.vo.TenantVO;
import com.breeze.boot.auth.service.SysTenantService;
import com.breeze.boot.auth.service.SysUserService;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.IS_USED;

/**
 * 系统租户服务impl
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    /**
     * 系统用户服务
     */
    private final SysUserService sysUserService;

    private final SysTenantConverter sysTenantConverter;

    /**
     * 列表页面
     *
     * @param query 租户查询
     * @return {@link Page}<{@link TenantVO}>
     */
    @Override
    @DymicSql
    public Page<TenantVO> listPage(@ConditionParam TenantQuery query) {
        Page<SysTenant> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysTenant> tenantPage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(query.getTenantName()), SysTenant::getTenantName, query.getTenantName())
                .orderByDesc(SysTenant::getCreateTime)
                .page(page);
        return this.sysTenantConverter.page2VOPage(tenantPage);
    }

    /**
     * 按id获取信息
     *
     * @param tenantId 租户id
     * @return {@link TenantVO }
     */
    @Override
    public TenantVO getInfoById(Long tenantId) {
        return this.sysTenantConverter.entity2VO(this.getById(tenantId));
    }

    /**
     * 修改租户
     *
     * @param id         ID
     * @param form 租户表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyTenant(Long id, TenantForm form) {
        SysTenant sysTenant = this.sysTenantConverter.form2Entity(form);
        sysTenant.setId(id);
        return this.updateById(sysTenant);
    }

    /**
     * 通过IDS删除租户
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeTenantByIds(List<Long> ids) {
        List<SysUser> sysUserList = this.sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getTenantId, ids));
        AssertUtil.isTrue(CollUtil.isEmpty(sysUserList), IS_USED);
        return Result.ok(this.removeByIds(ids));
    }

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectTenant() {
        return Result.ok(this.list().stream().map(tenant -> {
            Map<String, Object> tenantMap = Maps.newHashMap();
            tenantMap.put("value", String.valueOf(tenant.getId()));
            tenantMap.put("label", tenant.getTenantName());
            return tenantMap;
        }).collect(Collectors.toList()));
    }
}




