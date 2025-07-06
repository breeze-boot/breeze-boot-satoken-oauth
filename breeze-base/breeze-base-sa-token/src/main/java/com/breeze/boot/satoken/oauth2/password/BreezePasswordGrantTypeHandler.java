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

package com.breeze.boot.satoken.oauth2.password;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.oauth2.exception.SaOAuth2Exception;
import cn.dev33.satoken.oauth2.granttype.handler.PasswordGrantTypeHandler;
import cn.dev33.satoken.oauth2.granttype.handler.model.PasswordAuthResult;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import com.breeze.boot.satoken.oauth2.IUserDetailService;
import com.breeze.boot.satoken.propertise.AesSecretProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.function.Function;

import static com.breeze.boot.core.constants.CacheConstants.ROLE_PERMISSION;
import static com.breeze.boot.core.enums.ResultCode.VERIFY_UN_PASS;
import static com.breeze.boot.log.enums.LogEnum.LogType.LOGIN;
import static com.breeze.boot.log.enums.LogEnum.Result.FAIL;
import static com.breeze.boot.log.enums.LogEnum.Result.SUCCESS;

@Slf4j
@RequiredArgsConstructor
public class BreezePasswordGrantTypeHandler extends PasswordGrantTypeHandler {
    private final static String BCRYPT = "{bcrypt}";

    private final IUserDetailService userDetailService;

    private final AesSecretProperties aesSecretProperties;

    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    private final Function<HttpServletRequest, Boolean> captchaServiceFunction;

    @Override
    public PasswordAuthResult loginByUsernamePassword(String username, String password) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");

        if (captchaServiceFunction.apply(requestAttributes.getRequest())) {
            throw new BreezeBizException(VERIFY_UN_PASS);
        }
        String decodePwd = AesUtil.decryptStr(password, this.aesSecretProperties.getAesSecret());
        UserPrincipal userPrincipal = this.userDetailService.loadUserByUsername(username);
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
        if (BCrypt.checkpw(decodePwd, userPrincipal.getPassword().replace(BCRYPT, ""))) {
            SysLogBO sysLogBO = this.buildLog(requestAttributes.getRequest(), SUCCESS.getCode(), username);
            this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
            SaManager.getSaTokenDao().delete(ROLE_PERMISSION + userPrincipal.getId());
            StpUtil.login(userPrincipal.getId());
            return new PasswordAuthResult(userPrincipal.getId());
        }
        SysLogBO sysLogBO = this.buildLog(requestAttributes.getRequest(), FAIL.getCode(), username);
        this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
        throw new SaOAuth2Exception("无效账号密码");
    }

    /**
     * 执行日志 业务
     *
     * @param request  请求
     * @param result   日志结果
     * @param username 参数
     * @return {@link SysLogBO }
     */
    @SneakyThrows
    private SysLogBO buildLog(HttpServletRequest request, Integer result, String username) {
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder()
                .system(userAgent)
                .logTitle(LogType.USERNAME_LOGIN.getName())
                .doType(LogType.USERNAME_LOGIN.getCode())
                .logType(LOGIN.getCode())
                .result(result)
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .paramContent(username)
                .createTime(LocalDateTime.now())
                .createBy(username)
                .createName(username)
                .build();
    }
}
