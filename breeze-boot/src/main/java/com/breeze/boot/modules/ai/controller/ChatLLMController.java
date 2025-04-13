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

package com.breeze.boot.modules.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.ai.model.entity.ChatLLM;
import com.breeze.boot.modules.ai.service.IChatLLMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI聊天大模型管理
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@RestController
@Tag(name = "AI配置管理", description = "管理聊天大模型")
@RequiredArgsConstructor
@RequestMapping("/chat/llm")
public class ChatLLMController {

    private final IChatLLMService chatLLMService;

    @Operation(summary = "列表")
    @SaCheckPermission("chat:llm:list")
    @GetMapping
    public Result<List<?>> list() {
        List<ChatLLM> list = this.chatLLMService.list();
        return Result.ok(list);
    }

    @Operation(summary = "详情")
    @SaCheckPermission("chat:llm:info")
    @GetMapping(value = "/{llmId}")
    public Result<?> info(@Parameter(description = "项目ID") @NotNull(message = "模型ID不能为空") @PathVariable Long llmId) {
        return Result.ok(this.chatLLMService.getInfoById(llmId));
    }

    @Operation(summary = "新增")
    @SaCheckPermission("chat:llm:create")
    @PostMapping
    public Result<?> save(@Valid @ParameterObject @RequestBody ChatLLM chatLLM) {
        return this.chatLLMService.saveChatLLM(chatLLM);
    }

    @Operation(summary = "修改")
    @SaCheckPermission("chat:llm:modify")
    @PutMapping("/{id}")
    public Result<?> edit(@Parameter(description = "模型ID") @PathVariable Long id, @ParameterObject @Valid @RequestBody ChatLLM chatLLM) {
        return Result.ok(this.chatLLMService.modifyChatLLM(id, chatLLM));
    }

    @Operation(summary = "删除")
    @SaCheckPermission("chat:llm:delete")
    @DeleteMapping("/{llmIds}")
    public Result<Boolean> delete(@Parameter(description = "模型ID") @NotEmpty(message = "参数不能为空") @RequestBody Long[] llmIds) {
        return Result.ok(this.chatLLMService.deleteChatLLM(List.of(llmIds)));
    }
}