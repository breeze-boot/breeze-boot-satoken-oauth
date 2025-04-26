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

package com.breeze.boot.satoken.oauth2.oidc;

import cn.dev33.satoken.oauth2.data.model.oidc.IdTokenModel;
import cn.dev33.satoken.oauth2.scope.handler.OidcScopeHandler;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 扩展 OIDC 权限处理器，返回更多字段
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RequiredArgsConstructor
public class BreezeOidcScopeHandler extends OidcScopeHandler {

    private final IUserDetailService userDetailService;

    /**
     * 工作额外数据
     *
     * @param idToken id令牌
     * @return {@link IdTokenModel }
     */
    @Override
    public IdTokenModel workExtraData(IdTokenModel idToken) {
        Object userId = idToken.sub;
        UserPrincipal userPrincipal = this.userDetailService.loadUserByUserId(String.valueOf(userId));
        log.info("----- 为 idToken 追加扩展字段 ----- ");

        idToken.extraData.put("uid", userId);
        idToken.extraData.put("display_name", userPrincipal.getDisplayName());
        idToken.extraData.put("avatar", userPrincipal.getAvatar());
        idToken.extraData.put("email", userPrincipal.getEmail());
        idToken.extraData.put("phone_number", userPrincipal.getPhone());
        // 更多字段 ...
        // 可参考：https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims
        return idToken;
    }

}