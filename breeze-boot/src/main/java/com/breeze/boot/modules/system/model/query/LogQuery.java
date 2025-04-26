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

package com.breeze.boot.modules.system.model.query;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.breeze.boot.core.model.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 日志查询参数
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "日志查询参数")
public class LogQuery extends PageQuery {

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
    private Long logType;

    /**
     * 请求类型 GET POST PUT DELETE
     */
    @Schema(description = "请求类型 GET POST PUT DELETE")
    private Long requestType;

    /**
     * 操作类型 0 添加 1 删除 2 修改 3 登录
     */
    @Schema(description = "操作类型 0 添加 1 删除 2 修改 3 登录 ")
    private Long doType;

    /**
     * 结果 0 失败 1 成功
     */
    @Schema(description = " 结果 0 失败 1 成功")
    private Long result;

    /**
     * 开始日期
     */
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Schema(description = "开始日期")
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Schema(description = "结束日期")
    private LocalDateTime endDate;

}
