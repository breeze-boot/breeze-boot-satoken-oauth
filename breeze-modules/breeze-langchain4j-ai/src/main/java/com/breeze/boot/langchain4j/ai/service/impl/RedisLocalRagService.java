package com.breeze.boot.langchain4j.ai.service.impl;

import com.breeze.boot.langchain4j.ai.service.IRagService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RedisLocalRagService implements IRagService {
    @Override
    public void importDoc(List<MultipartFile> files) {

    }
}
