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

package com.breeze.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.system.mapper.SysDistrictMapper;
import com.breeze.boot.system.model.entity.SysDistrict;
import com.breeze.boot.system.service.SysDistrictService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 省份区域信息表 服务实现类
 */
@Service
public class SysDistrictServiceImpl extends ServiceImpl<SysDistrictMapper, SysDistrict> implements SysDistrictService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 缓存过期时间（1天）
    private static final long CACHE_EXPIRE_TIME = 24;
    private static final TimeUnit CACHE_EXPIRE_UNIT = TimeUnit.HOURS;

    // 缓存键前缀
    private static final String CACHE_KEY_PREFIX = "district:";

    @Override
    public List getProvince() {
        return buildTreeByLevel("province");
    }

    @Override
    public List<SysDistrict> getDistrictItem(String parentId, String level) {
        String cacheKey = CACHE_KEY_PREFIX + level + ":" + parentId;

        // 从Redis获取
        String districtJson = stringRedisTemplate.opsForValue().get(cacheKey);

        if (districtJson != null) {
            return JSON.parseArray(districtJson, SysDistrict.class);
        }

        // Redis中没有，则从数据库加载
        List<SysDistrict> districtList = loadDistrictFromDb(level, parentId);

        // 缓存到Redis
        if (CollUtil.isNotEmpty(districtList)) {
            stringRedisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(districtList), CACHE_EXPIRE_TIME, CACHE_EXPIRE_UNIT);
        }

        return districtList;
    }

    public List<Tree<String>> getDistrictTree(String parentId, String level) {
        List<SysDistrict> districts = getDistrictItem(parentId, level);
        return buildTree(districts);
    }

    private List buildTreeByLevel(String level) {
        String cacheKey = CACHE_KEY_PREFIX + "tree:" + level;

        // 从Redis获取树形结构
        String treeJson = stringRedisTemplate.opsForValue().get(cacheKey);

        if (treeJson != null) {
            return JSON.parseObject(treeJson, List.class);
        }

        // 构建树形结构
        List<SysDistrict> allDistricts = list(Wrappers.<SysDistrict>lambdaQuery().eq(SysDistrict::getLevel, level));
        List<Tree<String>> treeList = buildTree(allDistricts);

        // 缓存树形结构
        if (CollUtil.isNotEmpty(treeList)) {
            stringRedisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(treeList), CACHE_EXPIRE_TIME, CACHE_EXPIRE_UNIT);
        }

        return treeList;
    }

    private List<Tree<String>> buildTree(List<SysDistrict> districts) {
        if (CollUtil.isEmpty(districts)) {
            return Collections.emptyList();
        }

        // 构建树节点列表
        List<TreeNode<String>> nodeList = districts.stream()
                .map(district -> {
                    TreeNode<String> node = new TreeNode<>();
                    node.setId(district.getId().toString());
                    node.setParentId(district.getParentId().toString());
                    node.setName(district.getName());

                    // 扩展属性
                    Map<String, Object> extra = new HashMap<>();
                    extra.put("level", district.getLevel());
                    node.setExtra(extra);

                    return node;
                })
                .collect(Collectors.toList());

        // 构建树形结构
        return TreeUtil.build(nodeList, "0");
    }

    private List<SysDistrict> loadDistrictFromDb(String level, String parentId) {
        return list(Wrappers.<SysDistrict>lambdaQuery()
                .eq(SysDistrict::getLevel, level)
                .eq(SysDistrict::getParentId, parentId));
    }

    @PostConstruct
    public void initTree() {
        // 初始化缓存
        buildTreeByLevel("province");
        buildTreeByLevel("city");
        buildTreeByLevel("district");
    }
}