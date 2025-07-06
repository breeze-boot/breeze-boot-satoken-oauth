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

package com.breeze.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 行政区信息
 *
 * @author gaoweixuan
 * @since 2025/06/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_district")
@EqualsAndHashCode(callSuper = true)
public class SysDistrict extends Model<SysDistrict> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 行政区名称
     */
    private String name;

    /**
     * 行政区边界坐标点
     */
    private String polyline;

    /**
     * 区域中心点
     */
    private String center;

    /**
     * 行政区划级别
     * <p>
     * country:国家  province:省份（直辖市会在province和city显示） city:市（直辖市会在province和city显示） district:区县 street:街道
     * <p>
     */
    private String level;

}
