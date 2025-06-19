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

package com.breeze.boot.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.auth.model.entity.SysPost;
import com.breeze.boot.auth.model.form.PostForm;
import com.breeze.boot.auth.model.query.PostQuery;
import com.breeze.boot.auth.model.vo.PostVO;
import com.breeze.boot.core.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 系统岗位服务
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 列表页面
     *
     * @param query 岗位查询
     * @return {@link IPage}<{@link PostVO}>
     */
    IPage<PostVO> listPage(PostQuery query);

    /**
     * 获取信息
     *
     * @param postId post-id
     * @return {@link PostVO }
     */
    PostVO getInfoById(Long postId);

    /**
     * 保存岗位
     *
     * @param form 岗位表单
     * @return {@link Boolean }
     */
    Boolean savePost(PostForm form);

    /**
     * 修改岗位
     *
     * @param id       ID
     * @param form 岗位表单
     * @return {@link Boolean }
     */
    Boolean modifyPost(Long id, PostForm form);

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectPost();
}
