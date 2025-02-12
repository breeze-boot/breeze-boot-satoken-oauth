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

package com.breeze.boot.core.base;

import lombok.Data;

import java.util.List;

/**
 * 查询组装条件
 *
 * @author gaoweixuan
 * @since 2025-02-01
 */
@Data
public class Condition {

    private String condition;
    private String field;
    private String operator;
    private String value;
    private List<Condition> conditions;

}
