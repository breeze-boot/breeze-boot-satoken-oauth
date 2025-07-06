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

package com.breeze.boot.code.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.code.mapper.SequenceNoMapper;
import com.breeze.boot.code.model.entity.SequenceNo;
import com.breeze.boot.code.service.SequenceNoService;
import com.breeze.boot.core.lock.annotation.RedissonLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 自动编码服务Impl
 *
 * @author gaoweixuan
 * @since 2023-03-06
 */
@Service
public class SequenceNoServiceImpl extends ServiceImpl<SequenceNoMapper, SequenceNo> implements SequenceNoService {

    /**
     * 生成代码
     *
     * @param customPrefix 自定义前缀
     * @param sequenceType 序列类型
     * @return {@link String }
     */
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(value = "'lock_' + #sequenceType")
    public String generateCode(String customPrefix, String sequenceType) {
        try {
            LocalDate now = LocalDate.now();
            String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
            // 拼接得到最终的 sequenceType
            String finalSequenceType = customPrefix + yearMonth;

            // 获取当前序列，使用拼接后的 sequenceType
            SequenceNo genNo = this.getSequenceByType(finalSequenceType);
            if (Objects.isNull(genNo)) {
                genNo = new SequenceNo();
                genNo.setSequenceType(finalSequenceType);
                genNo.setCurrentSequence(1);
                this.save(genNo);
            } else {
                genNo.setCurrentSequence(genNo.getCurrentSequence() + 1);
                // 更新时使用拼接后的 sequenceType
                this.updateSequence(finalSequenceType, genNo.getCurrentSequence());
            }

            // 避免 yearMonth 重复拼接
            return finalSequenceType + String.format("%04d", genNo.getCurrentSequence());
        } catch (Exception e) {
            log.error("", e);
        }
        return "";
    }

    /**
     * 生成代码
     *
     * @param customPrefix 自定义前缀
     * @param sequenceType 序列类型
     * @return {@link String }
     */
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(value = "'lock_' + #sequenceType")
    public String generateCode(String customPrefix, String sequenceType, Integer length) {
        try {
            // 获取当前序列，使用拼接后的 sequenceType
            SequenceNo genNo = this.getSequenceByType(customPrefix);
            if (Objects.isNull(genNo)) {
                genNo = new SequenceNo();
                genNo.setSequenceType(customPrefix);
                genNo.setCurrentSequence(1);
                this.save(genNo);
            } else {
                genNo.setCurrentSequence(genNo.getCurrentSequence() + 1);
                // 更新时使用拼接后的 sequenceType
                this.updateSequence(customPrefix, genNo.getCurrentSequence());
            }

            // 使用传入的 length 参数进行格式化
            return customPrefix + String.format("%0" + length + "d", genNo.getCurrentSequence());
        } catch (Exception e) {
            log.error("生成序列号失败", e);
            throw new RuntimeException("生成序列号失败", e); // 显式抛出异常触发事务回滚
        }
    }


    private SequenceNo getSequenceByType(String sequenceType) {
        return this.getOne(Wrappers.<SequenceNo>lambdaQuery().eq(SequenceNo::getSequenceType, sequenceType));
    }

    private void updateSequence(String sequenceType, Integer newSequence) {
        SequenceNo sequence = new SequenceNo();
        sequence.setCurrentSequence(newSequence);
        sequence.setSequenceType(sequenceType);
        this.update(sequence, Wrappers.<SequenceNo>lambdaUpdate().eq(SequenceNo::getSequenceType, sequenceType));
    }

}
