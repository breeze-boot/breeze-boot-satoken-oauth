package com.breeze.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface FileEnum {

    @AllArgsConstructor
    @Getter
    enum StoreTypeEnum {


        LOCAL(0, "本地"),
        MINIO(1, "minio");

        private final Integer value;
        private final String label;

    }
}
