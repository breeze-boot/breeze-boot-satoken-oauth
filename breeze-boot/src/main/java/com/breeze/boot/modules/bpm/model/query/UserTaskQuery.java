package com.breeze.boot.modules.bpm.model.query;

import com.breeze.boot.core.base.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户任务查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserTaskQuery extends PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 流程定义key
     */
    private String procDefKey;

    /**
     * 是否签收
     */
    private Boolean isAssigned = Boolean.FALSE;

}
