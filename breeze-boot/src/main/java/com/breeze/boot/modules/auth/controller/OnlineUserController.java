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

package com.breeze.boot.modules.auth.controller;

import com.breeze.boot.core.base.PageQuery;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.vo.OnlineUserVO;
import com.breeze.boot.modules.auth.service.OnlineUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 在线用户控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/onlineUser")
@Tag(name = "在线用户管理模块", description = "OnlineUserController")
public class OnlineUserController {


    private final OnlineUserService onlineUserService;

    /**
     * 查询所有在线用户
     *
     * @param pageQuery 页面查询
     * @return {@link Result }<{@link List }<{@link OnlineUserVO }>>
     */
    @Operation(summary = "查询所有在线用户")
    @GetMapping("/list")
    public Result<List<OnlineUserVO>> listAllOnlineUser(PageQuery pageQuery) {
        return this.onlineUserService.listAllOnlineUser(pageQuery);
    }

    /**
     * 强制下线
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "强制下线")
    @PutMapping("/kickOut/{userId}")
    public Result<Boolean> kickOut(@PathVariable Long userId) {
        return this.onlineUserService.kickOut(userId);
    }

    /**
     * 强制下线
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "强制下线")
    @PutMapping("/kickOutByTokenValue/{token}")
    public Result<Boolean> kickOutByTokenValue(@PathVariable String token) {
        return this.onlineUserService.kickOutByTokenValue(token);
    }

    /**
     * 强制注销
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "强制注销")
    @PutMapping("/logoutByTokenValue/{token}")
    public Result<Boolean> logoutByTokenValue(@PathVariable String token) {
        return this.onlineUserService.logoutByTokenValue(token);
    }

    /**
     * 强制注销
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "强制注销")
    @PutMapping("/logout/{userId}")
    public Result<Boolean> logout(@PathVariable Long userId) {
        return this.onlineUserService.logout(userId);
    }

}
