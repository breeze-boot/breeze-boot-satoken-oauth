package com.breeze.boot.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UserEnum {


    @AllArgsConstructor
    @Getter
    enum Type {
        INNER(0, "内部用户"),
        OUTER(1, "外部用户"),
        ;

        private final Integer code;
        private final String desc;

    }
}
