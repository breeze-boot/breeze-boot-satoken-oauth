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

package com.breeze.boot.modules.ai.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

public class LanguageDetector {

    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4E00-\u9FFF]");
    private static final Pattern ENGLISH_PATTERN = Pattern.compile("[a-zA-Z]");

    public static Language detectLanguage(String input) {
        if (input == null || input.isEmpty()) {
            return Language.UNKNOWN;
        }

        int chineseCount = 0;
        int englishCount = 0;

        for (char c : input.toCharArray()) {
            if (CHINESE_PATTERN.matcher(String.valueOf(c)).matches()) {
                chineseCount++;
            } else if (ENGLISH_PATTERN.matcher(String.valueOf(c)).matches()) {
                englishCount++;
            }
        }

        // 判断逻辑（可根据需求调整阈值）
        if (chineseCount > englishCount) {
            return Language.CHINESE;
        } else if (englishCount > 0) {
            return Language.ENGLISH;
        } else {
            return Language.UNKNOWN;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Language {
        CHINESE("chinese"), ENGLISH("english"), UNKNOWN("UNKNOWN");
        private final String name;
    }
}