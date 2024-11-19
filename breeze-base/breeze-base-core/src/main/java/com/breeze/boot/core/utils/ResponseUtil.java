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

package com.breeze.boot.core.utils;

import com.breeze.boot.core.enums.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * response 返回JSON工具类
 *
 * @author gaoweixuan
 * @since 2021/10/2
 */
@Slf4j
public class ResponseUtil {

    /**
     * 响应
     *
     * @param response   响应
     * @param resultCode 结果
     */
    public static void response(HttpServletResponse response, ResultCode resultCode) {
        String message = MessageUtil.getMessage(resultCode.getKey());
        response(response, Result.fail(message));
    }

    /**
     * 响应
     *
     * @param response   响应
     * @param errorMsg 结果
     */
    public static void response(HttpServletResponse response, String errorMsg) {
        response(response, Result.fail(errorMsg));
    }

    private static void response(HttpServletResponse response, Result<Object> errorMsg) {
        ServletOutputStream out = null;
        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            out = response.getOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            out.write(objectMapper.writeValueAsString(errorMsg).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("response 响应失败", e);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("IO关闭异常", e);
            }
        }
    }

}
