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
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.util.SaFoxUtil;
import com.breeze.boot.core.utils.Result;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.breeze.boot.core.constants.CacheConstants.VALIDATE_EMAIL_CODE;

/**
 * 自定义邮箱登录接口
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RestController
public class EmailLoginEndPoint {

    @GetMapping("/oauth2/sendEmailCode")
    @ResponseBody
    public Result<?> sendEmailCode(@NotBlank(message = "邮箱不能为空") @RequestParam String email) {
        SaTokenDao saTokenDao = SaManager.getSaTokenDao();
        String cacheCode = saTokenDao.get(VALIDATE_EMAIL_CODE + email);
        if (Objects.nonNull(cacheCode)) {
            // 验证码存在，计算剩余时间
            long expire = saTokenDao.getTimeout(VALIDATE_EMAIL_CODE + email);
            return Result.ok(expire, "请等待 " + expire + " 秒后再试");
        }

        String code = SaFoxUtil.getRandomNumber(100000, 999999) + "";
        // TODO 发送验证码
        saTokenDao.set(VALIDATE_EMAIL_CODE + email, code, 60);

        log.info("手机号：" + email + "，验证码：" + code + "，已发送成功");
        return Result.ok(60L, "验证码发送成功");
    }
}