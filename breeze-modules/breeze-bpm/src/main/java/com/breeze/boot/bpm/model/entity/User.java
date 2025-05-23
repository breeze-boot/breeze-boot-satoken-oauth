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

package com.breeze.boot.bpm.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 流程用户组
 *
 * @author gaoweixuan
 * @since 2024-08-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("ACT_ID_USER")
@Schema(description = "流程用户")
public class User implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "ID_", type = IdType.INPUT)
	private String id;

	@TableField(value = "REV_")
	private Integer rev;

	@TableField(value = "FIRST_")
	private String first;

	@TableField(value = "LAST_")
	private String last;

	@TableField(value = "DISPLAY_NAME_")
	private String displayName;

	@TableField(value = "EMAIL_")
	private String email;

	@TableField(value = "PWD_")
	private String pwd;

	@TableField(value = "PICTURE_ID_")
	private String pictureId;

	@TableField(value = "TENANT_ID_")
	private String tenantId;

}
