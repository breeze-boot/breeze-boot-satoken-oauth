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

package com.breeze.boot.satoken.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.utils.AssertUtil;

import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;
import static com.breeze.boot.core.enums.ResultCode.NOT_LOGIN;

/**
 * stp-util
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
public class BreezeStpUtil {

    public static UserPrincipal getUser() {
        SaSession saSession = getSession();
        return (UserPrincipal) saSession.get(USER_TYPE);
    }

    public static String getUsername() {
        SaSession saSession = getSession();
        return ((UserPrincipal) saSession.get(USER_TYPE)).getUsername();
    }

    public static String getUserCode() {
        SaSession saSession = getSession();
        return ((UserPrincipal) saSession.get(USER_TYPE)).getUserCode();
    }

    public static boolean isAdmin() {
        SaSession saSession = getSession();
        return ((UserPrincipal) saSession.get(USER_TYPE)).getUserRoleCodes().contains("admin");
    }

    private static SaSession getSession() {
        SaSession saSession = StpUtil.getSession();
        AssertUtil.isNotNull(StpUtil.getSession(), NOT_LOGIN);
        return saSession;
    }

}
