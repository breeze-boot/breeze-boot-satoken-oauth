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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.system.model.entity.SysEmailSubject;
import com.breeze.boot.system.model.form.MSubjectForm;
import com.breeze.boot.system.model.form.MSubjectOpenForm;
import com.breeze.boot.system.model.form.MSubjectSetUserForm;
import com.breeze.boot.system.model.query.MSubjectQuery;
import com.breeze.boot.system.model.vo.MSubjectEmailVO;
import com.breeze.boot.system.model.vo.MSubjectVO;

import java.util.List;

/**
 * 系统邮箱主题服务
 *
 * @author gaoweixuan
 * @since 2024-07-13
 */
public interface SysMSubjectService extends IService<SysEmailSubject> {

    /**
     * 列表页
     *
     * @param query 邮箱主题查询
     * @return {@link Page }<{@link MSubjectVO }>
     */
    Page<MSubjectVO> listPage(MSubjectQuery query);

    /**
     * 按id获取信息
     *
     * @param subjectId 主题ID
     * @return {@link MSubjectVO }
     */
    MSubjectVO getInfoById(Long subjectId);

    /**
     * 保存邮箱主题
     *
     * @param form 邮箱主题表单
     * @return {@link Boolean }
     */
    Boolean saveEmailSubject(MSubjectForm form);

    /**
     * 修改邮箱主题
     *
     * @param id   ID
     * @param form 邮箱主题表单
     * @return {@link Boolean }
     */
    Boolean modifyEmailSubject(Long id, MSubjectForm form);

    /**
     * 邮寄
     *
     * @param id ID
     * @return {@link Boolean }
     */
    Boolean send(Long id);

    /**
     * 开启关闭锁定
     *
     * @param form 邮箱主题开关表单
     * @return {@link Boolean }
     */
    Boolean open(MSubjectOpenForm form);

    /**
     * 设置电子邮件用户
     *
     * @param id   ID
     * @param form m主题集用户表单
     * @return {@link Boolean }
     */
    Boolean setEmailUser(Long id, MSubjectSetUserForm form);

    /**
     * 查看邮箱接收人
     *
     * @param id ID
     * @return {@link Boolean }
     */
    List<MSubjectEmailVO> listCcEmailUser(Long id);

    /**
     * 查看邮箱抄送人
     *
     * @param id ID
     * @return {@link List }<{@link MSubjectEmailVO }>
     */
    List<MSubjectEmailVO> listToEmailUser(Long id);

}
