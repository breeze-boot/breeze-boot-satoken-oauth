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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysTenantMapper;
import com.breeze.boot.modules.auth.model.entity.SysTenant;
import com.breeze.boot.modules.auth.model.entity.SysUser;
import com.breeze.boot.modules.auth.model.form.TenantForm;
import com.breeze.boot.modules.auth.model.mappers.SysTenantMapStruct;
import com.breeze.boot.modules.auth.model.query.TenantQuery;
import com.breeze.boot.modules.auth.model.vo.TenantVO;
import com.breeze.boot.modules.auth.service.SysTenantService;
import com.breeze.boot.modules.auth.service.SysUserService;
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

    private final SysTenantMapStruct sysTenantMapStruct;

    /**
     * 列表页面
     *
     * @param tenantQuery 租户查询
     * @return {@link Page}<{@link TenantVO}>
     */
    @Override
    public Page<TenantVO> listPage(TenantQuery tenantQuery) {
        Page<SysTenant> page = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(tenantQuery.getTenantName()), SysTenant::getTenantName, tenantQuery.getTenantName())
                .orderByDesc(SysTenant::getCreateTime)
                .page(new Page<>(tenantQuery.getCurrent(), tenantQuery.getSize()));
        return sysTenantMapStruct.page2VOPage(page);
    }

    /**
     * 按id获取信息
     *
     * @param tenantId 租户id
     * @return {@link TenantVO }
     */
    @Override
    public TenantVO getInfoById(Long tenantId) {
        return this.sysTenantMapStruct.entity2VO(this.getById(tenantId));
    }

    /**
     * 修改租户
     *
     * @param id         ID
     * @param tenantForm 租户表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyTenant(Long id, TenantForm tenantForm) {
        SysTenant sysTenant = this.sysTenantMapStruct.form2Entity(tenantForm);
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

    public List<Map<String, Object>> selectTenant() {
        return this.list().stream().map(tenant -> {
            Map<String, Object> tenantMap = Maps.newHashMap();
            tenantMap.put("value", tenant.getId());
            tenantMap.put("label", tenant.getTenantName());
            return tenantMap;
        }).collect(Collectors.toList());
    }
}




