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

package com.breeze.boot.auth.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import cn.dev33.satoken.stp.StpUtil;
import com.breeze.boot.core.constants.CoreConstants;
import com.breeze.boot.core.model.PageQuery;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.bo.SysLogBO;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.log.events.PublisherSaveSysLogEvent;
import com.breeze.boot.log.events.SysLogSaveEvent;
import com.breeze.boot.auth.model.vo.OnlineUserVO;
import com.breeze.boot.auth.service.OnlineUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.breeze.boot.log.enums.LogEnum.LogType.LOGIN;
import static com.breeze.boot.log.enums.LogEnum.Result.SUCCESS;
import static com.breeze.boot.log.enums.LogType.FOCUS_KICK;
import static com.breeze.boot.log.enums.LogType.FOCUS_LOGOUT;

/**
 * 系统用户服务impl
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Service
@RequiredArgsConstructor
public class OnlineUserServiceImpl implements OnlineUserService {

    private final PublisherSaveSysLogEvent publisherSaveSysLogEvent;

    @Override
    public Result<List<OnlineUserVO>> listAllOnlineUser(PageQuery query) {
        // 返回数据对象
        List<OnlineUserVO> resultList = new ArrayList<>();
        // 获取所有登录的用户ids
        List<String> logIds = StpUtil.searchSessionId("", 0, -1, false);
        // 便利获取登录信息
        for (String logId : logIds) {
            SaSession session = StpUtil.getSessionBySessionId(logId);
            UserPrincipal userPrincipal = session.getModel(CoreConstants.USER_TYPE, UserPrincipal.class);
            List<TokenSign> tokenSignList = session.getTokenSignList();
            resultList.add(
                    OnlineUserVO.builder()
                            .userId(userPrincipal.getId())
                            .userCode(userPrincipal.getUserCode())
                            .username(userPrincipal.getUsername())
                            .displayName(userPrincipal.getDisplayName())
                            .loginDeviceCount(tokenSignList.size())
                            .loginDevice(tokenSignList)
                            .sessionCreateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(session.getCreateTime()), ZoneId.systemDefault()))
                            .build()
            );
        }
        return Result.ok(resultList);
    }

    @Override
    public Result<Boolean> kickOut(Long userId) {
        StpUtil.kickout(userId);
        this.saveLog(FOCUS_KICK, String.valueOf(userId));
        return Result.ok(Boolean.TRUE, "操作成功");
    }

    @Override
    public Result<Boolean> kickOutByTokenValue(String token) {
        StpUtil.kickoutByTokenValue(token);
        this.saveLog(FOCUS_KICK, token);
        return Result.ok(Boolean.TRUE, "操作成功");
    }

    @Override
    public Result<Boolean> logoutByTokenValue(String token) {
        StpUtil.logoutByTokenValue(token);
        this.saveLog(FOCUS_LOGOUT, token);
        return Result.ok(Boolean.TRUE, "操作成功");
    }

    @Override
    public Result<Boolean> logout(Long userId) {
        StpUtil.logout(userId);
        this.saveLog(FOCUS_LOGOUT, String.valueOf(userId));
        return Result.ok(Boolean.TRUE, "操作成功");
    }

    private void saveLog(LogType logType, String userId) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes is null");
        SysLogBO sysLogBO = this.buildLog(requestAttributes.getRequest(), userId, logType);
        this.publisherSaveSysLogEvent.publisherEvent(new SysLogSaveEvent(sysLogBO));
    }

    /**
     * 执行日志 业务
     *
     * @param request 请求
     * @param param   参数
     * @return {@link SysLogBO }
     */
    @SneakyThrows
    private SysLogBO buildLog(HttpServletRequest request, String param, LogType logType) {
        String userAgent = request.getHeader("User-Agent");
        return SysLogBO.builder()
                .system(userAgent)
                .logTitle(logType.getName())
                .doType(logType.getCode())
                .logType(LOGIN.getCode())
                .ip(request.getRemoteAddr())
                .requestType(request.getMethod())
                .paramContent(param)
                .result(SUCCESS.getCode())
                .createTime(LocalDateTime.now())
                .build();
    }

}
