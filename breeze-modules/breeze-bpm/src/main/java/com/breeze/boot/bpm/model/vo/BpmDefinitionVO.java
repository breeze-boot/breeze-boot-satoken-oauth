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

package com.breeze.boot.bpm.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程定义资源VO
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmDefinitionVO {

    /**
     * 流程部署id
     */
    private String id;

    /**
     * 流程定义Key
     */
    private String procDefKey;

    /**
     * 流程定义名字
     */
    private String procDefName;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类别编码
     */
    private String categoryCode;

    /**
     * 部署时间
     */
    private Date deploymentTime;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 暂停
     */
    private Integer suspended;

    /**
     * xml
     */
    private String xml;

    /**
     * 租户ID
     */
    private String tenantId;

    private String description;

}
