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

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.breeze.boot.satoken.oauth2.password.BreezePasswordGrantTypeHandler;
import com.breeze.boot.satoken.oauth2.phone.PhonePasswordGrantTypeHandler;
import com.breeze.boot.satoken.propertise.AesSecretProperties;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.auth.service.SysRegisteredClientService;
import com.breeze.boot.auth.service.SysUserService;
import com.breeze.boot.satoken.SaTokenOauthConfigure;
import com.breeze.boot.satoken.oauth2.client.SaOAuth2DataLoaderImpl;
import com.breeze.boot.satoken.oauth2.email.EmailCodeGrantTypeHandler;
import com.breeze.boot.satoken.oauth2.oidc.BreezeOidcScopeHandler;
import com.breeze.boot.satoken.oauth2.phone.PhoneCodeGrantTypeHandler;
import com.breeze.boot.satoken.oauth2.userinfo.UserinfoScopeHandler;
import com.breeze.boot.satoken.spt.StpInterfaceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资源服务器配置
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfiguration {

    private final ApplicationContext context;

    private final CaptchaService captchaService;

    private final SysUserService userService;

    private final SysRegisteredClientService sysRegisteredClientService;

    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    private final AesSecretProperties aesSecretProperties;

    public String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    @Bean
    public SaOAuth2DataLoaderImpl saOAuth2DataLoader() {
        return new SaOAuth2DataLoaderImpl(sysRegisteredClientService, aesSecretProperties);
    }

    @Bean
    public StpInterfaceImpl stpInterfaceImpl() {
        return new StpInterfaceImpl(this.userService);
    }

    @Bean
    public SaTokenOauthConfigure saTokenOauthConfigure() {
        return new SaTokenOauthConfigure(userService, aesSecretProperties, publisherSaveSysLogEvent);
    }

    @Bean
    public BreezePasswordGrantTypeHandler breezePasswordGrantTypeHandler() {
        return new BreezePasswordGrantTypeHandler(userService, aesSecretProperties, publisherSaveSysLogEvent, this::checkCapture);
    }

    /**
     * 用户服务
     *
     * @return {@link BreezeOidcScopeHandler}
     */
    @Bean
    public BreezeOidcScopeHandler oidcScopeHandler() {
        return new BreezeOidcScopeHandler(this.userService);
    }

    /**
     * 手机号验证码登录配置
     *
     * @return {@link PhoneCodeGrantTypeHandler}
     */
    @Bean
    public PhoneCodeGrantTypeHandler phoneCodeGrantTypeHandler() {
        return new PhoneCodeGrantTypeHandler(() -> userService);
    }

    /**
     * 手机号密码登录配置
     *
     * @return {@link PhonePasswordGrantTypeHandler}
     */
    @Bean
    public PhonePasswordGrantTypeHandler phonePasswordGrantTypeHandler() {
        return new PhonePasswordGrantTypeHandler(() -> userService, () -> aesSecretProperties);
    }

    /**
     * 邮箱杨验证码登录配置
     *
     * @return {@link EmailCodeGrantTypeHandler}
     */
    @Bean
    public EmailCodeGrantTypeHandler emailCodeGrantTypeHandler() {
        return new EmailCodeGrantTypeHandler(() -> userService);
    }

    /**
     * 用户登录配置
     *
     * @return {@link UserinfoScopeHandler}
     */
    @Bean
    public UserinfoScopeHandler userinfoScopeHandler() {
        return new UserinfoScopeHandler(() -> userService);
    }

    private boolean checkCapture(HttpServletRequest contextRequest) {
        if (getActiveProfile().endsWith("dev")) {
            return false;
        }
        CaptchaVO captchaVO = new CaptchaVO();
        String captchaVerification = contextRequest.getParameter("captchaVerification");
        if (StrUtil.isBlank(captchaVerification)) {
            return false;
        }
        captchaVO.setCaptchaVerification(captchaVerification);
        ResponseModel response = this.captchaService.verification(captchaVO);
        //验证码校验失败，返回信息告诉前端
        //repCode  0000  无异常，代表成功
        //repCode  9999  服务器内部异常
        //repCode  0011  参数不能为空
        //repCode  6110  验证码已失效，请重新获取
        //repCode  6111  验证失败
        //repCode  6112  获取验证码失败,请联系管理员
        return response.isSuccess();
    }

}



