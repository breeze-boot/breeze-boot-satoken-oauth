/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.system.mapper.SysConfigMapper;
import com.breeze.boot.system.model.converter.SysConfigConverter;
import com.breeze.boot.system.model.entity.SysConfig;
import com.breeze.boot.system.model.form.SysConfigForm;
import com.breeze.boot.system.model.query.SysConfigQuery;
import com.breeze.boot.system.model.vo.SysConfigVO;
import com.breeze.boot.system.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.breeze.boot.core.constants.CacheConstants.CONFIG_KEY_PREFIX;

/**
 * 系统参数配置表 impl
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService, InitializingBean {

    private final SysConfigConverter sysConfigConverter;
    private static final long CACHE_EXPIRE = 1; // 缓存1小时

    private final RedisTemplate<String, Object> redisTemplate;

    private final Environment environment;

    /**
     * 列表页面
     *
     * @param query 系统参数配置表查询
     * @return {@link Page}<{@link SysConfigVO }>
     */
    @Override
    @DymicSql
    public Page<SysConfigVO> listPage(@ConditionParam SysConfigQuery query) {
        Page<SysConfig> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysConfig> sysConfigPage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysConfig::getCreateTime)
                .page(page);
        return this.sysConfigConverter.page2VOPage(sysConfigPage);
    }

    /**
     * 按id获取信息
     *
     * @param sysConfigId 系统参数配置表 id
     * @return {@link SysConfigVO }
     */
    @Override
    public SysConfigVO getInfoById(Long sysConfigId) {
        return this.sysConfigConverter.entity2VO(this.getById(sysConfigId));
    }

    /**
     * 保存系统参数配置表
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveSysConfig(SysConfigForm form) {
        return this.save(sysConfigConverter.form2Entity(form));
    }

    /**
     * 修改系统参数配置表
     *
     * @param sysConfigId 系统参数配置表ID
     * @param form        系统参数配置表表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifySysConfig(Long sysConfigId, SysConfigForm form) {
        SysConfig sysConfig = this.sysConfigConverter.form2Entity(form);
        sysConfig.setId(sysConfigId);
        return this.updateById(sysConfig);
    }

    /**
     * 通过IDS删除系统参数配置表
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeSysConfigByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

    /**
     * 加载指定环境的所有配置到Redis
     */
    public void loadConfig(String envCode) {
        List<SysConfig> configList = this.list(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getEnvCode, envCode));
        String redisKey = CONFIG_KEY_PREFIX + envCode;

        Map<String, String> configMap = new HashMap<>();
        for (SysConfig config : configList) {
            configMap.put(config.getParamName(), config.getParamValue());
        }

        // 存入Redis（Hash结构）
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(redisKey, configMap);
        redisTemplate.expire(redisKey, CACHE_EXPIRE, TimeUnit.HOURS);
    }

    /**
     * 获取当前激活的环境编码（如prod）
     * <p>
     * 对应配置：spring.profiles.active
     */
    public String getCurrentEnv() {
        // 获取激活的环境，多个环境用逗号分隔时取第一个
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return activeProfiles[0];
        }
        // 若未配置，返回默认环境（如dev）
        return "dev";
    }

    /**
     * 刷新当前环境的配置（从Redis加载）
     */
    public void refreshCurrentEnvConfig() {
        loadConfig(getCurrentEnv());
    }

    public void afterPropertiesSet() {
        loadConfig(getCurrentEnv());
    }
}