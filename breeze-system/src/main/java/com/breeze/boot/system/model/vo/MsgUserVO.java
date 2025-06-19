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

package com.breeze.boot.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户消息VO
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户消息VO")
public class MsgUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息标题
     */
    private String code;

    /**
     * 消息类型 0 通知 1 公告
     */
    private Integer type;

    /**
     * 消息级别 error 紧急消息 info 一般消息 warning 警示消消息 success 正常消息
     */
    private String level;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 关闭
     */
    private Integer isClose;

    /**
     * 已读
     */
    private Integer isRead;

    /**
     * 消息接收人ID
     */
    private Long userId;

    /**
     * 接收人用户名
     */
    private String username;

    /**
     * 消息接收时间
     */
    private LocalDateTime createTime;
}
