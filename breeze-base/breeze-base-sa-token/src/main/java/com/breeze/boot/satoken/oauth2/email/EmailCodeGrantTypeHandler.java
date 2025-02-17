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

package com.breeze.boot.satoken.oauth2.email;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.data.model.AccessTokenModel;
import cn.dev33.satoken.oauth2.data.model.request.RequestAuthModel;
import cn.dev33.satoken.oauth2.exception.SaOAuth2Exception;
import cn.dev33.satoken.oauth2.granttype.handler.SaOAuth2GrantTypeHandlerInterface;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Supplier;

import static com.breeze.boot.core.constants.CacheConstants.VALIDATE_EMAIL_CODE;

/**
 * 自定义 sms_code 授权模式处理器
 *
 * @author gaoweixuan
 * @since 2025/01/05
 */
@Slf4j
@RequiredArgsConstructor
public class EmailCodeGrantTypeHandler implements SaOAuth2GrantTypeHandlerInterface {

    private final Supplier<IUserDetailService> userDetailServiceSupplier;

    @Override
    public String getHandlerGrantType() {
        return "email_code";
    }

    @Override
    public AccessTokenModel getAccessToken(SaRequest req, String clientId, List<String> scopes) {
        String email = req.getParamNotNull("email");
        String code = req.getParamNotNull("code");
        String realCode = SaManager.getSaTokenDao().get(VALIDATE_EMAIL_CODE + email);

        // 校验验证码是否正确
        if (!code.equals(realCode)) {
            throw new SaOAuth2Exception("登录失败");
        }
        // 校验通过，删除验证码
        SaManager.getSaTokenDao().delete(this.getHandlerGrantType() +":" + email);
        // 去登录获取用户信息
        UserPrincipal userPrincipal = userDetailServiceSupplier.get().loadUserByEmail(email);

        RequestAuthModel ra = new RequestAuthModel();
        ra.clientId = clientId;
        ra.loginId = userPrincipal.getId();
        ra.scopes = scopes;

        // 5、生成 Access-Token
        return SaOAuth2Manager.getDataGenerate().generateAccessToken(ra, true, atm -> atm.grantType = "email_code");
    }
}