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

package com.breeze.boot.xss.config;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * HttpServletRequestWrapper 采用装饰者模式对HttpServletRequest进行包装
 * 通过继承HttpServletRequestWrapper类去重写getParameterValues、getParameter等方法
 * 但是实际还是调用HttpServletRequest的相对应方法
 * <p>
 *
 * @author gaoweixuan
 * @since 2022-10-21
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    // 原始的 HttpServletRequest 对象，用于后续获取请求相关信息
    private final HttpServletRequest servletRequest;

    // 用于缓存处理后的请求体内容，避免重复读取和处理
    private byte[] cachedBody;

    /**
     * 构造函数，初始化包装器
     *
     * @param servletRequest 原始的 HttpServletRequest 对象
     */
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        this.servletRequest = servletRequest;
    }

    /**
     * 重写 getInputStream 方法，实现请求体的可重复读取以及对 application/json 类型请求体的 XSS 过滤
     *
     * @return 经过处理的 ServletInputStream 对象
     * @throws IOException 读取输入流时可能抛出的 IO 异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 获取请求的 Content-Type 信息
        String contentType = servletRequest.getContentType();
        // 如果请求是 multipart 类型，直接返回原始的输入流，不进行额外处理
        if (contentType != null && contentType.startsWith("multipart")) {
            return super.getInputStream();
        }
        // 如果请求的 Content-Type 不是 application/json 类型，也直接返回原始的输入流
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }

        // 如果 cachedBody 为空，说明还没有对请求体进行读取和处理
        if (cachedBody == null) {
            // 使用 try-with-resources 语句确保输入流和输出流在使用完毕后能正确关闭
            try (InputStream inputStream = super.getInputStream();
                 FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream()) {
                // 将输入流中的内容复制到输出流中
                IoUtil.copy(inputStream, outputStream);
                // 将输出流中的内容转换为字符串
                String json = outputStream.toString(StandardCharsets.UTF_8);
                // 如果字符串不为空，对其进行 XSS 过滤，去除 HTML 标签
                if (StrUtil.isNotBlank(json)) {
                    json = HtmlUtil.cleanHtmlTag(json);
                }
                // 将过滤后的字符串转换为字节数组并缓存到 cachedBody 中
                cachedBody = json.getBytes(StandardCharsets.UTF_8);
            }
        }

        // 根据缓存的字节数组创建一个新的 ByteArrayInputStream
        final ByteArrayInputStream bis = new ByteArrayInputStream(cachedBody);
        // 返回一个自定义的 ServletInputStream，用于读取缓存的请求体内容
        return new ServletInputStream() {
            /**
             * 判断输入流是否已经读取完毕
             *
             * @return 如果输入流中没有剩余字节可读，返回 true；否则返回 false
             */
            @Override
            public boolean isFinished() {
                return bis.available() == 0;
            }

            /**
             * 判断输入流是否准备好被读取
             *
             * @return 这里直接返回 true，表示随时可以读取
             */
            @Override
            public boolean isReady() {
                return true;
            }

            /**
             * 设置读取监听器，这里不做具体实现
             *
             * @param readListener 读取监听器对象
             */
            @Override
            public void setReadListener(ReadListener readListener) {
                // 不实现
            }

            /**
             * 从输入流中读取一个字节
             *
             * @return 读取的字节数据，如果已经到达流的末尾，返回 -1
             * @throws IOException 读取过程中可能抛出的 IO 异常
             */
            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    /**
     * 重写 getReader 方法，通过调用 getInputStream 方法获取输入流并创建 BufferedReader
     * 以支持对请求体的字符流读取
     *
     * @return 用于读取请求体的 BufferedReader 对象
     * @throws IOException 读取输入流时可能抛出的 IO 异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * 重写 getParameter 方法，对请求参数名和参数值进行 XSS 过滤
     *
     * @param name 请求参数名
     * @return 经过 XSS 过滤后的参数值
     */
    @Override
    public String getParameter(String name) {
        // 对参数名进行 XSS 过滤
        String value = super.getParameter(HtmlUtil.filter(name));
        // 如果参数值不为空，对其进行 XSS 过滤
        if (StrUtil.isAllNotBlank(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    /**
     * 重写 getParameterValues 方法，对请求参数值数组中的每个元素进行 XSS 过滤
     *
     * @param name 请求参数名
     * @return 经过 XSS 过滤后的参数值数组
     */
    @Override
    public String[] getParameterValues(String name) {
        // 获取原始的参数值数组
        String[] parameters = super.getParameterValues(name);
        // 如果参数值数组为空或长度为 0，直接返回
        if (parameters == null || parameters.length == 0) {
            return parameters;
        }

        // 遍历参数值数组，对每个元素进行 XSS 过滤
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = HtmlUtil.filter(parameters[i]);
        }
        return parameters;
    }

    /**
     * 重写 getParameterMap 方法，对请求参数映射中的每个参数值数组的元素进行 XSS 过滤
     *
     * @return 经过 XSS 过滤后的参数映射
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        // 创建一个新的 LinkedHashMap 用于存储过滤后的参数映射
        Map<String, String[]> map = new LinkedHashMap<>();
        // 获取原始的参数映射
        Map<String, String[]> parameters = super.getParameterMap();
        // 遍历原始参数映射的键集
        for (String key : parameters.keySet()) {
            // 获取当前键对应的参数值数组
            String[] values = parameters.get(key);
            // 遍历参数值数组，对每个元素进行 XSS 过滤
            for (int i = 0; i < values.length; i++) {
                values[i] = HtmlUtil.filter(values[i]);
            }
            // 将过滤后的参数值数组存入新的映射中
            map.put(key, values);
        }
        return map;
    }

    /**
     * 重写 getHeader 方法，对请求头名和请求头值进行 XSS 过滤
     *
     * @param name 请求头名
     * @return 经过 XSS 过滤后的请求头值
     */
    @Override
    public String getHeader(String name) {
        // 对请求头名进行 XSS 过滤
        String value = super.getHeader(HtmlUtil.filter(name));
        // 如果请求头值不为空，对其进行 XSS 过滤
        if (StrUtil.isAllNotBlank(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }
}