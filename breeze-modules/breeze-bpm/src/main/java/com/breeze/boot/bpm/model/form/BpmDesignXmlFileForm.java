package com.breeze.boot.bpm.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BpmDesignXmlFileForm {

    @Schema(description = "流程部署Key")
    @NotBlank(message = "流程部署Key不能为空")
    private String procDefKey;

    @Schema(description = "流程部署名称")
    @NotBlank(message = "流程部署名称不能为空")
    private String procDefName;

    @Schema(description = "流程部署分类")
    @NotBlank(message = "流程部署分类不能为空")
    private String categoryCode;

    @Schema(description = "流程部署xml")
    @NotNull(message = "xml不能为空")
    private MultipartFile xmlFile;

}