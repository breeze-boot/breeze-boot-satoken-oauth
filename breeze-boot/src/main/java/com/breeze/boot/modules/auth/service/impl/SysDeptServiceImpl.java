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

package com.breeze.boot.modules.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysDeptMapper;
import com.breeze.boot.modules.auth.model.bo.SysDeptBO;
import com.breeze.boot.modules.auth.model.entity.SysDept;
import com.breeze.boot.modules.auth.model.form.DeptForm;
import com.breeze.boot.modules.auth.model.mappers.SysDeptMapStruct;
import com.breeze.boot.modules.auth.model.query.DeptQuery;
import com.breeze.boot.modules.auth.service.SysDeptService;
import com.google.common.collect.Maps;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CoreConstants.ROOT;
import static com.breeze.boot.core.enums.ResultCode.IS_USED;

/**
 * 系统部门服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapStruct sysDeptMapStruct;

    /**
     * 部门列表
     *
     * @param deptQuery 部门查询
     * @return {@link List}<{@link Tree}<{@link Long}>>
     */
    @Override
    public List<?> listDept(DeptQuery deptQuery) {
        List<SysDept> deptEntityList = this.list(Wrappers.<SysDept>lambdaQuery()
                .like(Objects.nonNull(deptQuery) && StrUtil.isAllNotBlank(deptQuery.getDeptName()),
                        SysDept::getDeptName, deptQuery.getDeptName()));
        if (StrUtil.isAllNotBlank(deptQuery.getDeptName())) {
            return deptEntityList;
        }
        List<TreeNode<Long>> treeNodeList = deptEntityList.stream().map(
                sysDept -> {
                    TreeNode<Long> treeNode = new TreeNode<>();
                    treeNode.setId(sysDept.getId());
                    treeNode.setParentId(sysDept.getParentId());
                    treeNode.setName(sysDept.getDeptName());
                    Map<String, Object> leafMap = Maps.newHashMap();
                    leafMap.put("deptName", sysDept.getDeptName());
                    leafMap.put("deptCode", sysDept.getDeptCode());
                    if (Objects.equals(deptQuery.getId(), sysDept.getId())) {
                        leafMap.put("disabled", Boolean.TRUE);
                    }
                    leafMap.put("value", sysDept.getId());
                    leafMap.put("label", sysDept.getDeptName());
                    treeNode.setExtra(leafMap);
                    return treeNode;
                }
        ).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, ROOT);
    }

    @Override
    public Boolean saveDept(DeptForm deptForm) {
        SysDept sysDept = sysDeptMapStruct.form2Entity(deptForm);
        return this.save(sysDept);
    }

    @Override
    public Boolean modifyDept(@Valid Long id, DeptForm deptForm) {
        SysDept sysDept = sysDeptMapStruct.form2Entity(deptForm);
        sysDept.setId(id);
        return this.updateById(sysDept);
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteById(Long id) {
        List<SysDept> deptEntityList = this.list(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getParentId, id));
        AssertUtil.isTrue(CollUtil.isEmpty(deptEntityList), IS_USED);
        boolean remove = this.removeById(id);
        AssertUtil.isTrue(remove, ResultCode.FAIL);
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    @Override
    public List<Long> listDeptByParentId(Long deptId) {
        List<SysDeptBO> deptBOList = this.baseMapper.selectDeptById(deptId);
        if(CollUtil.isEmpty(deptBOList)){
            return Lists.newArrayList();
        }
        return this.findPropertyInTree(deptBOList.get(0));
    }

    @Override
    public List<SysDeptBO> listSubDeptId(Long deptId) {
        return this.baseMapper.selectDeptById(deptId);
    }

    public List<Long> findPropertyInTree(SysDeptBO node) {
        List<Long> result = new ArrayList<>();
        if (node != null) {
            result.add(node.getId()); // 将当前节点的属性值添加到结果中
            if (node.getSubDeptList() != null) {
                for (SysDeptBO child : node.getSubDeptList()) {
                    // 递归查询子节点的属性值并添加到结果中
                    result.addAll(findPropertyInTree(child));
                }
            }
        }
        return result;
    }

}
