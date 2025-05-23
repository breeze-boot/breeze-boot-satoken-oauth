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

package com.breeze.boot.gen.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.gen.domain.entity.Column;
import com.breeze.boot.gen.domain.entity.Table;
import com.breeze.boot.gen.domain.query.TableQuery;
import com.breeze.boot.gen.service.SysMysqlDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统表元数据控制器
 *
 * @author gaoweixuan
 * @since 2024-07-17
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/dev/v1/db")
@Tag(name = "系统表元数据控制器", description = "SysMysqlDbController")
public class SysMysqlDbController {

    private final SysMysqlDbService sysMysqlDbService;

    /**
     * 表列表
     *
     * @param query 表参数
     * @return {@link Result }<{@link List }<{@link Table }>>
     */
    @Operation(summary = "表列表")
    @PostMapping("/tables")
    @SaCheckPermission("gen:db:tables")
    public Result<List<Table>> listTable(@RequestBody TableQuery query) {
        return Result.ok(this.sysMysqlDbService.listTable(query));
    }

    /**
     * 表字段列表
     *
     * @param tableName 表名称
     * @return {@link Result }<{@link List }<{@link Column }>>
     */
    @Operation(summary = "表字段列表")
    @GetMapping("/columns")
    @SaCheckPermission("gen:db:columns")
    public Result<List<Column>> listTableColumn(@RequestParam String tableName) {
        return Result.ok(this.sysMysqlDbService.listTableColumn(tableName));
    }

    /**
     * 表名下拉框
     *
     * @param query 查询
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "表名下拉框", description = "下拉框接口")
    @GetMapping("/selectTable")
    public Result<List<Map<String, Object>>> selectTable(@RequestParam TableQuery query) {
        return Result.ok(this.sysMysqlDbService.selectTable(query));
    }

    /**
     * 字段下拉框
     *
     * @param tableName 表名
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "字段下拉框", description = "下拉框接口")
    @GetMapping("/selectTableColumn")
    public Result<List<Map<String, Object>>> selectTableColumn(@RequestParam("tableName") String tableName) {
        return Result.ok(this.sysMysqlDbService.selectTableColumn(tableName));
    }

}
