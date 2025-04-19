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

package com.breeze.boot.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.query.UserMsgQuery;
import com.breeze.boot.modules.system.model.vo.MsgUserVO;
import com.breeze.boot.modules.system.service.SysMsgUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统用户消息控制器
 *
 * @author gaoweixuan
 * @since 2022-11-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/msgUser")
@Tag(name = "系统用户消息管理模块", description = "SysUserMsgController")
public class SysMsgUserController {

    /**
     * 系统用户消息快照服务
     */
    private final SysMsgUserService sysMsgUserService;

    /**
     * 列表
     *
     * @param query 用户消息查询
     * @return {@link Result}<{@link IPage}<{@link MsgUserVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:msgUser:list")
    public Result<IPage<MsgUserVO>> list(@RequestBody UserMsgQuery query) {
        return Result.ok(this.sysMsgUserService.listPage(query));
    }

    /**
     * 获取用户的消息
     *
     * @param username 用户名
     * @return {@link Result}<{@link List}<{@link MsgUserVO}>>
     */
    @Operation(summary = "获取用户的消息")
    @GetMapping("/listUsersMsg")
    public Result<List<MsgUserVO>> listUsersMsg(@RequestParam String username) {
        return Result.ok(this.sysMsgUserService.listUsersMsg(username));
    }

    /**
     * 关闭
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "关闭")
    @PutMapping("/close/{msgId}")
    public Result<Boolean> close(@PathVariable Long msgId) {
        return this.sysMsgUserService.close(msgId);
    }

    /**
     * 标记已读
     *
     * @param msgId 消息Id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "已读")
    @PutMapping("/read/{msgId}")
    public Result<Boolean> read(@Parameter(description = "消息ID") @PathVariable Long msgId) {
        return this.sysMsgUserService.read(msgId);
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:msgUser:delete")
    @BreezeSysLog(description = "消息信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "消息记录IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysMsgUserService.removeUserMsgByIds(Arrays.asList(ids));
    }

}
