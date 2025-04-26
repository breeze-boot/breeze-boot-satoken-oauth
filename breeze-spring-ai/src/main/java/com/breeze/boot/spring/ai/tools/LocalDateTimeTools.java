package com.breeze.boot.spring.ai.tools;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateTimeTools {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    @Tool(description = "Retrieve the current date and time of a specified city based on its time zone ID, with Shanghai as the default")
    public String getCityTimeMethod(@ToolParam(description = "The time zone ID of the city, e.g., 'Asia/Shanghai' or 'America/New_York'") String timeZoneId) {
        log.info("The current time zone is {}", timeZoneId.replace("\n", "").replace("\r", ""));

        if (StrUtil.isBlankIfStr(timeZoneId)) {
            timeZoneId = "Asia/Shanghai";
        }
        // 使用完整的格式字符串
        return String.format("The current time zone is %s and the current time is %s", timeZoneId, this.getLocalDateTimeByZoneId(timeZoneId));
    }


    private String getLocalDateTimeByZoneId(String timeZoneId) {
        ZoneId zid = ZoneId.of(timeZoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zid);
        return zonedDateTime.format(formatter);
    }

}
