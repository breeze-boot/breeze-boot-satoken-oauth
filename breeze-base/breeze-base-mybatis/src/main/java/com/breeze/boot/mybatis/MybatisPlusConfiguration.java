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

package com.breeze.boot.mybatis;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.BreezeTenantHolder;
import com.breeze.boot.mybatis.aspect.DymicSqlAspect;
import com.breeze.boot.mybatis.config.BreezeLogicSqlInjector;
import com.breeze.boot.mybatis.config.TenantProperties;
import com.breeze.boot.mybatis.events.PublisherSaveSysAuditLogEvent;
import com.breeze.boot.mybatis.plugins.BreezeAuditInnerInterceptor;
import com.breeze.boot.mybatis.plugins.BreezeDataPermissionInterceptor;
import com.breeze.boot.mybatis.plugins.BreezeListConditionInterceptor;
import com.breeze.boot.mybatis.plugins.BreezeSqlLogInnerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static com.breeze.boot.core.constants.CoreConstants.TENANT_ID_COLUMN;

/**
 * mybatis +配置
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Slf4j
@RequiredArgsConstructor
@Import(PublisherSaveSysAuditLogEvent.class)
@EnableConfigurationProperties(TenantProperties.class)
public class MybatisPlusConfiguration {

    private final TenantProperties tenantProperties;
    private final PublisherSaveSysAuditLogEvent publisherSaveSysAuditLogEvent;

    /**
     * 自定义 SqlInjector 包含自定义的全局方法
     */
    @Bean
    public BreezeLogicSqlInjector logicSqlInjector() {
        return new BreezeLogicSqlInjector();
    }

    @Bean
    public DymicSqlAspect dymicSqlAspect() {
        return new DymicSqlAspect();
    }

    /**
     * mybatis +拦截器
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据审计
        interceptor.addInnerInterceptor(new BreezeAuditInnerInterceptor(publisherSaveSysAuditLogEvent));
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表删除更新
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 0. 自动拼装查询条件拦截器提前,减少后续无权限数据的处理
        interceptor.addInnerInterceptor(new BreezeListConditionInterceptor());
        // 1. 数据权限拦截器提前,减少后续无权限数据的处理
        interceptor.addInnerInterceptor(new BreezeDataPermissionInterceptor());
        // 2. 租户拦截器其次
        interceptor.addInnerInterceptor(this.tenantLineInnerInterceptor(this.tenantProperties));
        // 3. 分页拦截器放最后
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 4. SQL日志拦截器
        interceptor.addInnerInterceptor(new BreezeSqlLogInnerInterceptor());
        return interceptor;
    }

    /**
     * 定制租户拦截器
     *
     * @return {@link TenantLineInnerInterceptor}
     */
    private TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties tenantProperties) {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                Long tenantId = BreezeTenantHolder.getTenant();
                log.info("当前租户： {}", tenantId);
                AssertUtil.isNotNull(tenantId, ResultCode.TENANT_NOT_FOUND);
                return new LongValue(tenantId);
            }

            @Override
            public String getTenantIdColumn() {
                return TENANT_ID_COLUMN;
            }

            /**
             * 这是 default 方法
             * <p>
             * 默认返回 false 表示需要拼多租户条件
             * 默认返回 true 表示不需要拼多租户条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                log.info("在多租户表序列中？ {}", CollUtil.contains(tenantProperties.getTables(), tableName));
                return !CollUtil.contains(tenantProperties.getTables(), tableName);
            }
        });
    }

}

