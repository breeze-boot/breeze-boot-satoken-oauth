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

package com.breeze.boot.system.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户消息实体
 *
 * @author gaoweixuan
 * @since 2022-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_msg_user")
@Schema(description = "系统用户消息实体")
public class SysMsgUser extends Model<SysMsgUser> implements Serializable {

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
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long msgId;

    /**
     * 标记已读 0 未读 1 已读
     */
    @Schema(description = "标记已读 0 未读 1 已读")
    private Integer isRead;

    /**
     * 标记关闭 0 未关闭 1 已关闭
     */
    @Schema(description = "标记关闭 0 未关闭 1 已关闭")
    private Integer isClose;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    private Long userId;

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
     * <p>
     * JsonFormat 注解支持序列化和反序列化，反序列化只能用于Post请求的json提交[application/json]提交
     * <p>
     * DateTimeFormat 注解支持表单提交[application/x-www-form-urlencoded, multipart/form-data]或者Get请求的反序列化，若使用注解就不需要定义参数转换器
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    @Schema(hidden = true, description = "创建时间")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createTime;

}
