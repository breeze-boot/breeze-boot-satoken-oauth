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

package com.breeze.boot.manager.controller;

import com.breeze.boot.manager.service.FlowableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/user")
@Tag(name = "系统管理模块", description = "FlowableController")
public class FlowableController {

    private final FlowableService flowableService;

    /**
     * 同步用户角色和用户角色关系
     */
    @Operation(summary = "同步用户角色和用户角色关系")
    @GetMapping("/syncFlowableUser")
    public void syncFlowableUser() {
        this.flowableService.syncFlowableUser();
    }

}
