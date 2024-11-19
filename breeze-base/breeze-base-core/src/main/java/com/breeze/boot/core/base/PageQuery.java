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

package com.breeze.boot.core.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 分页查询参数
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Setter
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
     * 排序sql
     * -- SETTER --
     * 设置分页大小
     */
    private String sortSql;

    /**
     * 排序
     * <p>
     * 按照MybatisPlus的方式，顺序排序
     * </p>
     */
    @Getter
    private LinkedHashMap<String, String> sort;

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
     * 获取排序的列
     *
     * @return {@link Integer}
     */
    public LinkedHashMap<String, List<String>> getColumns() {
        LinkedHashMap<String, List<String>> sortMap = Maps.newLinkedHashMap();
        if (CollUtil.isEmpty(this.sort)) {
            return sortMap;
        }

        this.sort.forEach((sort, column) -> {
            if (StrUtil.isNotBlank(column)) {
                String[] columns = column.split("\\|");
                sortMap.putIfAbsent(sort.equals("ascending") ? "asc" : "desc", Arrays.asList(columns));
            }
        });

        return sortMap;
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
        this.getColumns().forEach((sort, columnList) -> queryWrapper.orderBy(this.isSort(), StrUtil.equals("asc", sort), columnList));
    }

    /**
     * 构建排序SQL语句
     *
     * @return 排序SQL语句 {@link String}
     */
    public String getSortSql() {
        if (CollUtil.isEmpty(this.sort)) {
            return "";
        }

        StringBuilder sortSqlBuilder = new StringBuilder("order by ");
        this.sort.forEach((sort, column) -> sortSqlBuilder.append(column).append(" ").append(sort).append(", "));

        // 移除最后一个逗号和空格
        if (sortSqlBuilder.length() > 0) {
            sortSqlBuilder.setLength(sortSqlBuilder.length() - 2);
        }

        return sortSqlBuilder.toString();
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
