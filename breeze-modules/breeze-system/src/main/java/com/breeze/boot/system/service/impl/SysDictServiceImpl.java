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

package com.breeze.boot.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.mapper.SysDictMapper;
import com.breeze.boot.system.model.converter.SysDictConverter;
import com.breeze.boot.system.model.entity.SysDict;
import com.breeze.boot.system.model.entity.SysDictItem;
import com.breeze.boot.system.model.form.DictForm;
import com.breeze.boot.system.model.form.DictOpenForm;
import com.breeze.boot.system.model.query.DictQuery;
import com.breeze.boot.system.model.vo.DictVO;
import com.breeze.boot.system.service.SysDictItemService;
import com.breeze.boot.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统字典服务impl
 *
 * @author gaoweixuan
  * @since 2024-07-13
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    /**
     * 系统字典项服务
     */
    private final SysDictItemService sysDictItemService;

    private final SysDictConverter sysDictConverter;

    /**
     * 字典分页
     *
     * @param query 字典查询
     * @return {@link Page}<{@link DictVO}>
     */
    @Override
    public Page<DictVO> listPage(DictQuery query) {
        Page<SysDict> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysDict> sysDictPage = this.baseMapper.listPage(page, query);
        return this.sysDictConverter.entityPage2VOPage(sysDictPage);
    }

    /**
     * 按id获取信息
     *
     * @param dictId dict id
     * @return {@link DictVO }
     */
    @Override
    public DictVO getInfoById(Long dictId) {
        return this.sysDictConverter.entity2VO(this.getById(dictId));
    }

    /**
     * 保存dict
     *
     * @param form 字典表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveDict(DictForm form) {
        SysDict sysDict = this.sysDictConverter.form2Entity(form);
        return this.save(sysDict);
    }

    /**
     * 修改dict
     *
     * @param id       ID
     * @param form 字典表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyDict(Long id, DictForm form) {
        SysDict sysDict = this.sysDictConverter.form2Entity(form);
        sysDict.setId(id);
        return this.updateById(sysDict);
    }

    /**
     * 开关
     *
     * @param form 字典开关参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(DictOpenForm form) {
        return this.update(Wrappers.<SysDict>lambdaUpdate()
                .set(SysDict::getStatus, form.getStatus())
                .eq(SysDict::getId, form.getId()));
    }

    /**
     * 删除字典通过IDS
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteByIds(List<Long> ids) {
        this.sysDictItemService.remove(Wrappers.<SysDictItem>lambdaQuery().in(SysDictItem::getDictId, ids));
        this.removeByIds(ids);
        return Result.ok(Boolean.TRUE, "删除成功");
    }

}
