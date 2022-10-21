/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志dto
 *
 * @author breeze
 * @date 2022-09-02
 */
@Data
public class LogDTO extends PageDTO {
    /**
     * 系统模块
     */
    private String systemModule;
    /**
     * 日志标题
     */
    private String logTitle;
    /**
     * 日志类型 0 普通日志 1 登录日志
     */
    private Long logType;
    /**
     * 请求类型 0 GET 1 POST 2 PUT 3 DELETE
     */
    private Long requestType;
    /**
     * 操作类型 0 添加 1 删除 2 修改 3 查询
     */
    private Long doType;
    /**
     * 结果 0 失败 1 成功
     */
    private Long result;

    /**
     * 开始日期
     */
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    private LocalDateTime endDate;
}
