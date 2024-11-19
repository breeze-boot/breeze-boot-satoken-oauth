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

package com.breeze.boot.log.bo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志BO
 *
 * @author gaoweixuan
 * @since 2022-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLogBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统模块
     */
    private String systemModule;

    /**
     * 日志标题
     */
    private String logTitle;

    /**
     * 日志类型 0 系统日志 1 登录日志
     */
    private Integer logType;

    /**
     * 请求类型 GET POST PUT DELETE
     */
    private String requestType;

    /**
     * IP
     */
    private String ip;

    /**
     * 操作类型 0 添加 1 删除 2 修改 3 登录
     */
    private Integer doType;

    /**
     * 浏览器名称
     */
    private String browser;

    /**
     * 操作系统类型
     */
    private String system;

    /**
     * 入参
     */
    private String paramContent;

    /**
     * 结果 0 失败 1 成功
     */
    private Integer result;

    /**
     * 时间
     */
    private String time;

    /**
     * 执行结果
     */
    private String resultMsg;

    /**
     * 创建人
     */
    @ExcelIgnore
    @Schema(description = "创建人编码", hidden = true)
    private String createBy;

    /**
     * 创建人姓名
     */
    @ExcelIgnore
    @Schema(description = "创建人姓名", hidden = true)
    private String createName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(hidden = true, description = "创建时间")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createTime;

}
