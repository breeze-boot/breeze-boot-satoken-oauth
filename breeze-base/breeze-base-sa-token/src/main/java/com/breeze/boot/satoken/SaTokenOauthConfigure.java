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

package com.breeze.boot.satoken;

import cn.dev33.satoken.oauth2.config.SaOAuth2ServerConfig;
import cn.dev33.satoken.oauth2.strategy.SaOAuth2Strategy;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.jackson.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * oauth令牌配置
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RequiredArgsConstructor
public class SaTokenOauthConfigure {
    private final static String BCRYPT = "{bcrypt}";
    private final IUserDetailService userDetailService;

    private final AesSecretProperties aesSecretProperties;
    private final Function<HttpServletRequest, Boolean> captchaServiceFunction;

    /**
     * Sa-Token OAuth2 定制化配置
     *
     * @param oauth2Server oauth2服务器
     */
    @Autowired
    public void configOAuth2Server(SaOAuth2ServerConfig oauth2Server) {
        // 未登录的视图
        oauth2Server.notLoginView = () -> new ModelAndView("login.html");

        // 登录处理函数
        oauth2Server.doLoginHandle = (name, pwd) -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes, "requestAttributes is null");

            if (captchaServiceFunction.apply(requestAttributes.getRequest())) {
//                throw new BreezeBizException(ResultCode.VERIFY_UN_FOUND);
            }
            String decodePwd = AesUtil.decryptStr(pwd, this.aesSecretProperties.getAesSecret());
            UserPrincipal userPrincipal = this.userDetailService.loadUserByUsername(name);
            String pw_hash = BCrypt.hashpw(pwd, BCrypt.gensalt());
            if (BCrypt.checkpw(decodePwd, userPrincipal.getPassword().replace(BCRYPT, ""))) {
                StpUtil.login(userPrincipal.getId());
                return Result.ok();
            }
            return Result.fail("账号名或密码错误");
        };

        // 授权确认视图
        oauth2Server.confirmView = (clientId, scopes) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("clientId", clientId);
            map.put("scope", scopes);
            return new ModelAndView("confirm.html", map);
        };

        // 重写 AccessToken 创建策略，返回会话令牌
        SaOAuth2Strategy.instance.createAccessToken = (clientId, loginId, scopes) -> {
            log.info("----返回会话令牌");
            return StpUtil.getOrCreateLoginSession(loginId);
        };
    }

}
