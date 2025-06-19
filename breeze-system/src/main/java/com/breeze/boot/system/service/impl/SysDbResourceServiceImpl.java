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

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.system.mapper.SysDbMapper;
import com.breeze.boot.system.model.converter.SysDbConverter;
import com.breeze.boot.system.model.entity.SysDbResource;
import com.breeze.boot.system.model.form.DbResourceForm;
import com.breeze.boot.system.model.query.DbResourceQuery;
import com.breeze.boot.system.model.vo.DbResourceVO;
import com.breeze.boot.system.service.SysDbResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * 系统字典服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysDbResourceServiceImpl extends ServiceImpl<SysDbMapper, SysDbResource> implements SysDbResourceService {

    private final DefaultDataSourceCreator defaultDataSourceCreator;

    private final SysDbConverter sysDbConverter;

    private final DataSource dataSource;

    /**
     * 分页
     *
     * @param query 数据源查询参数
     * @return {@link Page}<{@link DbResourceVO}>
     */
    @Override
    public Page<DbResourceVO> listPage(DbResourceQuery query) {
        Page<SysDbResource> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysDbResource> sysDbPage = this.baseMapper.listPage(page, query);
        return sysDbConverter.entityPage2VOPage(sysDbPage);
    }

    /**
     * 按id获取数据库资源
     *
     * @param id ID
     * @return {@link DbResourceVO }
     */
    @Override
    public DbResourceVO getDbResourceById(Long id) {
        return sysDbConverter.entity2VO(this.getById(id));
    }

    /**
     * 添加数据源
     *
     * @param form 数据源
     * @return {@link Boolean}
     */
    @Override
    public Boolean saveDbResource(DbResourceForm form) {
        SysDbResource sysDbResource = sysDbConverter.form2Entity(form);
        refreshDb(sysDbResource);
        return this.save(sysDbResource);
    }

    /**
     * 刷新数据源
     *
     * @param sysDbResource sys数据库
     */
    private void refreshDb(SysDbResource sysDbResource) {
        try {
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
            DataSourceProperty sourceProperty = new DataSourceProperty();
            sourceProperty.setPoolName(sysDbResource.getDbName());
            sourceProperty.setDriverClassName(sysDbResource.getDriver());
            sourceProperty.setUsername(sysDbResource.getUsername());
            sourceProperty.setPassword(sysDbResource.getPassword());
            DataSource creatorDataSource = defaultDataSourceCreator.createDataSource(sourceProperty);
            dynamicRoutingDataSource.addDataSource(sourceProperty.getPoolName(), creatorDataSource);
        } catch (Exception e) {
            log.error("[数据源注入失败", e);
        }
    }

    /**
     * 更新数据源
     *
     * @param id             ID
     * @param form 数据源表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean modifyDbResource(Long id, DbResourceForm form) {
        SysDbResource sysDbResource = this.getById(id);
        AssertUtil.isNotNull(sysDbResource, ResultCode.FAIL);
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        dynamicRoutingDataSource.removeDataSource(sysDbResource.getDbName());
        this.refreshDb(sysDbResource);
        return Boolean.TRUE;
    }

    /**
     * 删除数据源
     *
     * @param ids 数据源Ids
     * @return {@link Boolean}
     */
    @Override
    public Boolean removeDbResourceByIds(List<Long> ids) {
        for (Long id : ids) {
            SysDbResource sysDbResource = this.getById(id);
            AssertUtil.isNotNull(sysDbResource, ResultCode.FAIL);
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
            dynamicRoutingDataSource.removeDataSource(sysDbResource.getDbName());
        }
        return Boolean.TRUE;
    }

}
