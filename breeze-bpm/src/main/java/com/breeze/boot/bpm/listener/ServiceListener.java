package com.breeze.boot.bpm.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("serviceListener")
public class ServiceListener implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("========MyServiceTask==========");
    }
}
