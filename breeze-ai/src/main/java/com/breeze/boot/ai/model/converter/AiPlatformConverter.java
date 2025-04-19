package com.breeze.boot.ai.model.converter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.ai.model.entity.AiPlatform;
import com.breeze.boot.ai.model.form.AiPlatformForm;
import com.breeze.boot.ai.model.vo.AiPlatformVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiPlatformConverter {
    Page<AiPlatformVO> page2VOPage(Page<AiPlatform> page);

    AiPlatformVO entity2VO(AiPlatform byId);

    AiPlatform form2Entity(AiPlatformForm form);

}
