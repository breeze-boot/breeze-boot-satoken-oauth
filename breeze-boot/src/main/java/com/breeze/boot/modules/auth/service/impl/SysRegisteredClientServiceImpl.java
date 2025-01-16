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

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysRegisteredClientMapper;
import com.breeze.boot.modules.auth.model.entity.SysRegisteredClient;
import com.breeze.boot.modules.auth.model.form.RegisteredClientForm;
import com.breeze.boot.modules.auth.model.form.ResetClientSecretForm;
import com.breeze.boot.modules.auth.model.query.RegisteredClientQuery;
import com.breeze.boot.modules.auth.model.vo.RegisteredClientVO;
import com.breeze.boot.modules.auth.service.SysRegisteredClientService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.breeze.boot.core.enums.ResultCode.EXISTS;

/**
 * 重写auth注册客户端库
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRegisteredClientServiceImpl extends ServiceImpl<SysRegisteredClientMapper, SysRegisteredClient> implements SysRegisteredClientService {

    private final AesSecretProperties aesSecretProperties;


    /**
     * set 转换 string + [,]
     *
     * @return {@link Function}<{@link Set}<{@link String}>, {@link String}>
     */
    private static Function<Set<String>, String> convertSet2String() {
        return str -> String.join(",", str);
    }

    /**
     * 列表页面
     *
     * @param registeredClientQuery 注册客户端参数
     * @return {@link Page}<{@link RegisteredClientVO}>
     */
    @Override
    public Page<RegisteredClientVO> listPage(RegisteredClientQuery registeredClientQuery) {
        Page<SysRegisteredClient> page = new Page<>(registeredClientQuery.getCurrent(), registeredClientQuery.getSize());
        Page<RegisteredClientVO> registeredClientPage = this.baseMapper.listPage(page, registeredClientQuery);
        return registeredClientPage.setRecords(registeredClientPage.getRecords().stream().peek(this::getClientVO).collect(Collectors.toList()));
    }

    /**
     * 保存
     *
     * @param registeredClientForm 注册客户端表单
     */
    @SneakyThrows
    @Override
    public Result<Boolean> saveRegisteredClient(RegisteredClientForm registeredClientForm) {
        Assert.notNull(registeredClientForm, "registeredClient cannot be null");
        SysRegisteredClient registeredClient = this.getByClientId(registeredClientForm.getClientId());
        AssertUtil.isTrue(Objects.isNull(registeredClient), EXISTS);
        return Result.ok(this.save(this.buildClient(registeredClientForm)));
    }

    /**
     * 更新
     *
     * @param id     id
     * @param client 注册客户端表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean modifyRegisteredClient(Long id, RegisteredClientForm client) {
        SysRegisteredClient sysRegisteredClient = this.buildClient(client);
        sysRegisteredClient.setId(id);
        return this.update(Wrappers.<SysRegisteredClient>lambdaUpdate()
                .set(SysRegisteredClient::getClientId, sysRegisteredClient.getClientId())
                .set(SysRegisteredClient::getClientName, sysRegisteredClient.getClientName())
                .set(SysRegisteredClient::getClientIdIssuedAt, sysRegisteredClient.getClientIdIssuedAt())
                .set(SysRegisteredClient::getClientSecretExpiresAt, sysRegisteredClient.getClientSecretExpiresAt())
                .set(SysRegisteredClient::getClientAuthenticationMethods, sysRegisteredClient.getClientAuthenticationMethods())
                .set(SysRegisteredClient::getAuthorizationGrantTypes, sysRegisteredClient.getAuthorizationGrantTypes())
                .set(SysRegisteredClient::getRedirectUris, sysRegisteredClient.getRedirectUris())
                .set(SysRegisteredClient::getScopes, sysRegisteredClient.getScopes())
                .eq(SysRegisteredClient::getId, id));
    }

    @SneakyThrows
    private void getClientVO(RegisteredClientVO registeredClientVO) {
        registeredClientVO.setAuthorizationGrantTypes(Arrays.stream(registeredClientVO.getStrAuthorizationGrantTypes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setScopes(Arrays.stream(registeredClientVO.getStrScopes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setClientAuthenticationMethods(Arrays.stream(registeredClientVO.getStrClientAuthenticationMethods().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setRedirectUris(Arrays.stream(registeredClientVO.getStrRedirectUris().split(",")).collect(Collectors.toSet()));
    }

    @SneakyThrows
    private SysRegisteredClient buildClient(RegisteredClientForm client) {
        // @formatter:off
        SysRegisteredClient registeredClient = SysRegisteredClient.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .clientSecret(Optional.ofNullable(client.getClientSecret()).map(pwd -> SaSecureUtil.aesEncrypt(aesSecretProperties.getAesSecret(), pwd)).orElse(null))
                .clientAuthenticationMethods(Optional.ofNullable(client.getClientAuthenticationMethods()).map(convertSet2String()).orElse(null))
                .authorizationGrantTypes(Optional.ofNullable(client.getAuthorizationGrantTypes()).map(convertSet2String()).orElse(null))
                .scopes(Optional.ofNullable(client.getScopes()).map(convertSet2String()).orElse(null))
                .redirectUris(Optional.ofNullable(client.getRedirectUris()).map(convertSet2String()).orElse(null))
                .build();
        registeredClient.setId(client.getId());
        return registeredClient;
        // @formatter:on
    }

    /**
     * 通过客户端ID获取客户端信息
     *
     * @param clientId 客户端id
     * @return {@link SysRegisteredClient}
     */
    @Override
    public SysRegisteredClient getByClientId(String clientId) {
        return this.baseMapper.getRegisteredClientBy(RegisteredClientQuery.builder().clientId(clientId).build());
    }

    /**
     * 通过ID获取客户端信息
     *
     * @param id id
     * @return {@link SysRegisteredClient}
     */
    @Override
    public SysRegisteredClient getById(String id) {
        return this.baseMapper.getRegisteredClientBy(RegisteredClientQuery.builder().id(Long.valueOf(id)).build());
    }

    /**
     * 重置客户端密钥
     *
     * @param resetClientSecretForm 重置客户秘密表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean resetClientSecret(ResetClientSecretForm resetClientSecretForm) {
        resetClientSecretForm.setClientSecret(BCrypt.hashpw(resetClientSecretForm.getClientSecret(), BCrypt.gensalt()));
        return this.update(Wrappers.<SysRegisteredClient>lambdaUpdate()
                .set(SysRegisteredClient::getClientSecret, resetClientSecretForm.getClientSecret())
                .eq(SysRegisteredClient::getId, resetClientSecretForm.getId()));
    }

    /**
     * 信息
     *
     * @param clientId 客户端ID
     * @return {@link RegisteredClientVO}
     */
    @SneakyThrows
    @Override
    public RegisteredClientVO info(Long clientId) {
        SysRegisteredClient registeredClient = this.getById(clientId);
        RegisteredClientVO registeredClientVO = new RegisteredClientVO();
        BeanUtil.copyProperties(registeredClient, registeredClientVO);
        registeredClientVO.setAuthorizationGrantTypes(Arrays.stream(registeredClient.getAuthorizationGrantTypes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setScopes(Arrays.stream(registeredClient.getScopes().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setClientAuthenticationMethods(Arrays.stream(registeredClient.getClientAuthenticationMethods().split(",")).collect(Collectors.toSet()));
        registeredClientVO.setRedirectUris(Arrays.stream(registeredClient.getRedirectUris().split(",")).collect(Collectors.toSet()));
        return registeredClientVO;
    }

    /**
     * 客户端下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link String }>>>
     */
    @Override
    public Result<List<Map<String, String>>> selectRegisteredClient() {
        return Result.ok(this.list().stream().map(sysRegisteredClient -> {
            Map<String, String> roleMap = Maps.newHashMap();
            roleMap.put("value", sysRegisteredClient.getClientId());
            roleMap.put("label", sysRegisteredClient.getClientName());
            return roleMap;
        }).collect(Collectors.toList()));
    }

}
