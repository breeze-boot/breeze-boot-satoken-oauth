/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.system.controller;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.service.SysDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 省份区域信息表 前端控制器
 */
@RestController
@RequestMapping("/district")
@RequiredArgsConstructor
public class SysDistrictController {

    private final SysDistrictService districtService;

    @GetMapping("/getProvince")
    public Result<?> getProvince() {
        return Result.ok(districtService.getProvince());
    }

    @GetMapping("/getDistrictItem")
    public Result<?> getDistrictItem(String parentId, String level) {
        return Result.ok(districtService.getDistrictItem(parentId, level));
    }

}
