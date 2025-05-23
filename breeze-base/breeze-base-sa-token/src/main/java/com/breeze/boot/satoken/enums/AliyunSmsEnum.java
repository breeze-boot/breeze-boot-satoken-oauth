package com.breeze.boot.satoken.enums;

import lombok.Getter;

import static com.breeze.boot.core.constants.CacheConstants.*;

@Getter
public enum AliyunSmsEnum {

    FIRST_LOGIN("", "firstLogin", MINI_FIRST_LOGIN, "首次登录验证码"),
    RESET_LOGIN_PWD("", "resetPwd", MINI_RESET_PWD, "重置密码"),
    PC_SMS_LOGIN("", "smsLogin", SMS_LOGIN, "PC验证码登录");

    private final String value;
    private final String key;
    private final String redisKey;
    private final String label;

    AliyunSmsEnum(String value, String key, String redisKey, String label) {
        this.value = value;
        this.key = key;
        this.redisKey = redisKey;
        this.label = label;
    }

    public static AliyunSmsEnum getTemplateByKey(String key) {
        for (AliyunSmsEnum item : values()) {
            if (item.getKey().equals(key)) {
                return item;
            }
        }
        throw new RuntimeException("未找到对应模板");
    }
}
