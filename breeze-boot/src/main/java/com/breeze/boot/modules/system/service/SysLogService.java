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

package com.breeze.boot.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.modules.system.model.entity.SysLog;
import com.breeze.boot.modules.system.model.query.LogQuery;
import com.breeze.boot.modules.system.model.vo.LogVO;
import com.breeze.boot.modules.system.model.vo.StatisticLoginUser;

/**
 * 系统日志服务
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 日志列表
     *
     * @param logQuery 日志查询
     * @param logType  日志类型
     * @return {@link Page}<{@link LogVO}>
     */
    Page<LogVO> listPage(LogQuery logQuery, Integer logType);

    /**
     * 按id获取信息
     *
     * @param logId 日志id
     * @return {@link LogVO }
     */
    LogVO getInfoById(Long logId);

    /**
     * 保存系统日志
     *
     * @param sysLogBO 系统日志BO
     */
    void saveSysLog(SysLogBO sysLogBO);

    /**
     * 清空
     */
    void truncate();

    /**
     * 首页统计本年用户登录数量
     *
     * @return {@link StatisticLoginUser }
     */
    StatisticLoginUser statisticLoginUserPie();

}

