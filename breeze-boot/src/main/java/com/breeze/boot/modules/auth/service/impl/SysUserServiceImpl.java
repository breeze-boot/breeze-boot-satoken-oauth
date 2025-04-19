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

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.enums.DataPermissionType;
import com.breeze.boot.manager.FlowableManager;
import com.breeze.boot.satoken.propertise.AesSecretProperties;
import com.breeze.boot.core.utils.AesUtil;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.EasyExcelExport;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.mapper.SysUserMapper;
import com.breeze.boot.modules.auth.model.bo.FlowUserBO;
import com.breeze.boot.modules.auth.model.bo.SysDeptBO;
import com.breeze.boot.modules.auth.model.bo.UserBO;
import com.breeze.boot.modules.auth.model.bo.UserRoleBO;
import com.breeze.boot.modules.auth.model.entity.*;
import com.breeze.boot.modules.auth.model.excel.UserExcel;
import com.breeze.boot.modules.auth.model.form.UserForm;
import com.breeze.boot.modules.auth.model.form.UserOpenForm;
import com.breeze.boot.modules.auth.model.form.UserResetForm;
import com.breeze.boot.modules.auth.model.form.UserRolesForm;
import com.breeze.boot.modules.auth.model.converter.SysUserConverter;
import com.breeze.boot.modules.auth.model.query.UserQuery;
import com.breeze.boot.modules.auth.model.vo.UserVO;
import com.breeze.boot.modules.auth.service.*;
import com.breeze.boot.modules.system.service.SysFileService;
import com.breeze.boot.satoken.model.UserInfoDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

import static com.breeze.boot.core.constants.CacheConstants.ROLE_PERMISSION;
import static com.breeze.boot.core.enums.ResultCode.*;

/**
 * 系统用户服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserConverter sysUserConverter;

    private final FlowableManager flowableManager;

    /**
     * 系统角色服务
     */
    private final SysRoleService sysRoleService;

    /**
     * 系统用户角色服务
     */
    private final SysUserRoleService sysUserRoleService;

    /**
     * 系统文件服务
     */
    private final SysFileService sysFileService;

    /**
     * 系统部门服务
     */
    private final SysDeptService sysDeptService;

    /**
     * 系统岗位服务
     */
    private final SysPostService sysPostService;

    /**
     * 密码编码器
     */
    private final SysMenuService sysMenuService;

    /**
     * 系统角色行数据权限服务
     */
    private final SysRoleRowPermissionService sysRoleRowPermissionService;

    /**
     * 列表页面
     *
     * @param query 用户查询
     * @return {@link Page}<{@link UserVO}>
     */
    @Override
    public Page<UserVO> listPage(UserQuery query) {
        Page<SysUser> page = new Page<>(query.getCurrent(), query.getSize());
        Page<UserBO> userBOPage = this.baseMapper.listPage(page, query);
        return this.sysUserConverter.pageBO2PageVO(userBOPage);
    }

    /**
     * 通过ID查询用户
     *
     * @param id id
     * @return {@link UserVO }
     */
    @Override
    public UserVO getInfoById(Long id) {
        SysUser sysUser = this.getById(id);
        UserVO userVO = this.sysUserConverter.entity2VO(sysUser);
        AssertUtil.isNotNull(sysUser, USER_NOT_FOUND);
        List<SysRole> roleList = this.sysUserRoleService.getSysRoleByUserId(sysUser.getId());
        userVO.setRoleNames(roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList()));
        userVO.setRoleIds(roleList.stream().map(SysRole::getId).collect(Collectors.toList()));
        userVO.setDeptName(Optional.ofNullable(this.sysDeptService.getById(userVO.getDeptId())).orElseGet(SysDept::new).getDeptName());
        userVO.setPostName(Optional.ofNullable(this.sysPostService.getById(sysUser.getPostId())).orElseGet(SysPost::new).getPostName());
        if (StrUtil.isBlank(sysUser.getAvatar())) {
            userVO.setAvatar(this.sysFileService.preview(sysUser.getAvatarFileId()));
        }
        userVO.setSysRoles(roleList);
        return userVO;
    }

    /**
     * 保存用户
     *
     * @param form 系统用户
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> saveUser(UserForm form) {
        AssertUtil.isNotNull(this.sysDeptService.getById(form.getDeptId()), DEPT_NOT_FOUND);
        form.setPassword(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
        SysUser sysUser = sysUserConverter.form2Entity(form);
        boolean save = this.save(sysUser);
        AssertUtil.isTrue(save, FAIL);
        return Result.ok(this.saveUserRole(form, sysUser.getId()));
    }

    /**
     * 通过Id更新用户
     *
     * @param userForm 用户表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean modifyUser(Long id, UserForm userForm) {
        SysUser sysUser = sysUserConverter.form2Entity(userForm);
        sysUser.setId(id);
        sysUser.setPassword(null);
        boolean update = this.updateById(sysUser);
        if (update) {
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
            this.saveUserRole(userForm, id);
        }
        return update;
    }

    /**
     * 保存用户角色
     *
     * @param userForm 用户表单
     * @param id       userId
     * @return boolean
     */
    private boolean saveUserRole(UserForm userForm, Long id) {
        List<SysUserRole> userRoleList = Optional.ofNullable(userForm.getRoleIds()).orElseGet(Lists::newArrayList).stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(id);
            sysUserRole.setRoleId(roleId);
            return sysUserRole;
        }).collect(Collectors.toList());
        boolean saveBatch = this.sysUserRoleService.saveBatch(userRoleList);
        if (saveBatch) {
            @SuppressWarnings("unchecked") List<String> userRoleCodeList = (List<String>) SaManager.getSaTokenDao().getObject(ROLE_PERMISSION + id);
            if (userRoleCodeList != null) {
                List<UserRoleBO> userRoleBOList = Optional.ofNullable(sysRoleService.listRoleByUserId(id))
                        .orElse(Collections.emptyList());
                userRoleCodeList = userRoleBOList.stream().map(UserRoleBO::getRoleCode).collect(Collectors.toList());
                SaManager.getSaTokenDao().updateObject(ROLE_PERMISSION + id, userRoleCodeList);
            }
        }
        return saveBatch;
    }

    /**
     * 开启关闭锁定
     *
     * @param form 用户打开表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean open(UserOpenForm form) {
        return this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getIsLock, form.getIsLock())
                .eq(SysUser::getUsername, form.getUsername()));
    }

    /**
     * 重置密码
     *
     * @param form 用户重置密码表单
     * @return {@link Boolean}
     */
    @Override
    public Boolean reset(UserResetForm form) {
        AesSecretProperties aesSecretProperties = SpringUtil.getBean(AesSecretProperties.class);
        form.setPassword(BCrypt.hashpw(AesUtil.decryptStr(form.getPassword(), aesSecretProperties.getAesSecret()), BCrypt.gensalt()));
        return this.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getPassword, form.getPassword()).eq(SysUser::getId, form.getId()));
    }

    /**
     * 删除用户
     *
     * @param ids 用户
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> removeUser(List<Long> ids) {
        List<SysUser> sysUserList = this.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, ids));
        AssertUtil.isTrue(CollUtil.isNotEmpty(sysUserList), NOT_FOUND);
        for (SysUser sysUser : sysUserList) {
            this.removeUser(sysUser);
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }

    public void removeUser(SysUser sysUser) {
        boolean remove = this.remove(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, sysUser.getId()));
        if (remove) {
            // 删除用户角色关系
            this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, sysUser.getId()));
        }
    }

    /**
     * 用户添加角色
     *
     * @param form 用户角色表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> setRole(UserRolesForm form) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, form.getUserId()));
        AssertUtil.isNotNull(sysUser, USER_NOT_FOUND);
        this.sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));
        List<SysUserRole> collect = form.getRoleIds().stream()
                .map(roleId -> SysUserRole.builder().roleId(roleId).userId(sysUser.getId()).build())
                .collect(Collectors.toList());
        this.sysUserRoleService.saveBatch(collect);
        return Result.ok(Boolean.TRUE, "分配成功");
    }

    /**
     * 注册用户
     *
     * @param registerUser 注册用户
     * @param roleCode     角色编码
     * @return {@link SysUser}
     */
    @Override
    public SysUser registerUser(SysUser registerUser, String roleCode) {
        SysUser sysUser = SysUser.builder().username(registerUser.getUsername()).displayName(registerUser.getUsername()).password(("123456")).openId(registerUser.getOpenId()).phone(registerUser.getPhone()).tenantId(registerUser.getTenantId()).deptId(1L).build();
        this.save(sysUser);
        // 给用户赋予一个临时角色，临时角色指定接口的权限
        SysRole sysRole = this.sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, roleCode));
        AssertUtil.isNotNull(sysRole, ROLE_NOT_FOUND);
        this.sysUserRoleService.save(SysUserRole.builder().userId(sysUser.getId()).roleId(sysRole.getId()).build());
        return sysUser;
    }

    /**
     * 查询用户通过部门id
     *
     * @param deptIds 部门IDS
     * @return {@link List}<{@link SysUser}>
     */
    @Override
    public List<UserVO> listUserByDeptId(List<Long> deptIds) {
        List<UserBO> userBOList = this.baseMapper.listUserByDeptId(deptIds);
        return this.sysUserConverter.boList2VOList(userBOList);
    }

    /**
     * 导出
     *
     * @param userQuery 用户查询
     * @param response  响应
     */
    @Override
    public void export(UserQuery userQuery, HttpServletResponse response) {
        Page<UserVO> userList = this.listPage(userQuery);
        List<UserExcel> userExcels = this.sysUserConverter.vo2Excel(userList.getRecords());
        try {
            EasyExcelExport.export(response, "用户数据", "用户数据", userExcels, UserExcel.class);
        } catch (Exception e) {
            log.error("导出用户数据失败", e);
        }
    }

    /**
     * 查询部门用户
     *
     * @param deptId 部门ID
     * @return {@link List}<{@link SysUser}>
     */
    @Override
    public List<SysUser> listDeptsUser(Long deptId) {
        List<Long> deptIdList = this.sysDeptService.listDeptByParentId(deptId);
        AssertUtil.isTrue(CollUtil.isNotEmpty(deptIdList), FAIL);
        if (CollUtil.isNotEmpty(deptIdList)) {
            return this.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, deptIdList));
        }
        return Lists.newArrayList();
    }

    /**
     * 获取用户通过角色
     *
     * @param roleCode 角色编码
     * @return {@link List }<{@link SysUser }>
     */
    @Override
    public List<SysUser> listUserByRole(String roleCode) {
        return this.baseMapper.listUserByRole(roleCode);
    }

    /**
     * 同步审批流用户
     */
    @Override
    public void syncFlowableUser() {
        List<SysUser> sysUserList = this.list();
        List<SysRole> roles = this.sysRoleService.list();
        List<FlowUserBO> syncUser = sysUserList.stream().map(item -> {
            FlowUserBO flowUserBO = FlowUserBO.builder()
                    .userId(item.getId())
                    .username(item.getUsername())
                    .displayName(item.getDisplayName())
                    .email(item.getEmail())
                    .build();
            List<SysRole> userRoleList = this.sysUserRoleService.getSysRoleByUserId(item.getId());
            if (CollUtil.isNotEmpty(userRoleList)) {
                List<SysRole> sysRoleList = this.sysRoleService.listByIds(userRoleList.stream().map(SysRole::getId).collect(Collectors.toList()));
                flowUserBO.setRoleList(sysRoleList);
            }
            return flowUserBO;
        }).collect(Collectors.toList());
        this.flowableManager.syncUser(syncUser, roles);
    }

    /**
     * 用户列表
     *
     * @param deptId 部门ID
     * @return {@link Result }<{@link List }<{@link SysUser }>>
     */
    @Override
    public Result<List<SysUser>> listDeptUser(Long deptId) {
        return Result.ok(this.list(Wrappers.<SysUser>lambdaQuery()
                .eq(Objects.nonNull(deptId), SysUser::getDeptId, deptId)));
    }

    /**
     * 加载登录用户
     *
     * @param sysUser 系统用户实体
     * @return {@link UserInfoDTO}
     */
    public UserInfoDTO buildLoginUserInfo(SysUser sysUser) {
        UserInfoDTO userInfo = sysUserConverter.entity2BaseLoginUser(sysUser);

        try {
            // 查询用户的角色
            List<UserRoleBO> userRoleBOList = Optional.ofNullable(sysRoleService.listRoleByUserId(sysUser.getId()))
                    .orElse(Collections.emptyList());
            AssertUtil.isTrue(CollUtil.isNotEmpty(userRoleBOList), USERS_ROLE_IS_NULL);
            // 获取部门名称
            this.setDeptName(sysUser, userInfo);
            // 获取子级部门
            this.setSubDeptId(sysUser, userInfo);
            // 权限
            this.setAuthorities(userRoleBOList, userInfo);
            // 角色CODE
            this.setRoleCode(userRoleBOList, userInfo);
            // 用户的角色ID
            this.setUsersRoleId(userRoleBOList, userInfo);
            // 用户的角色的行数据权限
            this.setRowPermission(userRoleBOList, userInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userInfo;
    }

    private static void traverseTree(SysDeptBO node, Set<Long> ids) {
        if (Objects.isNull(node)) {
            return;
        }
        // 添加当前节点的 ID 到列表
        ids.add(node.getId());
        // 递归遍历所有子节点
        for (SysDeptBO child : node.getSubDeptList()) {
            traverseTree(child, ids);
        }
    }

    // 递归遍历树形结构并获取所有节点的 ID
    public static Set<Long> getAllNodeIds(SysDeptBO root) {
        Set<Long> ids = Sets.newHashSet();
        traverseTree(root, ids);
        return ids;
    }

    private void setSubDeptId(SysUser sysUser, UserInfoDTO userInfo) {
        Long deptId = sysUser.getDeptId();
        if (Objects.isNull(deptId)) {
            return;
        }
        List<SysDeptBO> sysDeptList = this.sysDeptService.listSubDeptId(deptId);
        if (CollUtil.isEmpty(sysDeptList)) {
            return;
        }
        userInfo.setSubDeptId(getAllNodeIds(sysDeptList.get(0)));
    }

    private void setAuthorities(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        userInfo.setAuthorities(this.sysMenuService.listUserMenuPermission(userRoleBOList));
    }

    private void setDeptName(SysUser sysUser, UserInfoDTO userInfo) {
        Optional.ofNullable(this.sysDeptService.getById(sysUser.getDeptId())).ifPresent(sysDept -> userInfo.setDeptName(sysDept.getDeptName()));
    }

    private Set<Long> getHasNormalPermissionRoleId(List<UserRoleBO> userRoleBOList) {
        // @formatter:off
        return userRoleBOList.stream()
                .filter(permission -> StrUtil.equals(permission.getRowPermissionType(), DataPermissionType.CUSTOMIZES.getType()))
                .map(UserRoleBO::getRoleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    private void setRowPermission(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        userInfo.setPermissionType(this.getMaxPermissionScope(userRoleBOList));
        Set<Long> roleIdSet = this.getHasNormalPermissionRoleId(userRoleBOList);
        Set<String> permissionCodeSet = this.sysRoleRowPermissionService.listRowPermission(roleIdSet);
        userInfo.setRowPermissionCode(permissionCodeSet);
    }

    private void setRoleCode(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        userInfo.setUserRoleCodes(userRoleBOList.stream().map(UserRoleBO::getRoleCode).collect(Collectors.toSet()));
    }

    private void setUsersRoleId(List<UserRoleBO> userRoleBOList, UserInfoDTO userInfo) {
        Set<Long> roleIds = userRoleBOList.stream().map(UserRoleBO::getRoleId).filter(Objects::nonNull).collect(Collectors.toSet());
        userInfo.setUserRoleIds(roleIds);
    }

    /**
     * 获取最大权限范围
     *
     * @param userRoleBOList 用户角色bolist
     * @return {@link String }
     */
    private String getMaxPermissionScope(List<UserRoleBO> userRoleBOList) {
        Set<String> permissionCodeSet = userRoleBOList.stream().map(UserRoleBO::getRowPermissionType)
                // 确保过滤掉null值，避免NullPointerException
                .filter(Objects::nonNull).collect(Collectors.toSet());
        // 初始化resultSet
        Set<DataPermissionType> resultSet = Sets.newHashSet();
        // 遍历DataPermissionCode枚举，匹配权限
        for (DataPermissionType value : DataPermissionType.values()) {
            if (permissionCodeSet.contains(value.getType())) {
                resultSet.add(value);
            }
        }
        return resultSet.stream().min(Comparator.comparingInt(DataPermissionType::getLevel)).map(DataPermissionType::toString).orElse("");
    }

    /**
     * 按用户id查询用户
     *
     * @param userId 用户id
     * @return {@link UserPrincipal }
     */
    @Override
    public UserPrincipal loadUserByUserId(String userId) {
        SysUser sysUser = this.getById(userId);
        AssertUtil.isNotNull(sysUser, USER_NOT_FOUND);
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");
        return convertResponseUserInfo(userInfoDTO);
    }

    /**
     * 按角色编码列出用户权限
     *
     * @param roleCode 角色编码
     * @return {@link List }<{@link String }>
     */
    @Override
    public List<String> loadUserPermissionByRoleCode(String roleCode) {
        // 权限
        return this.sysMenuService.listUserPermissionByRoleCode(roleCode);
    }

    /**
     * 通过电话查询用户
     *
     * @param phone 电话
     * @return {@link UserPrincipal }
     */
    @Override
    public UserPrincipal loadUserByPhone(String phone) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone));
        AssertUtil.isNotNull(sysUser, USER_NOT_FOUND);
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        return convertResponseUserInfo(userInfoDTO);
    }


    /**
     * 按用户名查询用户
     *
     * @param username 用户名
     * @return {@link UserPrincipal }
     */
    @Override
    public UserPrincipal loadUserByUsername(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        AssertUtil.isNotNull(sysUser, USER_NOT_FOUND);
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        return convertResponseUserInfo(userInfoDTO);
    }


    /**
     * 通过电子邮件查询用户
     *
     * @param email 电子邮件
     * @return {@link UserPrincipal }
     */
    @Override
    public UserPrincipal loadUserByEmail(String email) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email));
        AssertUtil.isNotNull(sysUser, USER_NOT_FOUND);
        UserInfoDTO userInfoDTO = this.buildLoginUserInfo(sysUser);
        return convertResponseUserInfo(userInfoDTO);
    }

    /**
     * 按用户id查询用户角色
     *
     * @param userId 用户id
     * @return {@link List }<{@link String }>
     */
    @Override
    public List<String> loadUserRoleByUserId(String userId) {
        List<UserRoleBO> userRoleBOList = sysRoleService.listRoleByUserId(Long.valueOf(userId));
        AssertUtil.isTrue(CollUtil.isNotEmpty(userRoleBOList), USERS_ROLE_IS_NULL);
        return userRoleBOList.stream().map(UserRoleBO::getRoleCode).collect(Collectors.toList());
    }

}
