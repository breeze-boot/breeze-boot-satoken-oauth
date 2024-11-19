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

package com.breeze.boot.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.MessageUtil;
import com.breeze.boot.core.utils.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.stream.Collectors;

/**
 * 全局异常处理程序
 *
 * @author gaoweixuan
 * @since 2022-10-10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * http请求方法参数异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handlerMethodValidationException(HandlerMethodValidationException ex) {
        log.error("HandlerMethodValidationException 请求方法参数异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.HANDLER_METHOD_VALIDATION_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 处理请求参数格式错误
     * MethodArgumentNotValidException //（Bean 校验异常）
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException 处理请求参数格式错误：", ex);
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * 绑定异常处理程序
     * BindException //（参数绑定异常）
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> bindExceptionHandler(BindException ex) {
        log.error("BindException 验证路径中请求实体校验失败后抛出的异常：", ex);
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * 处理请求参数格式错误
     * ConstraintViolationException  //（方法参数校验异常）如实体类中的@Size注解配置和数据库中该字段的长度不统一等问题
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.error("ConstraintViolationException 处理请求参数格式错误：", ex);
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * http消息不可读异常处理程序
     * <p>
     * 参数格式异常
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException requestBody 参数格式异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 未登录处理程序
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> notLoginException(NotLoginException ex) {
        log.error("NotLoginException requestBody 未登录：", ex);
        String message = MessageUtil.getMessage(ResultCode.AUTHENTICATION_FAILURE.getKey());
        return Result.fail(message);
    }

    /**
     * http消息转换异常
     * 当定义的Vo对象使用@AllArgsConstructor注解但缺少@NoArgsConstructor时，SpringMvc的消息转换器无法通过无参构造方法创建对象，导致HttpMessageConversionException。添加无参构造方法或使用@NoArgsConstructor注解可以解决问题。
     *
     * @param ex 错误
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> httpMessageConversionException(HttpMessageConversionException ex) {
        log.error("HttpMessageConversionException 请求参数异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.HTTP_MESSAGE_CONVERSION_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 用来兜底，定义未定义的异常消息，返回给前端
     *
     * @param e e
     * @return {@link Result }<{@link Object }>
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> exceptionHandler(Exception e) {
        log.error("Handler Exception: ", e);
        String message = MessageUtil.getMessage(ResultCode.SYSTEM_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 空指针统一异常处理
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> nullPointerException(NullPointerException ex) {
        log.error("NullPointerException 空指针异常：", ex);
        String message = MessageUtil.getMessage(ResultCode.SYSTEM_EXCEPTION.getKey());
        return Result.fail(message);
    }

    /**
     * 系统异常
     *
     * @param ex 错误
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(BreezeBizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> bizException(BreezeBizException ex) {
        log.error("bizException 自定义的系统异常：", ex);
        return Result.fail(ex.getMessage());
    }

    /**
     * 无权限
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> notPermissionException(NotPermissionException ex) {
        log.error("NotPermissionException 无权限：", ex);
        String message = MessageUtil.getMessage(ResultCode.SC_FORBIDDEN.getKey());
        return Result.fail(message);
    }

    /**
     * 无权限
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NotRoleException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> notRoleException(NotRoleException ex) {
        log.error("NotRoleException 无权限：", ex);
        String message = MessageUtil.getMessage(ResultCode.SC_FORBIDDEN.getKey());
        return Result.fail(message);
    }
}
