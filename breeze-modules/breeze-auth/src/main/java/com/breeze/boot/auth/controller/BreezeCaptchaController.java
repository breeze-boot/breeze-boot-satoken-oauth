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

package com.breeze.boot.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微风captcha控制器
 * <p>
 * 模仿 {@link com.anji.captcha.controller.CaptchaController } 重写请求路径
 *
 * @author gaoweixuan
 * @since 2022/09/18
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping({"/auth/v1/captcha"})
public class BreezeCaptchaController {

    /**
     * 验证码服务
     */
    private final CaptchaService captchaService;

    /**
     * 获取远程id
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);
        String ua = request.getHeader("user-agent");
        return StringUtils.isNotBlank(ip) ? ip + ua : request.getRemoteAddr() + ua;
    }

    /**
     * 从xfwd获得远程ip
     *
     * @param xfwd xfwd
     * @return {@link String}
     */
    private static String getRemoteIpFromXfwd(String xfwd) {
        if (StrUtil.isAllNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        }
        return null;
    }

    /**
     * 获取代码
     *
     * @param data    数据
     * @param request 请求
     * @return {@link ResponseModel}
     */
    @PostMapping("/code")
    public ResponseModel code(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setBrowserInfo(getRemoteId(request));
        return this.captchaService.get(data);
    }

    @PostMapping("/check")
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        return this.captchaService.check(data);
    }

}
