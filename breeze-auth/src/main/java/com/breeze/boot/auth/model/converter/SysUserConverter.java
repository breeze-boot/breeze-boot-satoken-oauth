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

package com.breeze.boot.auth.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.auth.model.bo.UserBO;
import com.breeze.boot.auth.model.entity.SysUser;
import com.breeze.boot.auth.model.excel.UserExcel;
import com.breeze.boot.auth.model.form.UserForm;
import com.breeze.boot.auth.model.vo.UserVO;
import com.breeze.boot.satoken.model.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 用户转换器
 *
 * @author gaoweixuan
 * @since 2024-07-14
 */
@Mapper(componentModel = "spring")
public interface SysUserConverter {

    Page<UserVO> pageBO2PageVO(Page<UserBO> userBOPage);

    SysUser form2Entity(UserForm UserForm);

    UserVO entity2VO(SysUser sysUser);

    List<UserVO> boList2VOList(List<UserBO> userBOList);
    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    UserInfoDTO entity2BaseLoginUser(SysUser sysUser);

    List<UserExcel> vo2Excel(List<UserVO> records);

}
