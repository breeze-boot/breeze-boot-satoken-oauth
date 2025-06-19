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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.auth.model.entity.SysRegisteredClient;
import com.breeze.boot.auth.model.form.RegisteredClientForm;
import com.breeze.boot.auth.model.form.ResetClientSecretForm;
import com.breeze.boot.auth.model.query.RegisteredClientQuery;
import com.breeze.boot.auth.model.vo.RegisteredClientVO;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.satoken.oauth2.IClientService;

import java.util.List;
import java.util.Map;

/**
 * 注册客户服务接口
 *
 * @author gaoweixuan
 * @since 2023/05/09
 */
public interface SysRegisteredClientService extends IService<SysRegisteredClient>, IClientService {

    /**
     * 通过客户端ID获取客户端信息
     *
     * @param clientId 客户端Id
     * @return {@link SysRegisteredClient}
     */
    @Override
    SysRegisteredClient getByClientId(String clientId);

    /**
     * 通过ID获取客户端信息
     *
     * @param id id
     * @return {@link SysRegisteredClient}
     */
    @Override
    SysRegisteredClient getById(String id);

    /**
     * 列表页面
     *
     * @param query 注册客户端参数
     * @return {@link Page}<{@link RegisteredClientVO}>
     */
    Page<RegisteredClientVO> listPage(RegisteredClientQuery query);

    /**
     * 保存注册客户端
     *
     * @param form 注册客户端表单
     * @return {@link Result }<{@link Boolean }>
     */
    Result<Boolean> saveRegisteredClient(RegisteredClientForm form);

    /**
     * 更新
     *
     * @param id                   ID
     * @param form 注册客户端表单
     * @return {@link Boolean}
     */
    Boolean modifyRegisteredClient(Long id, RegisteredClientForm form);

    /**
     * 重置客户端密钥
     *
     * @param form 重置客户端密钥
     * @return {@link Boolean}
     */
    Boolean resetClientSecret(ResetClientSecretForm form);

    /**
     * 信息
     *
     * @param clientId 客户端ID
     * @return {@link RegisteredClientVO}
     */
    RegisteredClientVO info(Long clientId);

    /**
     * 客户端下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link String }>>>
     */
    Result<List<Map<String, String>>> selectRegisteredClient();

}
