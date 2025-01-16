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

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.util.SaFoxUtil;
import com.breeze.boot.core.utils.Result;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.breeze.boot.core.constants.CacheConstants.VALIDATE_SMS_CODE;

/**
 * 自定义手机登录接口
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RestController
public class PhoneLoginEndPoint {

    @GetMapping("/oauth2/sendPhoneCode")
    @ResponseBody
    public Result<Boolean> sendCode(@NotBlank(message = "手机号不能为空") @RequestParam String phone) {
        String code = SaFoxUtil.getRandomNumber(100000, 999999) + "";
        SaManager.getSaTokenDao().set(VALIDATE_SMS_CODE + phone, code, 60 * 5);
        log.info("手机号：" + phone + "，验证码：" + code + "，已发送成功");
        return Result.ok(Boolean.TRUE,"验证码发送成功");
    }

}