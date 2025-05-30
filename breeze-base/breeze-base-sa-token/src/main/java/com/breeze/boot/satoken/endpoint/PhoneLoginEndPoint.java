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
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.satoken.enums.AliyunSmsEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.comm.constant.SupplierConstant;
import org.dromara.sms4j.comm.utils.SmsUtils;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Objects;

import static com.breeze.boot.core.constants.CacheConstants.SMS_LOGIN;

/**
 * 自定义手机登录接口
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@RestController
public class PhoneLoginEndPoint {

    /**
     * 发送验证码
     *
     * @param phone 电话
     * @return {@link Result }<{@link Long }>
     */
    @GetMapping("/oauth2/sendPhoneCode")
    public Result<Long> sendPhoneCode(@NotBlank(message = "业务KEY") @RequestParam String key,
                                      @NotBlank(message = "手机号不能为空") @RequestParam String phone) {
        AliyunSmsEnum smsEnum = AliyunSmsEnum.getTemplateByKey(key);
        SaTokenDao saTokenDao = SaManager.getSaTokenDao();
        String redisKey = smsEnum.getRedisKey();
        String cacheCode = saTokenDao.get(redisKey);
        if (Objects.nonNull(cacheCode)) {
            // 验证码存在，计算剩余时间
            long expire = saTokenDao.getTimeout(redisKey);
            return Result.ok(expire, "请等待 " + expire + " 秒后再试");
        }
        // 发送验证码
        long codeExpireSeconds =  saTokenDao.getTimeout(redisKey + phone);
        if (codeExpireSeconds > 0){
            return Result.fail("请勿频繁发送验证码");
        }
        String code = SmsUtils.getRandomInt(6);
        saTokenDao.set( SMS_LOGIN + phone, code, 5);
        LinkedHashMap<String, String> verifyParams = new LinkedHashMap<>();
        verifyParams.put("code", code);
        SmsBlend supplier = SmsFactory.getBySupplier(SupplierConstant.ALIBABA);
        SmsResponse smsResponse = supplier.sendMessage(phone, smsEnum.getValue(), verifyParams);
        if (!smsResponse.isSuccess()) {
            return Result.fail("发送失败");
        }

        saTokenDao.set(SMS_LOGIN + phone, code, 60);
        log.info("手机号：" + phone + "，验证码：" + code + "，已发送成功");
        return Result.ok(60L, "验证码发送成功");
    }

}