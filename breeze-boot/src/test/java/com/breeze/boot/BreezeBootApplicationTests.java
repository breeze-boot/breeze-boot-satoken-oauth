package com.breeze.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BreezeBootApplicationTests {


    @Autowired
    VectorStore vectorStore;

    @Test
    public void test() {

    }

}