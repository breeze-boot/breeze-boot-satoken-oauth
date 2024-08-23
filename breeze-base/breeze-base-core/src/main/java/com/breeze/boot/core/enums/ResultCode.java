package com.breeze.boot.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gaoweixuan
 * @since 2021/10/1
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    OK("0000", "请求成功"),

    /**
     * 警告
     */
    WARNING("0001", "请求不合法"),

    /**
     * 失败
     */
    FAIL("0002", "请求失败"),

    /**
     * 演示环境
     */
    PREVIEW("0003", "演示环境不可删除修改"),

    /**
     * 令牌无效
     */
    SC_UNAUTHORIZED("A101", "令牌无效"),

    /**
     * 未登录
     */
    UN_LOGIN("A102", "未登录"),

    /**
     * 未授权资源，请联系管理员授权
     */
    SC_FORBIDDEN("A103", "未授权资源，请联系管理员授权后重新登陆"),

    /**
     * 身份验证异常
     */
    FORBIDDEN("A104", "身份验证异常"),

    /**
     * 租户未获取到
     */
    TENANT_NOT_FOUND("A105", "租户未获取到"),

    VERIFY_FOUND("A106", "验证码失败"),

    /**
     * http消息转换异常
     */
    HTTP_MESSAGE_CONVERSION_EXCEPTION("B001", "请求参数错误"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND("B002", "文件不存在"),

    /**
     * 分页过大
     */
    PAGE_EXCEPTION("B003", "分页过大"),

    /**
     * 未获取到流程实例
     */
    PROCESS_NOT_FOUND("C001", "未获取到流程实例"),

    /**
     * 未获取到任务实例
     */
    TASK_NOT_FOUND("C002", "未获取到任务实例");

    @Setter
    private String code;

    @Setter
    private String msg;

    /**
     * 返回结果代码
     *
     * @param code 代码
     * @param msg  msg
     */
    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode exception(String errMsg) {
        ResultCode.FAIL.setMsg(errMsg);
        return ResultCode.FAIL;
    }

    public static ResultCode exception(ResultCode resultCode, String errMsg) {
        resultCode.setCode(resultCode.getCode());
        resultCode.setMsg(resultCode.getMsg() + errMsg);
        return resultCode;
    }
}
