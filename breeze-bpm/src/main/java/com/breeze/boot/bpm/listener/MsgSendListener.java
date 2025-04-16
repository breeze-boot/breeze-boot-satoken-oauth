package com.breeze.boot.bpm.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class MsgSendListener implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("========MyServiceTask==========");
    }
}
