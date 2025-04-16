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

package com.breeze.boot.bpm.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.bpm.service.IUserService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.bpm.model.query.BpmUserQuery;
import com.breeze.boot.bpm.model.vo.BpmUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 流程用户管理模块
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bpm/v1/user")
@Tag(name = "流程用户管理模块", description = "BpmUserController")
public class BpmUserController {

    private final IUserService userService;

    /**
     * 列表
     *
     * @param userQuery 用户查询
     * @return {@link Result }<{@link Page }<{@link BpmUserVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("bpm:user:list")
    public Result<Page<BpmUserVO>> list(@RequestBody BpmUserQuery userQuery) {
        return Result.ok(this.userService.listPage(userQuery));
    }

}
