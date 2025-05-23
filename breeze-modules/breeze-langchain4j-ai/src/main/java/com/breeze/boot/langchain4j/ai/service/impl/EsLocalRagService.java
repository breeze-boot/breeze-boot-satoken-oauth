package com.breeze.boot.langchain4j.ai.service.impl;

import com.breeze.boot.langchain4j.ai.service.IRagService;
import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EsLocalRagService implements IRagService {

    private final ElasticsearchEmbeddingStore esEmbeddingStore;
    private final EmbeddingModel qwenEmbeddingModel;
    private final QwenTokenizer qwenTokenizer;

    /**
     * 重写importDoc方法，用于导入多个文档文件
     * 该方法负责处理多部分文件，将它们保存到临时位置，分割文档，并将文档内容加载到系统中
     *
     * @param files 多个要导入的文档文件，封装为MultipartFile列表
     */
    @Override
    public void importDoc(List<MultipartFile> files) {
        // 初始化文档列表和临时文件列表
        List<Document> documents = new ArrayList<>();
        List<TextSegment> segmentList = new ArrayList<>();
        List<File> tempFiles = new ArrayList<>();

        try {
            // 遍历每个MultipartFile，将其保存到临时文件
            for (MultipartFile file : files) {
                File tempFile = saveMultipartFileToTemp(file);
                tempFiles.add(tempFile);
            }
            DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
            // 加载每个临时文件为文档对象
            for (File tempFile : tempFiles) {
                Document document = loadDocument(tempFile);
                documents.add(document);
                // 输出文档的元数据信息
                log.info("Loaded document: " + document.metadata());
                List<TextSegment> segments = splitter.split(document);
                segmentList.addAll(segments);
            }

            for (TextSegment textSegment : segmentList) {
                try {
                    Embedding embedding = qwenEmbeddingModel.embed(textSegment).content();
                    esEmbeddingStore.add(embedding, textSegment);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            // 捕获并记录文档加载过程中的IO错误
            log.error("Error loading document", e);
        } finally {
            // 清理创建的所有临时文件
            for (File tempFile : tempFiles) {
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
    }

    private Document loadDocument(File tempFile) throws IOException {
        String filename = tempFile.getName();
        if (filename.endsWith(".pdf")) {
            return FileSystemDocumentLoader.loadDocument(
                    tempFile.getAbsolutePath(),
                    new ApachePdfBoxDocumentParser()
            );
        } else if (filename.endsWith(".docx")) {
            return FileSystemDocumentLoader.loadDocument(
                    tempFile.getAbsolutePath(),
                    new ApacheTikaDocumentParser()
            );
        } else {
            return FileSystemDocumentLoader.loadDocument(
                    tempFile.getAbsolutePath(),
                    new ApacheTikaDocumentParser()
            );
        }
    }

    private File saveMultipartFileToTemp(MultipartFile multipartFile) throws IOException {
        // 获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        // 确保原始文件名不为空
        if (originalFilename == null) {
            throw new IllegalArgumentException("Original filename is null");
        }
        // 获取文件扩展名
        String extension = getFileExtension(originalFilename);
        // 确保临时目录存在
        Path tempDir = Paths.get("/var/temp");
        if (!Files.exists(tempDir)) {
            Files.createDirectories(tempDir);
        }
        // 创建临时文件
        Path tempFilePath = Files.createTempFile(tempDir, originalFilename + "_temp", extension);
        File tempFile = tempFilePath.toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return lastIndex != -1 ? filename.substring(lastIndex) : "";
    }
}
