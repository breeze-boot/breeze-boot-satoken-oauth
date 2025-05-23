package com.breeze.boot.auth.model.bo;

import com.breeze.boot.auth.model.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowUserBO {

    /**
     * 用户编ID
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名称")
    private String username;


    /**
     * 登录账户名称
     */
    @Schema(description = "登录账户名称")
    private String displayName;
    private String email;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    private List<SysRole> roleList;

}
