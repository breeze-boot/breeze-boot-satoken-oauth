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

package com.breeze.boot.quartz.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 工作调用Utils
 *
 * @author gaoweixuan
 * @since 2023-03-18
 */
public class JobInvokeUtils {

    /**
     * 获取参数
     *
     * @param clazzName clazz名字
     * @return {@link String}
     */
    public static String getParams(String clazzName) {
        return StrUtil.sub(clazzName, clazzName.lastIndexOf("(") + 1, clazzName.lastIndexOf(")"));
    }

    /**
     * get方法名字
     *
     * @param clazzName clazz名字
     * @param s         年代
     * @param s2        s2
     * @return {@link String}
     */
    public static String getMethodName(String clazzName, String s, String s2) {
        return StrUtil.sub(clazzName, clazzName.lastIndexOf(s) + 1, clazzName.lastIndexOf(s2));
    }

    /**
     * bean名字
     *
     * @param clazzName clazz名字
     * @return {@link String}
     */
    public static String getBeanName(String clazzName) {
        return StrUtil.sub(clazzName, 0, clazzName.lastIndexOf("."));
    }

    /**
     * 得到修剪
     *
     * @param param 参数
     * @return {@link String}
     */
    public static String getTrim(String param) {
        return param.substring(0, param.length() - 1).trim();
    }

    /**
     * 获取参数
     *
     * @param paramArray     参数数组
     * @param parameterTypes 参数类型
     * @param parameters     参数
     */
    public static void getParams(String[] paramArray, Class<?>[] parameterTypes, Object[] parameters) {
        for (int i = 0; i < paramArray.length; i++) {
            String param = paramArray[i].trim();
            if (param.endsWith("L")) {
                String trim = JobInvokeUtils.getTrim(param);
                parameters[i] = Long.parseLong(trim);
                parameterTypes[i] = Long.class;
            } else if (param.endsWith("D")) {
                String trim = JobInvokeUtils.getTrim(param);
                parameters[i] = Double.parseDouble(trim);
                parameterTypes[i] = Double.class;
            } else if ("false".equals(param)) {
                parameters[i] = Boolean.FALSE;
                parameterTypes[i] = Boolean.class;
            } else if ("true".equals(param)) {
                parameters[i] = Boolean.TRUE;
                parameterTypes[i] = Boolean.class;
            } else if (param.endsWith("'") || param.endsWith("\"")) {
                parameters[i] = param
                        .substring(0, param.length() - 1)
                        .substring(1, param.length() - 1);
                parameterTypes[i] = String.class;
            } else {
                parameters[i] = Integer.parseInt(param);
                parameterTypes[i] = Integer.class;
            }
        }
    }
}
