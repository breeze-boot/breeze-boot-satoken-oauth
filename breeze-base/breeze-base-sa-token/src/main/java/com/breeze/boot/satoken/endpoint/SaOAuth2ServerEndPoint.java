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

package com.breeze.boot.satoken.endpoint;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.processor.SaOAuth2ServerProcessor;
import cn.dev33.satoken.oauth2.template.SaOAuth2Util;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Sa-Token-OAuth2 Server端 Controller
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerEndPoint {

    private final IUserDetailService userDetailService;

    /**
     * OAuth2-Server 端：处理所有 OAuth2 相关请求
     *
     * @return {@link Object }
     */
    @RequestMapping("/oauth2/*")
    public Object request() {
        log.info("------- 进入请求: " + SaHolder.getRequest().getUrl());
        return SaOAuth2ServerProcessor.instance.dister();
    }

    /**
     * 获取 userinfo 信息
     *
     * @return {@link Result }<{@link Map }<{@link String }, {@link Object }>>
     */
    @RequestMapping("/oauth2/userinfo")
    public Result<UserPrincipal> userinfo() {
        // 获取 Access-Token 对应的账号id
        String accessToken = SaOAuth2Manager.getDataResolver().readAccessToken(SaHolder.getRequest());
        Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        log.info("-------- 此Access-Token对应的账号id: " + loginId);

        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkAccessTokenScope(accessToken, "userinfo");
        UserPrincipal userPrincipal = this.userDetailService.loadUserByUserId(String.valueOf(loginId));
        return Result.ok(userPrincipal);
    }

}
