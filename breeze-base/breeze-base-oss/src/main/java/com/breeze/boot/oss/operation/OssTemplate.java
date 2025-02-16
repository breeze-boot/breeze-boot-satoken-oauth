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

package com.breeze.boot.oss.operation;

import cn.hutool.core.io.IoUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * 操作系统模板
 *
 * @author gaoweixuan
 * @since 2023-04-18
 */
@Setter
@Slf4j
public class OssTemplate implements OssOperations {

    /**
     * amazon s3
     */
    private AmazonS3 amazonS3;

    /**
     * 创建桶
     *
     * @param bucketName bucket名称
     */
    @Override
    public void createBucket(String bucketName) {
        if (this.amazonS3.doesBucketExistV2(bucketName)) {
            log.debug("桶已存在无需创建：{}", bucketName);
            return;
        }
        this.amazonS3.createBucket(bucketName);
        log.info("成功创建桶：{}", bucketName);
    }

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     */
    @Override
    public void removeBucket(String bucketName) {
        if (!this.amazonS3.doesBucketExistV2(bucketName)) {
            log.debug("桶不存在无需删除：{}", bucketName);
            return;
        }
        this.amazonS3.deleteBucket(bucketName);
        log.info("成功删除桶：{}", bucketName);
    }

    /**
     * 列出所有桶
     *
     * @return {@link List}<{@link Bucket}>
     */
    @Override
    public List<Bucket> listAllBuckets() {
        return this.amazonS3.listBuckets();
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名称
     * @param stream      流
     * @param contentType 内容类型
     * @return {@link PutObjectResult}
     */
    @Override
    @SneakyThrows
    public PutObjectResult putObject(String bucketName, String objectName, InputStream stream, String contentType) {
        long size = stream.available();
        return this.putObjectInternal(bucketName, objectName, stream, size, contentType);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param file       文件
     */
    @Override
    public void putObject(String bucketName, String objectName, File file) {
        try (InputStream stream = new FileInputStream(file)) {
            long size = file.length();
            putObjectInternal(bucketName, objectName, stream, size, "application/octet-stream");
        } catch (IOException e) {
            log.error("上传文件时发生IO异常：{}", e.getMessage(), e);
        }
    }

    /**
     * 内部上传文件方法
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名称
     * @param stream      流
     * @param size        文件大小
     * @param contentType 内容类型
     * @return {@link PutObjectResult}
     */
    private PutObjectResult putObjectInternal(String bucketName, String objectName, InputStream stream, long size, String contentType) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);

        try {
            return this.amazonS3.putObject(bucketName, objectName, stream, objectMetadata);
        } catch (Exception e) {
            log.error("上传文件失败，桶名：{}，对象名：{}，错误信息：{}", bucketName, objectName, e.getMessage(), e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    /**
     * 得到对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @return {@link S3Object}
     */
    @Override
    public S3Object getObject(String bucketName, String objectName) {
        try {
            return this.amazonS3.getObject(bucketName, objectName);
        } catch (Exception e) {
            log.error("获取对象失败，桶名：{}，对象名：{}，错误信息：{}", bucketName, objectName, e.getMessage(), e);
            throw new RuntimeException("获取对象失败", e);
        }
    }

    /**
     * 获取对象url
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param expires    到期时间（天）
     * @return {@link String}
     */
    @Override
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        LocalDateTime now = LocalDateTime.now().plusDays(expires);
        ZonedDateTime zdt = now.atZone(ZoneId.systemDefault());
        Date expirationDate = Date.from(zdt.toInstant());
        try {
            URL url = amazonS3.generatePresignedUrl(bucketName, objectName, expirationDate);
            return url.toString();
        } catch (Exception e) {
            log.error("获取对象URL失败，桶名：{}，对象名：{}，错误信息：{}", bucketName, objectName, e.getMessage(), e);
            throw new RuntimeException("获取对象URL失败", e);
        }
    }

    /**
     * 删除指定存储桶中的对象
     *
     * @param bucketName 指定要从中删除对象的存储桶的名称
     * @param objectName 指定要删除的对象的名称
     */
    @Override
    public void removeObject(String bucketName, String objectName) {
        try {
            this.amazonS3.deleteObject(bucketName, objectName);
            log.info("成功删除对象，桶名：{}，对象名：{}", bucketName, objectName);
        } catch (Exception e) {
            log.error("删除对象失败，桶名：{}，对象名：{}，错误信息：{}", bucketName, objectName, e.getMessage(), e);
            throw new RuntimeException("删除对象失败", e);
        }
    }

    /**
     * 从指定存储桶下载对象，并将其作为附件发送到客户端
     *
     * @param bucketName 对象所在的S3存储桶名称
     * @param objectName 需要下载的对象在存储桶中的名称
     * @param fileName   下载文件的原始名称，将用于生成HTTP响应中Content-disposition头信息
     * @param response   HTTP响应对象，通过此对象将对象内容输出至客户端，触发浏览器下载操作
     */
    @Override
    public void downloadObject(String bucketName, String objectName, String fileName, HttpServletResponse response) {
        try (S3Object s3Object = this.amazonS3.getObject(bucketName, objectName)) {
            setResponseHeaders(response, fileName);
            IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
            log.info("成功下载对象，桶名：{}，对象名：{}", bucketName, objectName);
        } catch (Exception e) {
            log.error("下载对象失败，桶名：{}，对象名：{}，错误信息：{}", bucketName, objectName, e.getMessage(), e);
        }
    }

    /**
     * 设置响应头信息
     *
     * @param response HTTP响应对象
     * @param fileName 文件名
     */
    private void setResponseHeaders(HttpServletResponse response, String fileName) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/octet-stream; charset=UTF-8");
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        String safeFileName = encodeSafeFileName(encodedFileName);
        response.setHeader("Content-disposition", "attachment;filename=" + safeFileName);
    }

    /**
     * 编码文件名，移除不安全的字符，确保文件名在不同操作系统和浏览器下都能正确处理。
     *
     * @param fileName 待编码的原始文件名
     * @return 安全的、经过编码处理的文件名
     */
    private String encodeSafeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.\\-_]", "_");
    }
}