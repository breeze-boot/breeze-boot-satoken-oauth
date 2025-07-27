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

package com.breeze.boot.log.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 日志类型
 *
 * @author gaoweixuan
 * @since 2022-10-19
 */
@Getter
@NoArgsConstructor
public enum LogType {

    /**
     * 新增
     */
    SAVE(0, "新增"),
    /**
     * 删除
     */
    DELETE(1, "删除"),
    /**
     * 更新
     */
    EDIT(2, "更新"),
    /**
     * 用户名登录
     */
    USERNAME_LOGIN(3, "用户名登录"),
    /**
     * 手机登录
     */
    PHONE_LOGIN(4, "手机登录"),
    /**
     * 电子邮件登录
     */
    EMAIL_LOGIN(5, "电子邮件登录"),
    LOGOUT(6, "登出"),
    FOCUS_KICK(7, "踢出"),
    FOCUS_LOGOUT(8, "强制登出"),
    SYNC(9, "同步数据"),

    ;

    /**
     * 代码
     */
    private int code;

    /**
     * 名字
     */
    private String name;

    /**
     * 日志类型
     *
     * @param code 编码
     * @param name 名字
     */
    LogType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
