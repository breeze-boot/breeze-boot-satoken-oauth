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
import com.breeze.boot.modules.ai.model.entity.ChatDoc;
import com.breeze.boot.modules.ai.model.form.ChatDocForm;
import com.breeze.boot.modules.ai.model.vo.ChatDocVO;
import com.breeze.boot.modules.ai.service.IChatDocService;
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
 * AI聊天知识库文档管理
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@RestController
@Tag(name = "AI知识库管理")
@RequiredArgsConstructor
@RequestMapping("/chat/doc")
public class ChatDocController {

    private final IChatDocService chatDocService;

    @Operation(summary = "列表")
    @SaCheckPermission("chat:doc:list")
    @GetMapping
    public Result<List<?>> list() {
        List<ChatDoc> list = this.chatDocService.list();
        return Result.ok(list);
    }

    @Operation(summary = "详情")
    @SaCheckPermission("chat:doc:info")
    @GetMapping(value = "/{docId}")
    public Result<ChatDocVO> info(@Parameter(description = "知识库文档ID") @NotNull(message = "知识库文档ID不能为空") @PathVariable Long docId) {
        return Result.ok(this.chatDocService.getInfoById(docId));
    }

    @Operation(summary = "新增")
    @SaCheckPermission("chat:doc:create")
    @PostMapping
    public Result<Boolean> save(@Valid @ParameterObject @RequestBody ChatDocForm chatDocForm) {
        return Result.ok(this.chatDocService.saveChatDoc(chatDocForm));
    }

    @Operation(summary = "修改")
    @SaCheckPermission("chat:doc:modify")
    @PutMapping("/{id}")
    public Result<Boolean> edit(@Parameter(description = "知识库文档ID") @PathVariable Long id,
                          @ParameterObject @Valid @RequestBody ChatDocForm chatDocForm) {
        return Result.ok(this.chatDocService.modifyChatDoc(id, chatDocForm));
    }

    @Operation(summary = "删除")
    @SaCheckPermission("chat:doc:delete")
    @DeleteMapping("/{docIds}")
    public Result<Boolean> delete(@Parameter(description = "知识库文档ID") @NotEmpty(message = "参数不能为空") @RequestBody Long[] docIds) {
        return Result.ok(this.chatDocService.deleteChatDoc(List.of(docIds)));
    }
}