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

package com.breeze.boot.satoken.oauth2.client;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.data.loader.SaOAuth2DataLoader;
import cn.dev33.satoken.oauth2.data.model.loader.SaClientModel;
import cn.dev33.satoken.secure.SaSecureUtil;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.satoken.model.BaseSysRegisteredClient;
import com.breeze.boot.satoken.oauth2.IClientService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Sa-Token OAuth2：自定义数据加载器
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@RequiredArgsConstructor
public class SaOAuth2DataLoaderImpl implements SaOAuth2DataLoader {

    private final IClientService iClientService;

    private final AesSecretProperties aesSecretProperties;

    /**
     * 根据 clientId 获取 Client 信息
     *
     * @param clientId 客户端id
     * @return {@link SaClientModel }
     */
    @Override
    public SaClientModel getClientModel(String clientId) {
        BaseSysRegisteredClient registeredClient = iClientService.getByClientId(clientId);
        if (registeredClient == null) {
            throw new SaTokenException("未发现客户端配置");
        }

        LocalDateTime clientIdIssuedAt = registeredClient.getClientIdIssuedAt();
        if (clientIdIssuedAt.isBefore(LocalDateTime.now())){
            throw new SaTokenException("客户端过期");
        }
        return new SaClientModel().setClientId(registeredClient.getClientId())
                .setClientSecret(SaSecureUtil.aesDecrypt(aesSecretProperties.getAesSecret(), registeredClient.getClientSecret())) // client 秘钥
                .addAllowRedirectUris(registeredClient.getRedirectUris().split(","))    // 所有允许授权的 url
                .addContractScopes(registeredClient.getScopes().split(","))    // 所有签约的权限
                .addAllowGrantTypes(registeredClient.getAuthorizationGrantTypes().split(","));
    }

    /**
     * 根据 clientId 和 loginId 获取 openid
     *
     * @param clientId 客户端id
     * @param loginId  登录id
     * @return {@link String }
     */
    @Override
    public String getOpenid(String clientId, Object loginId) {
        BaseSysRegisteredClient registeredClient = this.iClientService.getByClientId(clientId);
        if (Objects.nonNull(registeredClient)) {
            // 从数据库查询
            return SaSecureUtil.md5(SaOAuth2Manager.getServerConfig().getOpenidDigestPrefix() + "_" + registeredClient.getClientId() + "_" + loginId);
        }
        // 此处使用框架默认算法生成 openid
        return SaOAuth2DataLoader.super.getOpenid(clientId, loginId);
    }

}
