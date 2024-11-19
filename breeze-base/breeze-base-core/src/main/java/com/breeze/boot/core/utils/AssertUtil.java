package com.breeze.boot.core.utils;


import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;

/**
 * 断言工具类，用于判断并抛出异常
 *
 * @author XiuMu
 * @since 2024/9/19 16:44
 */
public class AssertUtil {

    /**
     * 断言不能为空
     *
     * @param object    对象
     * @param exception 异常枚举
     */
    public static void isNotNull(Object object, ResultCode exception) {
        isTrue(object != null, exception);
    }

    /**
     * 断言必须为空
     *
     * @param object    对象
     * @param exception 异常枚举
     */
    public static void isNull(Object object, ResultCode exception) {
        isTrue(object == null, exception);
    }

    /**
     * 断言必须为 False
     *
     * @param expression 布尔值
     * @param exception  异常
     */
    public static void isFalse(boolean expression, ResultCode exception) {
        isTrue(!expression, exception);
    }

    /**
     * 断言必须为True
     *
     * @param expression 布尔值
     * @param exception  异常
     */
    public static void isTrue(boolean expression, ResultCode exception) {
        if (!expression) {
            throw new BreezeBizException(exception);
        }
    }
}
