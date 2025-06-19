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

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.mapper.SysPlatformMapper;
import com.breeze.boot.auth.model.converter.SysPlatformConverter;
import com.breeze.boot.auth.model.entity.SysPlatform;
import com.breeze.boot.auth.model.form.PlatformForm;
import com.breeze.boot.auth.model.query.PlatformQuery;
import com.breeze.boot.auth.model.vo.PlatformVO;
import com.breeze.boot.auth.service.SysPlatformService;
import com.breeze.boot.core.utils.BUtils;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统平台服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysPlatformServiceImpl extends ServiceImpl<SysPlatformMapper, SysPlatform> implements SysPlatformService {

    private final SysPlatformConverter sysPlatformConverter;

    /**
     * 列表页面
     *
     * @param query 平台查询
     * @return {@link Page}<{@link PlatformVO}>
     */
    @Override
    @DymicSql
    public Page<PlatformVO> listPage(@ConditionParam PlatformQuery query) {
        Page<SysPlatform> platformPage = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper<SysPlatform> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isAllNotBlank(query.getPlatformName()), BUtils.toFieldName(SysPlatform::getPlatformName), query.getPlatformName());
        wrapper.like(StrUtil.isAllNotBlank(query.getPlatformCode()), BUtils.toFieldName(SysPlatform::getPlatformCode), query.getPlatformCode());
        Page<SysPlatform> page = this.page(platformPage, wrapper);
        return this.sysPlatformConverter.page2PageVO(page);
    }

    /**
     * 详情
     *
     * @param platformId 平台id
     * @return {@link PlatformVO }
     */
    @Override
    public PlatformVO getInfoById(Long platformId) {
        SysPlatform sysPlatform = this.getById(platformId);
        return this.sysPlatformConverter.entity2VO(sysPlatform);
    }

    /**
     * 保存平台
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean savePlatform(PlatformForm form) {
        return this.save(this.sysPlatformConverter.form2Entity(form));
    }

    /**
     * 修改平台
     *
     * @param id   ID
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyPlatform(Long id, PlatformForm form) {
        SysPlatform sysPlatform = this.sysPlatformConverter.form2Entity(form);
        sysPlatform.setId(id);
        return this.updateById(sysPlatform);
    }

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPlatform() {
        List<SysPlatform> platformList = this.list();
        List<Map<String, Object>> collect = platformList.stream().map(sysPlatform -> {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("value", sysPlatform.getId());
            map.put("label", sysPlatform.getPlatformName());
            return map;
        }).collect(Collectors.toList());
        return Result.ok(collect);
    }

}
