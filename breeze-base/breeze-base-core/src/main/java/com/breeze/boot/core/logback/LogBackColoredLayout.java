package com.breeze.boot.core.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.HashMap;
import java.util.Map;

public class LogBackColoredLayout extends PatternLayout {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";

    // 使用 Map 缓存日志级别与颜色的映射关系
    private static final Map<Level, String> LEVEL_COLOR_MAP = new HashMap<>();

    static {
        LEVEL_COLOR_MAP.put(Level.ERROR, ANSI_RED);
        LEVEL_COLOR_MAP.put(Level.WARN, ANSI_YELLOW);
        LEVEL_COLOR_MAP.put(Level.INFO, ANSI_BLUE);
        LEVEL_COLOR_MAP.put(Level.DEBUG, ANSI_GREEN);
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String originalLayout = super.doLayout(event);
        Level level = event.getLevel();
        // 从 Map 中获取日志级别对应的颜色
        String levelColor = LEVEL_COLOR_MAP.getOrDefault(level, ANSI_RESET);

        return getColoredLayout(event, originalLayout, levelColor);
    }

    private static String getColoredLayout(ILoggingEvent event, String originalLayout, String levelColor) {
        String levelStr = event.getLevel().toString();
        // 提前检查日志级别是否为空
        if (levelStr == null || levelStr.isEmpty()) {
            return ANSI_BLUE + originalLayout + ANSI_RESET;
        }

        int levelStartIndex = originalLayout.indexOf(levelStr);
        int levelEndIndex = levelStartIndex + levelStr.length();

        return ANSI_BLUE +
                originalLayout.substring(0, levelStartIndex) +
                levelColor +
                originalLayout.substring(levelStartIndex, levelEndIndex) +
                ANSI_BLUE +
                originalLayout.substring(levelEndIndex) +
                ANSI_RESET;
    }
}