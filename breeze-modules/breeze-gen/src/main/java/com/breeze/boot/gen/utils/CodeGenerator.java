/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.gen.utils;

import com.breeze.boot.gen.domain.bo.TableBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class CodeGenerator {

    private static final String BACKEND_TEMPLATE_DIR = "templates/backend/";
    private static final String XML_TEMPLATE_DIR = "templates/resources/";
    private static final String FRONTEND_TEMPLATE_DIR = "templates/frontend/";
    private static final String SQL_TEMPLATE_DIR = "templates/sql/";

    private final VelocityEngine velocityEngine;

    public CodeGenerator() {
        velocityEngine = new VelocityEngine();
        // 设置类路径加载器（模板应放在resources/templates目录）
        velocityEngine.setProperty("resource.loader", "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("classpath.resource.loader.path", "/templates");
        velocityEngine.init();
    }

    public byte[] generateCodeZip(TableBO tableInfo) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(bos, StandardCharsets.UTF_8)) {

            generateFiles(tableInfo, zipOut, new String[][]{
                    {"Controller.java.vm", "controller"},
                    {"Service.java.vm", "service"},
                    {"ServiceImpl.java.vm", "service/impl"},
                    {"Mapper.java.vm", "mapper"},
                    {"Mapper.xml.vm", "resources/mapper"},
                    {"Converter.java.vm", "model/converter"},
                    {"Entity.java.vm", "model/entity"},
                    {"Form.java.vm", "model/form"},
                    {"Query.java.vm", "model/query"},
                    {"VO.java.vm", "model/vo"},
                    {"en.ts.vm", "src/" + tableInfo.getEntityLowerName() + "/i18n"},
                    {"zh-cn.ts.vm", "src/" + tableInfo.getEntityLowerName() + "/i18n"},
                    {"type.ts.vm", "src/api/" + tableInfo.getEntityLowerName()},
                    {"index.ts.vm", "src/api/" + tableInfo.getEntityLowerName()},
                    {"AddOrEdit.vue.vm", "src/" + tableInfo.getEntityLowerName() + "/components"},
                    {"index.vue.vm", "src/" + tableInfo.getEntityLowerName()},
                    {"menu.sql.vm", "sql/"},
            });

            zipOut.finish();
            log.info("生成代码压缩包成功");
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("生成代码压缩包失败", e);
            throw new RuntimeException("生成代码压缩包失败", e);
        }
    }

    private void generateFiles(TableBO tableInfo, ZipOutputStream zipOut, String[][] fileConfigs) throws IOException {
        for (String[] config : fileConfigs) {
            String templateName = config[0];
            String targetDir = config[1];
            if (templateName.startsWith("menu.sql")) {
                log.info("生成{}菜单SQL文件 {} {}", tableInfo.getTableName(), templateName, targetDir);
                generateSqlFile(tableInfo, templateName, targetDir, zipOut);
            } else if (templateName.endsWith(".vue.vm") || templateName.endsWith(".ts.vm")) {
                log.info("生成{}前端文件 {} {}", tableInfo.getTableName(), templateName, targetDir);
                generateFrontEndFile(tableInfo, templateName, targetDir, zipOut);
            } else if (templateName.contains("xml")) {
                log.info("生成{}XML文件 {} {}", tableInfo.getTableName(), templateName, targetDir);
                generateXmlFile(tableInfo, templateName, targetDir, zipOut);
            } else if (templateName.contains("java")) {
                log.info("生成{}后端文件 {} {}", tableInfo.getTableName(), templateName, targetDir);
                generateBackEndFile(tableInfo, templateName, targetDir, zipOut);
            }
        }
    }

    private void generateXmlFile(TableBO tableInfo, String templateName, String targetDir, ZipOutputStream zipOut) throws IOException {
        String templatePath = XML_TEMPLATE_DIR + templateName;
        generateFile(tableInfo, templatePath, targetDir, zipOut);
    }

    private void generateFile(TableBO tableInfo, String templatePath, String targetDir, ZipOutputStream zipOut) throws IOException {
        Template template = velocityEngine.getTemplate(templatePath, StandardCharsets.UTF_8.name());
        VelocityContext context = createVelocityContext(tableInfo);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String output = writer.toString();

        String filePath = getFilePath(tableInfo, targetDir, templatePath);

        ZipEntry zipEntry = new ZipEntry(filePath);
        zipOut.putNextEntry(zipEntry);
        zipOut.write(output.getBytes(StandardCharsets.UTF_8));
        zipOut.closeEntry();
    }

    private VelocityContext createVelocityContext(TableBO tableInfo) {
        VelocityContext context = new VelocityContext();
        // 添加模板需要的基础变量
        context.put("tableName", tableInfo.getTableName());
        context.put("columns", tableInfo.getColumns());
        context.put("packageName", tableInfo.getPackageName());
        context.put("date", LocalDate.now());
        context.put("module", tableInfo.getModule());
        context.put("entityClassName", tableInfo.getEntityClassName());
        context.put("entityClassLowerName", tableInfo.getEntityLowerName());
        context.put("entityClassNameComment", tableInfo.getEntityClassNameComment());
        context.put("entityClassNameUpper", tableInfo.getEntityClassNameUpper());
        context.put("rootId", tableInfo.getRootId());
        context.put("infoId", tableInfo.getInfoId());
        context.put("editId", tableInfo.getEditId());
        context.put("addId", tableInfo.getAddId());
        context.put("delId", tableInfo.getDelId());
        context.put("year", tableInfo.getYear());
        return context;
    }

    private String getFilePath(TableBO tableInfo, String targetDir, String templatePath) {
        String templateName = templatePath.substring(templatePath.lastIndexOf("/") + 1);
        String fileName = getFileName(tableInfo, templateName);
        if (templatePath.startsWith(BACKEND_TEMPLATE_DIR)) {
            String packagePath = tableInfo.getPackageName().replace(".", "/") + "/" + tableInfo.getModule();
            return packagePath + "/" + targetDir + "/" + fileName;
        }
        return targetDir + "/" + fileName;
    }

    private void generateBackEndFile(TableBO tableInfo, String templateName, String targetDir, ZipOutputStream zipOut) throws IOException {
        String templatePath = BACKEND_TEMPLATE_DIR + templateName;
        generateFile(tableInfo, templatePath, targetDir, zipOut);
    }

    private void generateFrontEndFile(TableBO tableInfo, String templateName, String targetDir, ZipOutputStream zipOut) throws IOException {
        String templatePath = FRONTEND_TEMPLATE_DIR + templateName;
        generateFile(tableInfo, templatePath, targetDir, zipOut);
    }

    private void generateSqlFile(TableBO tableInfo, String templateName, String targetDir, ZipOutputStream zipOut) throws IOException {
        String templatePath = SQL_TEMPLATE_DIR + templateName;
        generateFile(tableInfo, templatePath, targetDir, zipOut);
    }

    private String getFileName(TableBO tableInfo, String templateName) {
        Map<String, String> nameMappings = new HashMap<>();
        nameMappings.put("Controller", tableInfo.getEntityClassName() + "Controller.java");
        nameMappings.put("Service", tableInfo.getEntityClassName() + "Service.java");
        nameMappings.put("ServiceImpl", tableInfo.getEntityClassName() + "ServiceImpl.java");
        nameMappings.put("VO", tableInfo.getEntityClassName() + "VO.java");
        nameMappings.put("Converter", tableInfo.getEntityClassName() + "Converter.java");
        nameMappings.put("Form", tableInfo.getEntityClassName() + "Form.java");
        nameMappings.put("Query", tableInfo.getEntityClassName() + "Query.java");
        nameMappings.put("Entity", tableInfo.getEntityClassName() + ".java");
        nameMappings.put("Mapper.java", tableInfo.getEntityClassName() + "Mapper.java");
        nameMappings.put("zh-cn.ts", "zh-cn.ts");
        nameMappings.put("en.ts", "en.ts");
        nameMappings.put("type.ts", "type.ts");
        nameMappings.put("index.ts", "index.ts");
        nameMappings.put("AddOrEdit.vue", tableInfo.getEntityClassName() + "AddOrEdit.vue");
        nameMappings.put("index.vue", "index.vue");
        nameMappings.put("menu.sql", tableInfo.getEntityClassName() + "Menu.sql");
        nameMappings.put("Mapper.xml", tableInfo.getEntityClassName() + "Mapper.xml");

        for (Map.Entry<String, String> entry : nameMappings.entrySet()) {
            if (templateName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        throw new IllegalArgumentException("Unknown template name: " + templateName);
    }
}