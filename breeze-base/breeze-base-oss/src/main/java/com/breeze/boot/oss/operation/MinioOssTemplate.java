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

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.breeze.boot.oss.config.OssProperties;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * 操作系统模板
 *
 * @author gaoweixuan
 * @since 2023-04-18
 */
@Slf4j
public class MinioOssTemplate extends OssTemplate implements InitializingBean, EnvironmentAware {

    /**
     * 存储地址
     */
    private final String resourceName;
    private OssProperties ossProperties;

    public MinioOssTemplate() {
        this.resourceName = "minio";
    }

    /**
     * 初始化OSS客户端配置。
     * 此方法在bean的属性设置完成后调用，用于配置并初始化OSS客户端。
     * 该方法不接受参数，也不返回任何值。
     */
    @Override
    public void afterPropertiesSet() {
        // 从配置中获取指定资源名的OSS配置实体
        OssProperties.ConfigEntity configEntity = ossProperties.getS3().get(resourceName);

        // 配置客户端设置，例如最大连接数
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxConnections(configEntity.getMaxConnections());

        // 构建AWS终端节点配置
        AwsClientBuilder.EndpointConfiguration endpointConfiguration
                = new AwsClientBuilder.EndpointConfiguration(configEntity.getEndpoint(), configEntity.getRegion());

        // 创建AWS认证凭证
        AWSCredentials awsCredentials
                = new BasicAWSCredentials(configEntity.getAccessKey(), configEntity.getAccessKeySecret());

        // 创建静态凭证提供者
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        // 配置并构建AmazonS3客户端实例，包括终端节点配置、客户端配置、凭证提供者等
        super.setAmazonS3(AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding() // 禁用分块编码
                .withPathStyleAccessEnabled(configEntity.getPathStyleAccess()) // 启用路径样式访问
                .build());
    }

    /**
     * 设置环境配置，将环境变量中的OssProperties配置绑定到当前类的ossProperties属性上。
     * 这个方法通过读取环境变量中的特定前缀配置，来初始化或更新OssProperties实例。
     *
     * @param environment 应用的环境配置，不可为null，用于获取应用的配置属性。
     */
    @Override
    public void setEnvironment(Environment environment) {
        // 从OssProperties类上获取ConfigurationProperties注解，用于定义绑定的前缀
        ConfigurationProperties annotation = OssProperties.class.getAnnotation(ConfigurationProperties.class);
        // 使用Binder将环境变量中的配置绑定到OssProperties类上，并根据注解的前缀进行匹配
        BindResult<OssProperties> bindResult = Binder.get(environment).bind(annotation.prefix(), OssProperties.class);
        // 将绑定结果赋值给ossProperties属性，用于后续使用
        ossProperties = bindResult.get();
    }

    /**
     * 预览图片的函数。
     *
     * @param path   图片在存储桶中的相对路径。
     * @param bucket 存储桶的名称。
     * @return 返回图片的预览URL。
     */
    public String previewImg(String path, String bucket) {
        // 生成并返回图片的预览URL
        return this.ossProperties.getS3().get("minio").getDomainUrl() + bucket + '/' + path;
    }

}
