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
import com.breeze.boot.system.model.entity.SysEmailConfig;
import com.breeze.boot.system.model.form.EmailConfigForm;
import com.breeze.boot.system.model.form.EmailConfigOpenForm;
import com.breeze.boot.system.model.query.EmailConfigQuery;
import com.breeze.boot.system.model.vo.EmailConfigVO;

/**
 * 系统邮箱服务
 *
 * @author gaoweixuan
  * @since 2024-07-13
 */
public interface SysEmailConfigService extends IService<SysEmailConfig> {

    /**
     * 列表页
     *
     * @param query 电子邮件查询
     * @return {@link Page }<{@link EmailConfigVO }>
     */
    Page<EmailConfigVO> listPage(EmailConfigQuery query);

    /**
     * 按id获取信息
     *
     * @param emailId 电子邮件id
     * @return {@link EmailConfigVO }
     */
    EmailConfigVO getInfoById(Long emailId);

    /**
     * 保存电子邮件
     *
     * @param form 电子邮件表单
     * @return {@link Boolean }
     */
    Boolean saveEmail(EmailConfigForm form);

    /**
     * 修改电子邮件
     *
     * @param id        ID
     * @param form 电子邮件表单
     * @return {@link Boolean }
     */
    Boolean modifyEmail(Long id, EmailConfigForm form);

    /**
     * 开启关闭锁定
     *
     * @param form 邮件打开表单
     * @return {@link Boolean }
     */
    Boolean open(EmailConfigOpenForm form);

}
