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

package com.breeze.boot.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class BUtils {

    /**
     * 将方法引用转换为对应的字段名
     *
     * @param methodReference 方法引用
     * @param <T>             实体类类型
     * @param <R>             方法返回值类型
     * @return 对应的字段名
     */
    public static <T, R> String toFieldName(SerializableFunction<T, R> methodReference) {
        try {
            // 获取序列化的 Lambda 信息
            SerializedLambda serializedLambda = getSerializedLambda(methodReference);
            // 获取方法名
            String methodName = serializedLambda.getImplMethodName();
            // 处理方法名，去掉 get 前缀并转换为下划线命名
            return processMethodName(methodName);
        } catch (Exception e) {
            log.error("Failed to convert method reference to field name", e);
            return "";
        }
    }

    /**
     * 获取序列化的 Lambda 信息
     *
     * @param lambda Lambda 表达式
     * @return SerializedLambda 对象
     * @throws NoSuchMethodException     如果找不到 writeReplace 方法
     * @throws IllegalAccessException    如果无法访问 writeReplace 方法
     * @throws InvocationTargetException 如果调用 writeReplace 方法时出错
     */
    private static SerializedLambda getSerializedLambda(Object lambda) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        return (SerializedLambda) writeReplace.invoke(lambda);
    }

    /**
     * 处理方法名，去掉 get 前缀并转换为下划线命名
     *
     * @param methodName 方法名
     * @return 处理后的字段名
     */
    private static String processMethodName(String methodName) {
        if (methodName.startsWith("get")) {
            // 去掉 get 前缀
            String propertyName = methodName.substring(3);
            // 将首字母小写
            propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
            // 转换为下划线命名
            return camelToSnakeCase(propertyName);
        }
        return methodName;
    }

    /**
     * 将驼峰命名转换为下划线命名
     *
     * @param camelCase 驼峰命名的字符串
     * @return 下划线命名的字符串
     */
    private static String camelToSnakeCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}