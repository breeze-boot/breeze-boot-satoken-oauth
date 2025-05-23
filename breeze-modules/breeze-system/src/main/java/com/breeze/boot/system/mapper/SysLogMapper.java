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

package com.breeze.boot.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.system.model.entity.SysLog;
import com.breeze.boot.system.model.query.LogQuery;
import com.breeze.boot.system.model.vo.StatisticLoginUser;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.mybatis.mapper.BreezeBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志映射器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Mapper
public interface SysLogMapper extends BreezeBaseMapper<SysLog> {

    /**
     * 清空
     */
    void truncate();

    /**
     * 列表页面
     *
     * @param page     页面
     * @param logQuery 日志查询
     * @param logType 日志类型
     * @return {@link Page}<{@link SysLog}>
     */
    @DymicSql
    Page<SysLog> listPage(Page<SysLog> page, @ConditionParam @Param("logQuery") LogQuery logQuery, @Param("logType") Integer logType);

    /**
     * 统计登录用户饼图
     *
     * @return {@link List }<{@link StatisticLoginUser.Series }>
     */
    List<StatisticLoginUser.Series> statisticLoginUserPie();

    /**
     * 统计登录用户饼图图例
     *
     * @return {@link List }<{@link String }>
     */
    List<String> statisticLoginUserPieLegend();

}
