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

package com.breeze.boot.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.enums.ContentType;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.local.operation.LocalStorageTemplate;
import com.breeze.boot.modules.system.mapper.SysFileMapper;
import com.breeze.boot.modules.system.model.FileInfo;
import com.breeze.boot.modules.system.model.entity.SysFile;
import com.breeze.boot.modules.system.model.form.FileBizForm;
import com.breeze.boot.modules.system.model.query.FileQuery;
import com.breeze.boot.modules.system.service.SysFileService;
import com.breeze.boot.oss.operation.MinioOssTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.breeze.boot.core.constants.StorageConstants.SYSTEM_BUCKET_NAME;
import static com.breeze.boot.core.enums.ResultCode.FILE_NOT_FOUND;
import static com.breeze.boot.modules.system.enums.FileEnum.StoreTypeEnum.LOCAL;
import static com.breeze.boot.modules.system.enums.FileEnum.StoreTypeEnum.MINIO;

/**
 * 系统文件服务impl
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    /**
     * oss存储服务
     */
    private final MinioOssTemplate ossTemplate;

    /**
     * 本地存储模板
     */
    private final LocalStorageTemplate localStorageTemplate;


    /**
     * 列表页面
     *
     * @param query 文件查询
     * @return {@link Page}<{@link SysFile}>
     */
    @Override
    public Page<SysFile> listPage(FileQuery query) {
        Page<SysFile> logEntityPage = new Page<>(query.getCurrent(), query.getSize());
        return new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .like(StrUtil.isAllNotBlank(query.getName()), SysFile::getName, query.getName())
                .like(StrUtil.isAllNotBlank(query.getBizType()), SysFile::getBizType, query.getBizType())
                .like(StrUtil.isAllNotBlank(query.getCreateName()), SysFile::getCreateName, query.getCreateName())
                .page(logEntityPage);
    }

    @Override
    public Boolean updateFileById(Long fileId, FileBizForm form) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.nonNull(sysFile)) {
            sysFile.setBizId(form.getBizId());
            sysFile.updateById();
        }
        return Boolean.FALSE;
    }

    /**
     * 将文件上传至Minio S3存储服务，并保存文件信息到数据库。
     *
     * @param bizType  业务类型
     * @param request  HTTP请求对象，用于获取上传文件的MIME类型信息。
     * @param response HTTP响应对象，本次方法调用中未使用。
     * @return {@link Result }<{@link FileInfo }>
     */
    @SneakyThrows
    @Override
    public Result<FileInfo> uploadMinioS3(String bizType, MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        LocalDate now = LocalDate.now();

        // 获取并验证文件原始名称
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "文件名不能为空");

        // 构建基于日期和UUID的独特文件存储路径
        String objectName = "/" + now.getYear() + now.getMonthOfYear() + now.getDayOfMonth() + IdUtil.simpleUUID() + "/" + originalFilename;

        // 创建存储桶（如果尚未存在）
        this.ossTemplate.createBucket(SYSTEM_BUCKET_NAME);

        // 将文件上传至Minio S3
        this.ossTemplate.putObject(
                SYSTEM_BUCKET_NAME,
                objectName,
                file.getInputStream(),
                ContentType.getContentType(originalFilename)
        );

        // 构建SysFile实体以记录文件信息至数据库
        SysFile sysFile = SysFile.builder()
                .bucket(SYSTEM_BUCKET_NAME)
                .name(originalFilename)
                .objectName(SYSTEM_BUCKET_NAME + objectName)
                .uri(SYSTEM_BUCKET_NAME + objectName)
                .bizType(bizType)
                .format(extractFileFormat(Objects.requireNonNull(originalFilename)))
                .contentType(request.getContentType())
                .storeType(MINIO.getValue())
                .build();
        this.save(sysFile);

        FileInfo fileInfo = new FileInfo();
        String url = this.ossTemplate.previewImg(sysFile.getUri(), sysFile.getBucket());
        fileInfo.setUrl(sysFile.getObjectName());
        fileInfo.setName(sysFile.getName());
        log.info("上传文件路径：{}", url);
        return Result.ok(fileInfo);
    }

    /**
     * 将本地存储的文件上传到服务器，并保存相关文件信息至数据库。
     *
     * @param bizType  业务类型
     * @param request  HTTP请求对象，用于获取上传文件的MIME类型信息。
     * @param response HTTP响应对象，本次方法调用中未使用。
     * @return {@link Result }<{@link FileInfo }>
     */
    @Override
    public Result<FileInfo> uploadLocalStorage(String bizType, MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        // 获取上传文件的原始名称
        String originalFilename = file.getOriginalFilename();
        // 确保文件名不为空
        Assert.notNull(originalFilename, "文件名不能为空");
        // 生成基于当前日期和UUID的独特文件存储路径
        LocalDate now = LocalDate.now();
        String objectName = "/" + now.getYear() + now.getMonthOfYear() + now.getDayOfMonth() + IdUtil.simpleUUID() + "/" + originalFilename;

        // 执行文件上传至本地存储并获取服务器上的存储路径
        String path = this.localStorageTemplate.uploadFile(file, objectName, originalFilename);
        log.debug("上传文件路径：{}", path);

        // 创建SysFile实体以记录文件信息到数据库
        SysFile sysFile = SysFile.builder()
                .name(originalFilename)
                .objectName(SYSTEM_BUCKET_NAME + objectName)
                .uri(SYSTEM_BUCKET_NAME + objectName)
                .bizType(bizType)
                .format(extractFileFormat(Objects.requireNonNull(originalFilename)))
                .contentType(request.getContentType())
                .bucket(SYSTEM_BUCKET_NAME)
                .storeType(LOCAL.getValue())
                .build();
        this.save(sysFile);

        FileInfo fileInfo = new FileInfo();
        String url = this.ossTemplate.previewImg(sysFile.getUri(), sysFile.getBucket());
        fileInfo.setUrl(sysFile.getObjectName());
        fileInfo.setName(sysFile.getName());
        log.info("上传文件路径：{}", url);
        return Result.ok(fileInfo);
    }

    /**
     * 提取文件扩展名作为格式信息
     *
     * @param originalFilename 文件名
     * @return {@link String} 扩展名
     */
    private String extractFileFormat(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }


    /**
     * 预览
     *
     * @param fileId 文件ID
     * @return {@link Result}<{@link Boolean}>
     */
    @SneakyThrows
    @Override
    public String preview(Long fileId) {
        SysFile sysFile = this.getById(fileId);
        if (Objects.isNull(sysFile)) {
            // TODO 缺省图
            return "";
        }
        return this.ossTemplate.getObjectURL(SYSTEM_BUCKET_NAME, sysFile.getUri(), 2);
    }

    /**
     * 下载
     *
     * @param fileId   文件ID
     * @param response 响应
     */
    @Override
    public void download(Long fileId, HttpServletResponse response) {
        SysFile sysFile = this.getById(fileId);
        AssertUtil.isNotNull(sysFile, FILE_NOT_FOUND);
        this.ossTemplate.downloadObject(SYSTEM_BUCKET_NAME, sysFile.getUri(), sysFile.getName(), response);
    }

    /**
     * 通过id删除文件
     *
     * @param fileIds 文件IDS
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeFileByIds(List<Long> fileIds) {
        List<SysFile> sysFileList = this.listByIds(fileIds);
        AssertUtil.isTrue(CollUtil.isNotEmpty(sysFileList), FILE_NOT_FOUND);
        for (SysFile sysFile : sysFileList) {
            this.ossTemplate.removeObject(SYSTEM_BUCKET_NAME, sysFile.getUri());
            this.removeById(sysFile.getId());
        }
        return Result.ok(Boolean.TRUE, "删除成功");
    }


}
