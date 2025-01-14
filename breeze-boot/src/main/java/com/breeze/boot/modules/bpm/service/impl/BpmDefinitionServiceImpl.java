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

package com.breeze.boot.modules.bpm.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.bpm.model.form.BpmDefinitionDeleteForm;
import com.breeze.boot.modules.bpm.model.form.BpmDesignXmlFileForm;
import com.breeze.boot.modules.bpm.model.form.BpmDesignXmlStringForm;
import com.breeze.boot.modules.bpm.model.query.BpmDefinitionQuery;
import com.breeze.boot.modules.bpm.model.vo.BpmDefinitionVO;
import com.breeze.boot.modules.bpm.model.vo.XmlVO;
import com.breeze.boot.modules.bpm.service.IActReDeploymentService;
import com.breeze.boot.modules.bpm.service.IBpmDefinitionService;
import com.breeze.boot.satoken.utils.BreezeStpUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程资源管理服务impl
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmDefinitionServiceImpl implements IBpmDefinitionService {

    private final RepositoryService repositoryService;

    private final HistoryService historyService;

    private final RuntimeService runtimeService;

    private final IActReDeploymentService actReDeploymentService;

    /**
     * 部署
     *
     * @param xmlStringForm 流程设计参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<String> deploy(BpmDesignXmlStringForm xmlStringForm) {
        String username = BreezeStpUtil.getUser().getUsername();
        Long tenantId = BreezeStpUtil.getUser().getTenantId();
        try {
            Authentication.setAuthenticatedUserId(username);
            // 构造文件名
            String xmlName = this.generateXmlName(xmlStringForm);
            Deployment deploy = this.repositoryService.createDeployment()
                    .addString(xmlName, xmlStringForm.getXml())
                    .name(xmlStringForm.getProcDefName())
                    .key(xmlStringForm.getProcDefKey())
                    .tenantId(String.valueOf(tenantId))
                    .category(xmlStringForm.getCategoryCode())
                    .deploy();

            return Result.ok(deploy.getId(), "发布成功");
        } catch (Exception e) {
            log.error("流程部署失败, 用户ID:{} ", username, e);
            return Result.fail("发布失败：" + e.getMessage());
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    /**
     * 构造XML文件名
     *
     * @param stringForm 流程设计参数
     * @return 文件名
     */
    private String generateXmlName(BpmDesignXmlStringForm stringForm) {
        return stringForm.getProcDefName() + "-" + stringForm.getCategoryCode() + "-" + stringForm.getProcDefKey() + ".bpmn20.xml";
    }

    @Override
    public Result<String> deploy(BpmDesignXmlFileForm xmlFileForm) {
        try {
            String username = BreezeStpUtil.getUser().getUsername();
            Long tenantId = BreezeStpUtil.getUser().getTenantId();
            Authentication.setAuthenticatedUserId(username);
            // 生成文件名的逻辑
            String xmlName = this.generateXmlName(xmlFileForm);
            // @formatter:off
            Deployment deploy = this.repositoryService.createDeployment()
                    .addInputStream(xmlName, getInputStream(xmlFileForm))
                    .name(xmlFileForm.getProcDefName())
                    .key(xmlFileForm.getProcDefKey())
                    .tenantId(String.valueOf(tenantId))
                    .category(xmlFileForm.getCategoryCode())
                    .deploy();
            // @formatter:on
            log.info("流程定义成功部署，部署ID: {}", deploy.getId());
            return Result.ok(deploy.getId());
        } catch (Exception e) {
            log.error("流程定义部署失败", e);
            return Result.fail("流程定义部署失败：" + e.getMessage());
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    /**
     * 列表页面
     *
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    @Override
    public Page<BpmDefinitionVO> listPage(BpmDefinitionQuery bpmDefinitionQuery) {
        return this.actReDeploymentService.listPage(bpmDefinitionQuery);
    }

    @Override
    public BpmDefinitionVO getInfo(String procDefId) {
        BpmDefinitionVO info = this.actReDeploymentService.getInfo(procDefId);
        info.setXml(this.getXmlStr(info.getProcDefKey()));
        return info;
    }

    /**
     * 挂起/激活
     *
     * @param procDefId 流程定义ID
     * @return {@link Boolean}
     */
    @Override
    public Result<Boolean> suspendedDefinition(String procDefId) {
        Long tenantId = BreezeStpUtil.getUser().getTenantId();
        String username = BreezeStpUtil.getUser().getUsername();
        try {
            Authentication.setAuthenticatedUserId(username);
            // @formatter:off
            ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(procDefId)
                    .processDefinitionTenantId(String.valueOf(tenantId))
                    .singleResult();
            // @formatter:on

            if (Objects.isNull(processDefinition)) {
                log.error("未找到流程定义: {}", procDefId);
                throw new BreezeBizException(ResultCode.DEFINITION_NOT_FOUND);
            }

            log.info("状态: {}", processDefinition.isSuspended());
            // 处理挂起和激活
            if (processDefinition.isSuspended()) {
                // 被挂起 => 去激活
                this.repositoryService.activateProcessDefinitionById(procDefId, true, null);
                return Result.ok(Boolean.TRUE, "激活成功");
            } else {
                // 激活状态 => 去挂起
                this.repositoryService.suspendProcessDefinitionById(procDefId, true, null);
                return Result.ok(Boolean.TRUE, "挂起成功");
            }
        } catch (Exception e) {
            log.error("处理流程定义状态时发生错误", e);
            throw new BreezeBizException(ResultCode.SYSTEM_EXCEPTION);
        } finally {
            // 最终使用一个ThreadLocal类型的变量进行存储，也就是与当前的线程绑定，所以流程实例启动完毕之后，需要设置为null，防止多线程的时候出问题。
            Authentication.setAuthenticatedUserId(null);
        }
    }

    /**
     * 版本列表页面
     *
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Page}<{@link BpmDefinitionVO}>
     */
    @Override
    public Page<BpmDefinitionVO> listVersionPage(BpmDefinitionQuery bpmDefinitionQuery) {
        // @formatter:off
        List<ProcessDefinition> processDefinitionList = this.repositoryService.createProcessDefinitionQuery()
                .listPage(bpmDefinitionQuery.getOffset(), bpmDefinitionQuery.getLimit());
        // @formatter:on
        BpmDefinitionVO flowDefinitionVO = new BpmDefinitionVO();
        Page<BpmDefinitionVO> page = new Page<>();
        page.setRecords(null);
        return page;
    }

    /**
     * 获取流程定义png
     *
     * @param procInstId 流程实例ID
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public String getBpmDefinitionPng(String procInstId) {
        // @formatter:off
        String procDefId;
        try {
            // 查询流程实例
            ProcessInstance procInst = this.runtimeService.createProcessInstanceQuery()
                    .processInstanceId(procInstId)
                    .singleResult();
            if (Objects.nonNull(procInst)) {
                procDefId = procInst.getProcessDefinitionId();
            } else {
                // 实例不存在就去查历史
                HistoricProcessInstance hisProcInst = this.historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(procInstId)
                        .singleResult();
                if (Objects.isNull(hisProcInst)) {
                    // 历史也不存在就抛出异常
                    throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
                }
                procDefId = hisProcInst.getProcessDefinitionId();
            }

            DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
            List<String> highLightedActivityList = new ArrayList<>();
            List<String> highLightedFlows = new ArrayList<>();
            // 历史activityId
            List<HistoricActivityInstance> hisActiInstList = this.historyService.createHistoricActivityInstanceQuery().processInstanceId(procInstId).list();
            hisActiInstList.forEach(hisActiInst -> {
                if ("sequenceFlow".equals(hisActiInst.getActivityType())) {
                    // 线条上色
                    highLightedFlows.add(hisActiInst.getActivityId());
                } else {
                    highLightedActivityList.add(hisActiInst.getActivityId());
                }
            });
            // 生成图片
            BpmnModel bpmnModel = this.repositoryService.getBpmnModel(procDefId);
            try (InputStream inputStream = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
                    "png",
                    highLightedActivityList,
                    highLightedFlows,
                    "宋体", "微软雅黑", "宋体", null, 1.0d, true)) {
                return "data:image/png;base64," + Base64.encode(inputStream);
            }
        } catch (Exception e) {
            log.error("获取流程图片失败", e);
        }
        // @formatter:on
        return "";
    }

    /**
     * 获得版本流程定义xml
     *
     * @param procInstId 流程实例ID
     * @return {@link XmlVO }
     */
    @Override
    public XmlVO getBpmDefinitionXml(String procInstId) {
        HistoricProcessInstance historicProcessInstance = this.historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(procInstId)
                .singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
        }

        return this.getXmlVO(procInstId, historicProcessInstance);
    }

    private XmlVO getXmlVO(String procInstId, HistoricProcessInstance hiscProcInst) {
        // 获取流程定义的BPMN模型
        BpmnModel bpmnModel;
        try {
            bpmnModel = this.repositoryService.getBpmnModel(hiscProcInst.getProcessDefinitionId());
            if (bpmnModel == null) {
                throw new BreezeBizException(ResultCode.BPM_MODEL_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new BreezeBizException(ResultCode.BPM_MODEL_NOT_FOUND);
        }

        // 查询流程实例的所有历史活动实例
        List<String> activityInstTask = this.historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(procInstId)
                .list()
                .stream()
                .filter(s -> !StrUtil.equals(s.getActivityType(), "sequenceFlow"))
                .map(HistoricActivityInstance::getActivityId)
                .toList();

        // 查询当前活动节点
        Set<String> currentTaskIdSet = this.historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(procInstId)
                .unfinished()
                .list()
                .stream()
                .map(HistoricActivityInstance::getActivityId)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        XmlVO xmlVO = new XmlVO();
        xmlVO.setFinishedNode(new LinkedHashSet<>(activityInstTask));
        xmlVO.setCurrentTaskNode(currentTaskIdSet);
        xmlVO.setXmlStr(this.getXmlStr(hiscProcInst.getProcessDefinitionKey()));

        return xmlVO;
    }

    private String getXmlStr(String definitionKey) {
        // @formatter:off
        Long tenantId = BreezeStpUtil.getUser().getTenantId();
        ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(definitionKey)
                .processDefinitionTenantId(String.valueOf(tenantId))
                .latestVersion()
                .singleResult();
        // @formatter:on
        try (InputStream resourceAsStream = this.repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName())) {
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("未获取到XML", e);
            throw new BreezeBizException(ResultCode.XML_NOT_FOUND);
        }
    }

    /**
     * 删除
     *
     * @param deleteForms 流定义删除参数列表
     * @return {@link Boolean}
     */
    @Override
    public Boolean delete(List<BpmDefinitionDeleteForm> deleteForms) {
        // @formatter:off
        for (BpmDefinitionDeleteForm deleteForm : deleteForms) {
            List<ProcessDefinition> definitionList = this.repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(deleteForm.getProcDefKey())
                    .list();
            for (ProcessDefinition processDefinition : definitionList) {
                this.repositoryService.deleteDeployment(processDefinition.getDeploymentId(), deleteForm.getCascade());
            }
        }
        // @formatter:on
        return Boolean.TRUE;
    }

    @SneakyThrows
    private InputStream getInputStream(BpmDesignXmlFileForm bpmDesignXmlFileForm) {
        // 使用try-with-resources确保资源释放，即使在异常情况下
        try (InputStream inputStream = bpmDesignXmlFileForm.getXmlFile().getInputStream()) {
            return inputStream;
        }
    }

    private String generateXmlName(BpmDesignXmlFileForm xmlFileForm) {
        return xmlFileForm.getProcDefName() + "-" + xmlFileForm.getCategoryCode() + "-" + xmlFileForm.getProcDefKey() + ".bpmn20.xml";
    }
}
