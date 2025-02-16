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

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * OSS操作
 *
 * @author gaoweixuan
 * @since 2023-04-18
 */
public interface OssOperations {

    /**
     * 创建桶
     *
     * @param bucketName bucket名称
     */
    void createBucket(String bucketName);

    /**
     * 删除桶
     *
     * @param bucketName bucket名称
     */
    void removeBucket(String bucketName);

    /**
     * 列出所有桶
     *
     * @return {@link List}<{@link Bucket}>
     */
    List<Bucket> listAllBuckets();

    /**
     * 上传对象
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名称
     * @param stream      文件流
     * @param contentType 类型
     * @return {@link PutObjectResult}
     */
    PutObjectResult putObject(String bucketName, String objectName, InputStream stream, String contentType);

    /**
     * 上传对象
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param file       文件
     */
    void putObject(String bucketName, String objectName, File file);

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return S3Object
     */
    S3Object getObject(String bucketName, String objectName);

    /**
     * 获取对象的url
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expires    到期
     * @return {@link String }
     */
    String getObjectURL(String bucketName, String objectName, Integer expires);

    /**
     * 使用bucketName objectName删除对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    void removeObject(String bucketName, String objectName);

    /**
     * 下载
     *
     * @param bucketName       存储桶名称
     * @param objectName       对象名称
     * @param response         响应
     * @param originalFilename 原始文件名
     */
    void downloadObject(String bucketName, String objectName, String response, HttpServletResponse originalFilename);

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 对象名称
     * @param stream     流
     * @return {@link PutObjectResult }
     */
    @SneakyThrows
    default PutObjectResult putObject(String bucketName, String objectName, InputStream stream) {
        return this.putObject(bucketName, objectName, stream, "application/octet-stream");
    }

}
