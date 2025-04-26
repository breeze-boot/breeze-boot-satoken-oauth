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

package com.breeze.boot.satoken.oauth2.userinfo;

import cn.dev33.satoken.oauth2.data.model.AccessTokenModel;
import cn.dev33.satoken.oauth2.data.model.ClientTokenModel;
import cn.dev33.satoken.oauth2.scope.handler.SaOAuth2ScopeHandlerInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.breeze.boot.core.constants.CoreConstants.USER_INFO;
import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;

/**
 * 用户信息作用域处理程序
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RequiredArgsConstructor
public class UserinfoScopeHandler implements SaOAuth2ScopeHandlerInterface {

    private final Supplier<IUserDetailService> userDetailServiceSupplier;

    @Override
    public String getHandlerScope() {
        return USER_INFO;
    }

    /**
     * 访问令牌
     */
    @Override
    public void workAccessToken(AccessTokenModel at) {
        UserPrincipal userPrincipal = userDetailServiceSupplier.get().loadUserByUserId(at.getLoginId().toString());
        StpUtil.getSession().set(USER_TYPE, userPrincipal);
        log.info("--------- userinfo 权限，加工 AccessTokenModel --------- ");
        Map<String, Object> map = new LinkedHashMap<>();
        userPrincipal.setPassword(null);
        Map<String, Object> toMap = BeanUtil.beanToMap(userPrincipal, map, false, true);
        at.extraData.put(USER_INFO, toMap);
    }

    @Override
    public void workClientToken(ClientTokenModel ct) {
    }

}