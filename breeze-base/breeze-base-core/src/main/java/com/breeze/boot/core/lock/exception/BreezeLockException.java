package com.breeze.boot.core.lock.exception;

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.MessageUtil;

public class BreezeLockException extends RuntimeException {

    public BreezeLockException() {
        super(MessageUtil.getMessage(ResultCode.LOCK_EXCEPTION.getKey()));
    }
}