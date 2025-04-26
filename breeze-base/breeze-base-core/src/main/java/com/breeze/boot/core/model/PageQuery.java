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

package com.breeze.boot.core.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;

/**
 * 分页查询参数
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "分页查询参数", hidden = true)
public class PageQuery {

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 大小
     * -- SETTER --
     * 设置分页大小
     */
    private Integer size;

    /**
     * 排序
     * <p>
     * 按照MybatisPlus的方式，顺序排序
     * </p>
     */
    private LinkedHashMap<String, Object> sort;

    private Condition condition;

    /**
     * 页面查询
     */
    public PageQuery() {
    }

    /**
     * 获取当前页码
     *
     * @return {@link Integer}
     */
    public Integer getCurrent() {
        if (this.current == null) {
            return 1;
        }
        return current;
    }

    /**
     * 获取是否排序
     *
     * @return {@link Integer}
     */
    public Boolean isSort() {
        return CollUtil.isNotEmpty(this.sort);
    }

    /**
     * 组装排序
     *
     * @param queryWrapper 查询包装器
     */
    public void getSortQueryWrapper(QueryWrapper<?> queryWrapper) {
        if (CollUtil.isEmpty(this.sort)) {
            return;
        }
        this.sort.forEach((column, sort) -> {
            queryWrapper.orderBy(this.isSort(), StrUtil.equals("ascending", sort.toString()), column);
        });
    }

    /**
     * 获取当前页码
     *
     * @return {@link Integer}
     */
    @JsonIgnore
    public Integer getOffset() {
        return (getCurrent() - 1) * this.getSize();
    }

    /**
     * 获取分页大小
     *
     * @return {@link Integer}
     */
    public Integer getSize() {
        if (this.size == null) {
            return 10;
        }
        AssertUtil.isFalse(this.size > 1000, ResultCode.PAGE_EXCEPTION);
        return size;
    }

    /**
     * 获取分页大小
     *
     * @return {@link Integer}
     */
    @JsonIgnore
    public Integer getLimit() {
        return getSize();
    }

}
