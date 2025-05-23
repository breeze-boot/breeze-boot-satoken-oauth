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

package com.breeze.boot.auth.model.bo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统部门BO
 *
 * @author gaoweixuan
 * @since 2024-07-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统部门BO")
public class SysDeptBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String deptCode;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 上级部门ID
     */
    @Schema(description = "上级部门ID")
    private Long parentId;

    /**
     * sys部门名单列表
     */
    @Schema(description = "下级部门")
    private List<SysDeptBO> subDeptList;

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

    /**
     * 修改人
     */
    @JsonIgnore
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改人编码", hidden = true)
    private String updateBy;

    /**
     * 修改人姓名
     */
    @JsonIgnore
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改人姓名", hidden = true)
    private String updateName;

    /**
     * 修改时间
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改时间", hidden = true)
    private LocalDateTime updateTime;
}
