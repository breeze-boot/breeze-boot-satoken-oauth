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

package com.breeze.boot.gen.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.breeze.boot.gen.service.DevCodeGenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gen/v1")
public class DevCodeGenController {

    private final DevCodeGenService devCodeGenService;

    @GetMapping("/generate-code")
    @SaCheckPermission("gen:dev:code")
    public void generateCode(
            @RequestParam List<String> tableName,
            @RequestParam String packageName,
            @RequestParam String moduleName,
            HttpServletResponse response) {
        // 生成代码压缩包
        byte[] zipBytes = devCodeGenService.generateCodeZip(tableName, packageName, moduleName);

        // 设置响应头
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated_code.zip");

        // 将文件流写入响应
        try (OutputStream os = response.getOutputStream()) {
            os.write(zipBytes);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }

}