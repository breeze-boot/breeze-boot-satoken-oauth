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

package com.breeze.boot.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.breeze.boot.system.model.entity.SysDbResource;
import com.breeze.boot.system.service.SysDbResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.sql.DataSource;
import java.util.List;

/**
 * 加载数据源
 *
 * @author gaoweixuan
 * @since 2024/01/24
 */
@Configuration
@RequiredArgsConstructor
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DefaultDataSourceCreator defaultDataSourceCreator;

    private final DataSource dataSource;

    private final SysDbResourceService sysDbResourceService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        List<SysDbResource> selected = sysDbResourceService.list();
        for (SysDbResource sysDbResource : selected) {
            DataSourceProperty sourceProperty = new DataSourceProperty();
            sourceProperty.setPoolName(sysDbResource.getDbName());
            sourceProperty.setDriverClassName(sysDbResource.getDriver());
            sourceProperty.setUsername(sysDbResource.getUsername());
            sourceProperty.setPassword(sysDbResource.getPassword());
            DataSource creatorDataSource = defaultDataSourceCreator.createDataSource(sourceProperty);
            dynamicRoutingDataSource.addDataSource(sourceProperty.getPoolName(), creatorDataSource);
        }
    }

}
