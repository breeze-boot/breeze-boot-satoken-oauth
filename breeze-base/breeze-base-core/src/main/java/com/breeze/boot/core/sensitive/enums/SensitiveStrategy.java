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

package com.breeze.boot.core.sensitive.enums;

import cn.hutool.core.util.DesensitizedUtil;
import com.breeze.boot.core.sensitive.SensitiveInterface;
import lombok.Getter;

/**
 * 敏感类型
 *
 * @author gaoweixuan
 * @since 2023/06/01
 */
@Getter
public enum SensitiveStrategy {

    /**
     * 真实名称
     */
    REAL_NAME(DesensitizedUtil::chineseName),
    /**
     * 电子邮件
     */
    EMAIL(DesensitizedUtil::email),
    /**
     * 手机号
     */
    PHONE(DesensitizedUtil::fixedPhone),
    /**
     * 身份证号
     */
    ID_CARD(s -> DesensitizedUtil.idCardNum(s, 6, 10)),
    /**
     * 地址
     */
    ADDRESS(s -> DesensitizedUtil.address(s, 7)),
    /**
     * 银行卡
     */
    BANK_CARD(DesensitizedUtil::bankCard),

    ;

    private final SensitiveInterface sensitiveStrategy;

    SensitiveStrategy(SensitiveInterface sensitiveStrategy) {
        this.sensitiveStrategy = sensitiveStrategy;
    }
}
