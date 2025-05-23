package com.breeze.boot.modules.wo.model.query;

import com.breeze.boot.core.model.PageQuery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 平台查询参数
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "请假工单查询参数")
public class WoLeaveQuery extends PageQuery {

    private String title;
    private String reason;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
