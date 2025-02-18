
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
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.base.CustomizePermission;
import com.breeze.boot.core.enums.DataPermissionType;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.BreezeTenantHolder;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysRowPermissionMapper;
import com.breeze.boot.modules.auth.model.entity.SysRoleRowPermission;
import com.breeze.boot.modules.auth.model.entity.SysRowPermission;
import com.breeze.boot.modules.auth.model.entity.SysTenant;
import com.breeze.boot.modules.auth.model.form.RowPermissionForm;
import com.breeze.boot.modules.auth.model.mappers.SysRowPermissionMapStruct;
import com.breeze.boot.modules.auth.model.query.RowPermissionQuery;
import com.breeze.boot.modules.auth.model.vo.RowPermissionVO;
import com.breeze.boot.modules.auth.service.SysRoleRowPermissionService;
import com.breeze.boot.modules.auth.service.SysRowPermissionService;
import com.breeze.boot.modules.auth.service.SysTenantService;
import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CacheConstants.ROW_PERMISSION;
import static com.breeze.boot.core.enums.ResultCode.IS_USED;

/**
 * 系统行级数据权限服务 impl
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Service
@RequiredArgsConstructor
public class SysRowPermissionServiceImpl extends ServiceImpl<SysRowPermissionMapper, SysRowPermission> implements SysRowPermissionService {

    /**
     * 系统角色数据权限服务
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    private final SysRowPermissionMapStruct sysRowPermissionMapStruct;

    private final SysTenantService sysTenantService;

    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    @PostConstruct
    public void init() {
        List<SysTenant> sysTenantList = sysTenantService.list();

        sysTenantList.forEach(sysTenant -> {
            BreezeTenantHolder.setTenant(sysTenant.getId());
            try {
                List<SysRowPermission> sysRowPermissionList = this.list();
                // 使用批量处理优化
                List<CustomizePermission> customizePermissionList = new ArrayList<>();
                sysRowPermissionList.forEach(rowPermission -> {
                    CustomizePermission customizePermission = sysRowPermissionMapStruct.entity2Cache(rowPermission);
                    String permissionsString = rowPermission.getPermissions().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(","));
                    customizePermission.setPermissions(permissionsString);
                    customizePermissionList.add(customizePermission);
                });

                // 批量添加到缓存中
                customizePermissionList.forEach(customizePermission -> {
                    String permissionCode = customizePermission.getPermissionCode();
                    this.redisTemplate.opsForValue().set(ROW_PERMISSION + permissionCode, customizePermission);
                });
            } catch (Exception e) {
                // 增加异常处理逻辑，记录日志或进行其他处理
                log.error("Error processing tenant: " + sysTenant.getId(), e);
            } finally {
                BreezeTenantHolder.clean();
            }
        });
    }

    /**
     * 列表页面
     *
     * @param rowPermissionQuery 权限查询
     * @return {@link Page}<{@link RowPermissionVO}>
     */
    @Override
    public Page<RowPermissionVO> listPage(RowPermissionQuery rowPermissionQuery) {
        Page<SysRowPermission> page = new Page<>(rowPermissionQuery.getCurrent(), rowPermissionQuery.getSize());
        Page<SysRowPermission> rowPermissionPage = this.baseMapper.listPage(page, rowPermissionQuery);
        return this.sysRowPermissionMapStruct.page2VOPage(rowPermissionPage);
    }

    /**
     * 按id获取信息
     *
     * @param permissionId 权限id
     * @return {@link RowPermissionVO }
     */
    @Override
    public RowPermissionVO getInfoById(Long permissionId) {
        SysRowPermission sysRowPermission = this.getById(permissionId);
        CustomizePermission customizePermission = sysRowPermissionMapStruct.entity2Cache(sysRowPermission);
        String permissionsString = sysRowPermission.getPermissions().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        customizePermission.setPermissions(permissionsString);

        // 批量添加到缓存中
        String permissionCode = customizePermission.getPermissionCode();
        this.redisTemplate.opsForValue().set(ROW_PERMISSION + permissionCode, customizePermission);
        return this.sysRowPermissionMapStruct.entity2VO(sysRowPermission);
    }

    /**
     * 保存数据权限
     *
     * @param rowPermissionForm 行权限表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveRowPermission(RowPermissionForm rowPermissionForm) {
        SysRowPermission sysRowPermission = sysRowPermissionMapStruct.form2Entity(rowPermissionForm);
        AssertUtil.isFalse(DataPermissionType.checkInEnum(rowPermissionForm.getPermissionCode()), ResultCode.NO_ACTION_IS_ALLOWED);
        boolean save = this.save(sysRowPermission);
        AssertUtil.isTrue(save, ResultCode.FAIL);
        CustomizePermission customizePermission = sysRowPermissionMapStruct.entity2Cache(sysRowPermission);
        String permissionsString = sysRowPermission.getPermissions().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        customizePermission.setPermissions(permissionsString);

        // 批量添加到缓存中
        String permissionCode = customizePermission.getPermissionCode();
        this.redisTemplate.opsForValue().set(ROW_PERMISSION + permissionCode, customizePermission);
        return Result.ok();
    }

    /**
     * 修改权限
     *
     * @param id                id
     * @param rowPermissionForm 行权限表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    public Result<Boolean> modifyRowPermission(Long id, RowPermissionForm rowPermissionForm) {
        SysRowPermission sysRowPermission = sysRowPermissionMapStruct.form2Entity(rowPermissionForm);
        sysRowPermission.setId(id);
        AssertUtil.isFalse(DataPermissionType.checkInEnum(rowPermissionForm.getPermissionCode()), ResultCode.NO_ACTION_IS_ALLOWED);
        boolean update = sysRowPermission.updateById();
        AssertUtil.isTrue(update, ResultCode.FAIL);
        CustomizePermission customizePermission = sysRowPermissionMapStruct.entity2Cache(sysRowPermission);
        String permissionsString = sysRowPermission.getPermissions().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        customizePermission.setPermissions(permissionsString);

        // 批量添加到缓存中
        String permissionCode = customizePermission.getPermissionCode();
        this.redisTemplate.opsForValue().set(ROW_PERMISSION + permissionCode, customizePermission);
        return Result.ok();
    }

    /**
     * 删除数据权限通过IDs
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeRowPermissionByIds(List<Long> ids) {
        List<SysRoleRowPermission> rolePermissionList = this.sysRoleRowPermissionService.list(Wrappers.<SysRoleRowPermission>lambdaQuery().in(SysRoleRowPermission::getPermissionId, ids));
        AssertUtil.isTrue(CollUtil.isEmpty(rolePermissionList), IS_USED);
        List<SysRowPermission> rowPermissionList = this.listByIds(ids);
        for (SysRowPermission rowPermission : rowPermissionList) {
            this.redisTemplate.delete(ROW_PERMISSION + rowPermission.getPermissionCode());
        }

        return Result.ok(this.removeBatchByIds(ids));
    }

    /**
     * 数据权限类型下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectPermissionType() {
        return Result.ok(Arrays.stream(DataPermissionType.values()).map(permission -> {
            Map<String, Object> permissionMap = Maps.newHashMap();
            permissionMap.put("value", permission.getType());
            permissionMap.put("label", permission.getDesc());
            if (StrUtil.equals(DataPermissionType.CUSTOMIZES.getType(), permission.getType())) {
                permissionMap.put("flag", Boolean.TRUE);
            }
            return permissionMap;
        }).collect(Collectors.toList()));
    }


    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Override
    public Result<List<Map<String, Object>>> selectCustomizePermission() {
        return Result.ok(this.list().stream().map(permission -> {
            Map<String, Object> customizePermissionMap = Maps.newHashMap();
            customizePermissionMap.put("value", permission.getId());
            customizePermissionMap.put("label", permission.getPermissionName());
            return customizePermissionMap;
        }).collect(Collectors.toList()));
    }
}
