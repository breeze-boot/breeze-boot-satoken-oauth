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

package com.breeze.boot.core.constants;

/**
 * 缓存常量
 *
 * @author gaoweixuan
 * @since 2021/10/1
 */
public class CacheConstants {

    public static String MINI_FIRST_LOGIN = "breeze:login:first:";

    public static String MINI_RESET_PWD = "breeze:login:reset:";

    /**
     * 验证电话号码
     */
    public static final String SMS_LOGIN = "breeze:login:validate_sms_code:";

    /**
     * 验证电子邮件代码
     */
    public static final String VALIDATE_EMAIL_CODE = "breeze:login:validate_email_code:";

    /**
     * 行权限缓存
     */
    public static final String ROW_PERMISSION = "Authorization:row:permission:";

    /**
     * 角色权限缓存
     */
    public static final String ROLE_PERMISSION = "Authorization:loginid-role:permission:";

    /**
     * 权限缓存
     */
    public static final String PERMISSIONS = "Authorization:user-permissions:permission:";

}
