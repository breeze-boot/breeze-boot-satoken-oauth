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

package com.breeze.boot.modules.auth.service;

import com.breeze.boot.core.base.PageQuery;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.vo.OnlineUserVO;

import java.util.List;

/**
 * 系统用户服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface OnlineUserService {

    Result<List<OnlineUserVO>> listAllOnlineUser(PageQuery query);

    Result<Boolean> kickOut(Long userId);

    Result<Boolean> kickOutByTokenValue(String token);

    Result<Boolean> logoutByTokenValue(String token);

    Result<Boolean> logout(Long userId);

}
