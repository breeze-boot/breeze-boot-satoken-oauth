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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.model.entity.SysDbResource;
import com.breeze.boot.system.model.form.DbResourceForm;
import com.breeze.boot.system.model.query.DbResourceQuery;
import com.breeze.boot.system.model.vo.DbResourceVO;

import java.util.List;

/**
 * 系统数据源服务
 *
 * @author gaoweixuan
 * @since 2024-01-15
 */
public interface SysDbResourceService extends IService<SysDbResource> {

    /**
     * 分页
     *
     * @param query 数据源查询参数
     * @return {@link Page}<{@link DbResourceVO}>
     */
    Page<DbResourceVO> listPage(DbResourceQuery query);

    /**
     * 按id获取数据库资源
     *
     * @param id ID
     * @return {@link DbResourceVO }
     */
    DbResourceVO getDbResourceById(Long id);

    /**
     * 添加数据源
     *
     * @param form 数据源
     * @return {@link Boolean}
     */
    Boolean saveDbResource(DbResourceForm form);

    /**
     * 更新数据源
     *
     * @param id   ID
     * @param form db表格
     * @return {@link Boolean}
     */
    Boolean modifyDbResource(Long id, DbResourceForm form);

    /**
     * 删除数据源
     *
     * @param ids 数据源Ids
     * @return {@link Boolean}
     */
    Boolean removeDbResourceByIds(List<Long> ids);

}
