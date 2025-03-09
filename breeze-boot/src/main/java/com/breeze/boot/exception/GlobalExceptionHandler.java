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
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.oauth2.exception.SaOAuth2ClientModelException;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.lock.exception.BreezeLockException;
import com.breeze.boot.core.utils.MessageUtil;
import com.breeze.boot.core.utils.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
     * <p>
     * HandlerMethodValidationException：
     * 抛出时机：通常在 Spring 的方法级别验证机制出现异常时抛出。它是一个更通用的异常，可能会涵盖多种验证相关的问题，不仅限于参数验证，还可能包括方法级别的验证逻辑异常。
     * 当使用 @Validated 注解对控制器方法进行整体验证时，如果验证失败，可能会抛出此异常。它可以被视为一个更高层次的异常，可能包含了其他验证异常作为其原因。
     * 异常来源：它主要源于 Spring 的方法级验证机制，当 Spring 框架在执行方法级别的验证操作时，若出现异常情况，会抛出该异常。
     * 处理范围：处理范围更广，可能涉及到整个方法验证过程中出现的问题，包括对方法参数的验证、方法返回值的验证等多个方面。
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handlerMethodValidationException(HandlerMethodValidationException ex) {
        log.error("HandlerMethodValidationException 请求方法参数异常：", ex);
        String message = ex.getAllErrors().stream().map(MessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.fail(message);
    }

    /**
     * 处理请求参数格式错误
     * <p>
     * MethodArgumentNotValidException：
     * 抛出时机：专门用于处理控制器方法的参数验证失败的情况。当使用 @Valid 或 @Validated 注解对控制器方法的参数进行验证，并且验证不通过时，就会抛出 MethodArgumentNotValidException。
     * 例如，在控制器方法中对请求体或请求参数使用 @Valid 注解时，如果该参数对象不满足验证约束，就会抛出此异常。
     * 异常来源：它主要是由于方法参数的验证失败而引发，是 Spring 对控制器方法中参数验证的具体反馈。它是基于参数对象的验证约束（如 @NotNull、@Size、@Pattern 等）而产生的。
     * 处理范围：主要聚焦于方法参数的验证失败情况，它提供了 BindingResult 信息，允许开发人员获取关于参数验证失败的详细信息，包括哪个参数不符合哪个验证规则，有哪些验证错误消息等，便于在控制器中对参数验证错误进行针对性的处理。
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
     * 抛出时机：
     * BindException:
     * 通常在数据绑定阶段抛出。当 Spring 尝试将请求参数（如 @RequestParam、@PathVariable、@RequestBody 等）绑定到控制器方法的参数对象时，如果出现问题，就会抛出该异常。这种问题可能包括以下几种情况：
     * 请求参数的名称与对象属性名称不匹配，导致无法正确绑定。
     * 请求参数的类型与对象属性的类型不兼容，例如将一个非数字的字符串绑定到 int 或 long 类型的属性。
     * 请求参数的格式不符合对象属性的期望格式，例如将不符合日期格式的字符串绑定到 java.util.Date 类型的属性。
     * 异常来源： 它主要源于 Spring 的数据绑定机制。Spring 在处理 HTTP 请求时，会自动将请求中的数据转换并绑定到控制器方法的参数对象中。这个过程是通过各种 DataBinder 和 ConversionService 等组件完成的。如果在这个转换和绑定过程中出现问题，就会抛出 BindException。
     * 处理范围： 主要涉及请求参数到对象的绑定问题，不直接涉及对象属性的验证（虽然验证通常在绑定之后进行）。它提供了 BindingResult 信息，允许开发人员查看哪些字段绑定失败以及相应的错误消息，以便进行针对性的处理。
     * <p>
     * 与 MethodArgumentNotValidException 的区别：
     * MethodArgumentNotValidException 主要是在对已绑定的对象进行验证时抛出，即使用 @Valid 或 @Validated 注解对对象进行验证，如果对象不满足验证约束（如 @NotNull、@Size 等），会抛出此异常。
     * 而 BindException 发生在将请求参数绑定到对象的阶段，还未涉及验证操作。
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
     * ConstraintViolationException 通常是在使用 Java Bean Validation API 对对象进行验证时抛出，不局限于 Spring 控制器的请求处理，而是在使用 Validator 接口进行对象级验证时产生。而 BindException 是在 Spring 处理请求的绑定阶段出现的问题。
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
     * 处理请求参数格式错误
     *
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> constraintViolationExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.error("ConstraintViolationException 处理请求参数格式错误：", ex);
        return Result.fail(ex.getMessage());
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
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(BreezeLockException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> saOAuth2ClientModelException(BreezeLockException ex) {
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

    /**
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(SaTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> saTokenException(SaTokenException ex) {
        log.error("SaTokenException", ex);
        String message = MessageUtil.getMessage(ResultCode.SC_FORBIDDEN.getKey());
        return Result.fail(message + ":" + ex.getMessage());
    }

    /**
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> noResourceFoundException(NoResourceFoundException ex) {
        log.error("NoResourceFoundException", ex);
        String message = MessageUtil.getMessage(ResultCode.RESOURCE_NO_FOUND.getKey());
        return Result.fail(message);
    }
	
    /**
     * @param ex 异常
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(SaOAuth2ClientModelException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> saOAuth2ClientModelException(SaOAuth2ClientModelException ex) {
        log.error("SaOAuth2ClientModelException", ex);
        String message = MessageUtil.getMessage(ResultCode.SC_FORBIDDEN.getKey());
        return Result.fail(message + ":" + ex.getMessage());
    }

}
