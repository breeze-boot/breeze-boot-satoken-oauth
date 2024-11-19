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

package com.breeze.boot.modules.system.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志
 *
 * @author gaoweixuan
 * @since 2022-10-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_log")
@Schema(description = "系统日志实体")
public class SysLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private Long id;

    /**
     * 系统模块
     */
    @Schema(description = "系统模块")
    private String systemModule;

    /**
     * 日志标题
     */
    @Schema(description = "日志标题")
    private String logTitle;

    /**
     * 日志类型 0 系统日志 1 登录日志
     */
    @Schema(description = "日志类型 0 系统日志 1 登录日志")
    private Integer logType;

    /**
     * 请求类型 GET POST PUT DELETE
     */
    @Schema(description = "请求类型 GET POST PUT DELETE")
    private String requestType;

    /**
     * IP
     */
    @Schema(description = "ip")
    private String ip;

    /**
     * 操作类型 0 添加 1 删除 2 修改 3 登录
     */
    @Schema(description = "操作类型 0 添加 1 删除 2 修改 3 登录 ")
    private Integer doType;

    /**
     * 系统
     */
    @Schema(description = "系统")
    @TableField("`system`")
    private String system;

    /**
     * 浏览器名称
     */
    @Schema(description = "浏览器名称")
    private String browser;

    /**
     * 入参
     */
    @Schema(description = "入参")
    private String paramContent;

    /**
     * 结果 0 失败 1 成功
     */
    @Schema(description = "结果 0 失败 1 成功")
    private Integer result;

    /**
     * 结果信息
     */
    @Schema(description = "结果信息")
    private String resultMsg;

    /**
     * 方法执行时间
     */
    @Schema(description = "方法执行时间")
    private String time;

    /**
     * 创建人
     */
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人编码", hidden = true)
    private String createBy;

    /**
     * 创建人姓名
     */
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人姓名", hidden = true)
    private String createName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    @Schema(hidden = true, description = "创建时间")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createTime;

}
