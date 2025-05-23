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

package com.breeze.boot.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.auth.model.converter.SysPostConverter;
import com.breeze.boot.auth.model.form.PostForm;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.auth.mapper.SysPostMapper;
import com.breeze.boot.auth.model.entity.SysPost;
import com.breeze.boot.auth.model.query.PostQuery;
import com.breeze.boot.auth.model.vo.PostVO;
import com.breeze.boot.auth.service.SysPostService;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统岗服务impl
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final SysPostConverter sysPostConverter;

    /**
     * 列表页面
     *
     * @param query 岗位查询
     * @return {@link IPage}<{@link PostVO}>
     */
    @Override
    @DymicSql
    public IPage<PostVO> listPage(@ConditionParam PostQuery query) {
        Page<SysPost> page = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(query.getPostCode()), SysPost::getPostCode, query.getPostCode())
                .like(StrUtil.isAllNotBlank(query.getPostName()), SysPost::getPostName, query.getPostName())
                .orderByDesc(SysPost::getCreateTime)
                .page(new Page<>(query.getCurrent(), query.getSize()));
        return this.sysPostConverter.page2PageVO(page);
    }

    /**
     * 获取信息
     *
     * @param postId post-id
     * @return {@link PostVO }
     */
    @Override
    public PostVO getInfoById(Long postId) {
        SysPost sysPost = this.getById(postId);
        return this.sysPostConverter.entity2VO(sysPost);
    }

    /**
     * 保存岗位
     *
     * @param form 岗位表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean savePost(PostForm form) {
        return this.save(sysPostConverter.form2Entity(form));
    }

    /**
     * 修改岗位
     *
     * @param id       ID
     * @param form 岗位表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyPost(Long id, PostForm form) {
        SysPost sysPost = sysPostConverter.form2Entity(form);
        sysPost.setId(id);
        return this.updateById(sysPost);
    }

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPost() {
        return Result.ok(this.list().stream().map(post -> {
            Map< String,  Object> postMap = Maps.newHashMap();
            postMap.put("value", post.getId());
            postMap.put("label", post.getPostName());
            return postMap;
        }).collect(Collectors.toList()));
    }
}




