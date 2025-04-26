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

package com.breeze.boot.mybatis.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.breeze.boot.core.model.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;

/**
 * mybatis元对象处理程序
 *
 * @author gaoweixuan
 * @since 2022-10-12
 */
@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createBy")) {
            this.strictInsertFill(metaObject, "createBy", this::getUserCode, String.class);
        }
        if (metaObject.hasGetter("createName")) {
            this.strictInsertFill(metaObject, "createName", this::getUsername, String.class);
        }
        if (metaObject.hasGetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 普通更新操作
        if (metaObject.hasGetter("updateBy")) {
            this.strictUpdateFill(metaObject, "updateBy", this::getUserCode, String.class);
        }
        if (metaObject.hasGetter("updateName")) {
            this.strictUpdateFill(metaObject, "updateName", this::getUsername, String.class);
        }
        if (metaObject.hasGetter("updateTime")) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
    }

    private String getUsername() {
        boolean login = StpUtil.isLogin();
        if (login) {
            return ((UserPrincipal) StpUtil.getSession().get(USER_TYPE)).getUsername();
        }
        return null;
    }

    private String getUserCode() {
        boolean login = StpUtil.isLogin();
        if (login) {
            return ((UserPrincipal) StpUtil.getSession().get(USER_TYPE)).getUserCode();
        }
        return null;
    }
}