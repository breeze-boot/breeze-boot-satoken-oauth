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

package com.breeze.boot.core.enums;

import lombok.Getter;

/**
 * @author gaoweixuan
 * @since 2021/10/1
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    OK("result.ok", "请求成功"),

    /**
     * 失败
     */
    FAIL("result.fail", "请求失败"),

    /**
     * 账号未找到，请先注册
     */
    NOT_REGISTER_USER("result.not.register.user", "账号未找到，请先注册"),

    /**
     * 不允许操作
     */
    NO_ACTION_IS_ALLOWED("result.no.action.is.allowed", "固定权限无需修改，请修改自定义权限"),

    /**
     * 已经使用
     */
    IS_USED("result.is.used", "已经使用不允许操作"),

    /**
     * 客户端不存在
     */
    CLIENT_IS_NOT_EXISTS("result.client.is.not.exists", "客户端不存在"),

    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR("result.verification.code.error", "验证码错误"),

    /**
     * 参数异常
     */
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION("result.http.message.not.readable.exception", "参数异常"),

    /**
     * 请求参数校验失败
     */
    HANDLER_METHOD_VALIDATION_EXCEPTION("result.handler.method.validation.exception", "请求失败"),

    /**
     * 演示环境
     */
    PREVIEW("result.preview", "演示环境不可删除修改"),

    /**
     * 未授权资源
     */
    SC_FORBIDDEN("result.sc.forbidden", "未授权资源"),

    /**
     * 资源未发现
     */
    RESOURCE_NO_FOUND("result.resource.not.found", "资源未发现"),

    /**
     * 认证失败
     */
    AUTHENTICATION_FAILURE("result.authentication.failure", "认证失败"),

    /**
     * 未找到
     */
    NOT_FOUND("result.not.found", "未找到要操作的数据"),

    /**
     * 数据已经存在
     */
    EXISTS("result.exists", "数据已经存在"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND("result.user.not.found", "用户不存在"),

    /**
     * 角色不存在
     */
    ROLE_NOT_FOUND("result.role.not.found", "角色不存在"),

    /**
     * 部门不存在
     */
    DEPT_NOT_FOUND("result.dept.not.found", "部门不存在"),

    /**
     * 任务不存在
     */
    JOB_NOT_FOUND("result.job.not.found", "任务不存在"),

    /**

     * 租户未获取到
     */
    TENANT_NOT_FOUND("result.tenant.not.found", "租户未获取到"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND("result.file.not.found", "文件不存在"),

    /**
     * 用户需要初始角色
     */
    USERS_ROLE_IS_NULL("result.users.role.is.null", "用户需要初始角色"),

    /**
     * 邮箱不存在
     */
    EMAIL_NOT_FOUND("result.email.not.found", "邮箱不存在"),

    /**
     * 邮箱配置不存在
     */
    EMAIL_CONFIG_NOT_FOUND("result.email.config.not.found", "邮箱配置不存在"),

    /**
     * 未发现此消息
     */
    MSG_NOT_FOUND("result.msg.not.found", "未发现此消息"),

    /**
     * 未获取到流程实例
     */
    PROCESS_NOT_FOUND("result.process.not.found", "未获取到流程实例"),

    /**
     * 未获取到任务实例
     */
    TASK_NOT_FOUND("result.task.not.found", "未获取到任务实例"),

    /**
     * 未获取到流程定义
     */
    DEFINITION_NOT_FOUND("result.definition.not.found", "未获取到流程定义"),

    /**
     * 未获取到BPMN模型
     */
    BPM_MODEL_NOT_FOUND("result.bpm.model.not.found", "无法获取BPMN模型"),

    /**
     * 未获取到XML
     */
    XML_NOT_FOUND("result.xml.not.found", "未获取到XML"),

    /**
     * 流程已挂起
     */
    PROCESS_SUSPENDED("result.process.suspended", "流程已挂起"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("result.system.exception", "系统异常"),

    /**
     * 分页过大
     */
    PAGE_EXCEPTION("result.page.exception", "分页过大"),

    /**
     * 登录失败
     */
    LOGIN_EXCEPTION("result.login.exception", "登录失败"),

    /**
     * http消息转换异常
     */
    HTTP_MESSAGE_CONVERSION_EXCEPTION("result.http.message.conversion.exception", "请求参数错误"),

    /**
     * 未登录
     */
    NOT_LOGIN("result.not.login", "未登录"),

    /**
     * 验证码未通过
     */
    VERIFY_UN_PASS("result.verify.un.pass", "验证码未通过"),

    /**
     * json格式错误
     */
    JSON_ERROR("result.json.error", "json格式错误"),

    /**
     * sql转换错误
     */
    SQL_PARSE_EXCEPTION("result.sql.parse.exception", "sql转换错误" ),

    LOCK_EXCEPTION("result.lock.fail.exception" , "您提交太快，请稍等再提交");

    private final String key;

    private final String desc;

    /**
     * 返回结果代码
     *
     * @param key  代码
     * @param desc 描述
     */
    ResultCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}
