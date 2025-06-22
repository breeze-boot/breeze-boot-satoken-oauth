DROP DATABASE IF EXISTS breeze_boot_17;
CREATE DATABASE breeze_boot_17 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE breeze_boot_17;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_bpm_category
-- ----------------------------
DROP TABLE IF EXISTS `act_bpm_category`;
CREATE TABLE `act_bpm_category`  (
  `id` bigint NOT NULL,
  `category_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程分类编码',
  `category_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程分类名称',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of act_bpm_category
-- ----------------------------

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log`  (
  `LOG_NR_` bigint NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DATA_` longblob NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`LOG_NR_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BYTES_` longblob NULL,
  `GENERATED_` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_BYTEARR_DEPL`(`DEPLOYMENT_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ge_bytearray_ibfk_1` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property`  (
  `NAME_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `VALUE_` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REV_` int NULL DEFAULT NULL,
  PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO `act_ge_property` VALUES ('batch.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('cfg.execution-related-entities-count', 'true', 1);
INSERT INTO `act_ge_property` VALUES ('cfg.task-related-entities-count', 'true', 1);
INSERT INTO `act_ge_property` VALUES ('common.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('entitylink.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('eventsubscription.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('identitylink.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('job.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('next.dbid', '1', 1);
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(6.8.0.0)', 1);
INSERT INTO `act_ge_property` VALUES ('schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('task.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('variable.schema.version', '6.8.0.0', 1);

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT 1,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `TRANSACTION_ORDER_` int NULL DEFAULT NULL,
  `DURATION_` bigint NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_START`(`START_TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_END`(`END_TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_PROCINST`(`PROC_INST_ID_` ASC, `ACT_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_EXEC`(`EXECUTION_ID_` ASC, `ACT_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `URL_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CONTENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACTION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `MESSAGE_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `FULL_MSG_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `VAR_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REV_` int NULL DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_PROC_INST`(`PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_ACT_INST`(`ACT_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_TIME`(`TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_NAME`(`NAME_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_TASK_ID`(`TASK_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_entitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_entitylink`;
CREATE TABLE `act_hi_entitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LINK_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PARENT_ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REF_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REF_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REF_SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HIERARCHY_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_REF_SCOPE`(`REF_SCOPE_ID_` ASC, `REF_SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_ROOT_SCOPE`(`ROOT_SCOPE_ID_` ASC, `ROOT_SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_entitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_USER`(`USER_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_TASK`(`TASK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT 1,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint NULL DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `START_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `END_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `PROC_INST_ID_`(`PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_INST_END`(`END_TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_I_BUSKEY`(`BUSINESS_KEY_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_SUPER_PROCINST`(`SUPER_PROCESS_INSTANCE_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT 1,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) NULL DEFAULT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PRIORITY_` int NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) NULL DEFAULT NULL,
  `FORM_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  `LAST_UPDATED_TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_INST_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_tsk_log
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_tsk_log`;
CREATE TABLE `act_hi_tsk_log`  (
  `ID_` bigint NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DATA_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_tsk_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT 1,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `VAR_TYPE_` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_NAME_TYPE`(`NAME_` ASC, `VAR_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_VAR_SCOPE_ID_TYPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_VAR_SUB_ID_TYPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_PROC_INST`(`PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_TASK_ID`(`TASK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_EXE`(`EXECUTION_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_id_bytearray`;
CREATE TABLE `act_id_bytearray`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BYTES_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------
INSERT INTO `act_id_group` VALUES ('1', NULL, 'admin', NULL);
INSERT INTO `act_id_group` VALUES ('2', NULL, '2', NULL);

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `USER_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `VALUE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PASSWORD_` longblob NULL,
  `PARENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership`  (
  `USER_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `GROUP_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`, `GROUP_ID_`) USING BTREE,
  INDEX `ACT_FK_MEMB_GROUP`(`GROUP_ID_` ASC) USING BTREE,
  CONSTRAINT `act_id_membership_ibfk_1` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_id_membership_ibfk_2` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_priv
-- ----------------------------
DROP TABLE IF EXISTS `act_id_priv`;
CREATE TABLE `act_id_priv`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_PRIV_NAME`(`NAME_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_priv
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_priv_mapping
-- ----------------------------
DROP TABLE IF EXISTS `act_id_priv_mapping`;
CREATE TABLE `act_id_priv_mapping`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PRIV_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_PRIV_MAPPING`(`PRIV_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_PRIV_USER`(`USER_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_PRIV_GROUP`(`GROUP_ID_` ASC) USING BTREE,
  CONSTRAINT `act_id_priv_mapping_ibfk_1` FOREIGN KEY (`PRIV_ID_`) REFERENCES `act_id_priv` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_priv_mapping
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_property
-- ----------------------------
DROP TABLE IF EXISTS `act_id_property`;
CREATE TABLE `act_id_property`  (
  `NAME_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `VALUE_` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REV_` int NULL DEFAULT NULL,
  PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_property
-- ----------------------------
INSERT INTO `act_id_property` VALUES ('schema.version', '6.8.0.0', 1);

-- ----------------------------
-- Table structure for act_id_token
-- ----------------------------
DROP TABLE IF EXISTS `act_id_token`;
CREATE TABLE `act_id_token`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `TOKEN_VALUE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TOKEN_DATE_` timestamp(3) NULL DEFAULT NULL,
  `IP_ADDRESS_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `USER_AGENT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TOKEN_DATA_` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_token
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `FIRST_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `LAST_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DISPLAY_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EMAIL_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PWD_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PICTURE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------
INSERT INTO `act_id_user` VALUES ('admin', NULL, 'admin', 'admin', 'admin', NULL, NULL, NULL, '');
INSERT INTO `act_id_user` VALUES ('user1', NULL, 'user1', 'user1', 'user1', NULL, NULL, NULL, '');
INSERT INTO `act_id_user` VALUES ('user2', NULL, 'user2', 'user2', 'user2', NULL, NULL, NULL, '');
INSERT INTO `act_id_user` VALUES ('user3', NULL, 'user3', 'user3', 'user3', NULL, NULL, NULL, '');
INSERT INTO `act_id_user` VALUES ('user4', NULL, 'user4', 'user4', 'user4', NULL, NULL, NULL, '');

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_INFO_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_INFO_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_INFO_JSON_BA`(`INFO_JSON_ID_` ASC) USING BTREE,
  CONSTRAINT `act_procdef_info_ibfk_1` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_procdef_info_ibfk_2` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_procdef_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ENGINE_VERSION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int NULL DEFAULT NULL,
  `META_INFO_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_SOURCE`(`EDITOR_SOURCE_VALUE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_MODEL_SOURCE_EXTRA`(`EDITOR_SOURCE_EXTRA_VALUE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_MODEL_DEPLOYMENT`(`DEPLOYMENT_ID_` ASC) USING BTREE,
  CONSTRAINT `act_re_model_ibfk_1` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_re_model_ibfk_2` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_re_model_ibfk_3` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `VERSION_` int NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint NULL DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  `ENGINE_VERSION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DERIVED_VERSION_` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_PROCDEF`(`KEY_` ASC, `VERSION_` ASC, `DERIVED_VERSION_` ASC, `TENANT_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_actinst`;
CREATE TABLE `act_ru_actinst`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT 1,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint NULL DEFAULT NULL,
  `TRANSACTION_ORDER_` int NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_START`(`START_TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_END`(`END_TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_PROC`(`PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_PROC_ACT`(`PROC_INST_ID_` ASC, `ACT_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_EXEC`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_EXEC_ACT`(`EXECUTION_ID_` ASC, `ACT_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_TASK`(`TASK_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_deadletter_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_deadletter_job`;
CREATE TABLE `act_ru_deadletter_job`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_DEADLETTER_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_DEADLETTER_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_DEADLETTER_JOB_CORRELATION_ID`(`CORRELATION_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_DJOB_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_DJOB_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_DJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_EXECUTION`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_PROC_DEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_deadletter_job_ibfk_1` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_deadletter_job_ibfk_2` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_deadletter_job_ibfk_3` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_deadletter_job_ibfk_4` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_deadletter_job_ibfk_5` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_deadletter_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_entitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_entitylink`;
CREATE TABLE `act_ru_entitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `LINK_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PARENT_ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REF_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REF_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REF_SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HIERARCHY_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_REF_SCOPE`(`REF_SCOPE_ID_` ASC, `REF_SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_ROOT_SCOPE`(`ROOT_SCOPE_ID_` ASC, `ROOT_SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC, `LINK_TYPE_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_entitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EVENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EVENT_SUBSCR_CONFIG_`(`CONFIGURATION_` ASC) USING BTREE,
  INDEX `ACT_IDX_EVENT_SUBSCR_SCOPEREF_`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_EVENT_EXEC`(`EXECUTION_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_event_subscr_ibfk_1` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PARENT_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `IS_ACTIVE_` tinyint NULL DEFAULT NULL,
  `IS_CONCURRENT_` tinyint NULL DEFAULT NULL,
  `IS_SCOPE_` tinyint NULL DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint NULL DEFAULT NULL,
  `IS_MI_ROOT_` tinyint NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int NULL DEFAULT NULL,
  `CACHED_ENT_STATE_` int NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `START_ACT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NULL DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint NULL DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int NULL DEFAULT NULL,
  `TASK_COUNT_` int NULL DEFAULT NULL,
  `JOB_COUNT_` int NULL DEFAULT NULL,
  `TIMER_JOB_COUNT_` int NULL DEFAULT NULL,
  `SUSP_JOB_COUNT_` int NULL DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int NULL DEFAULT NULL,
  `EXTERNAL_WORKER_JOB_COUNT_` int NULL DEFAULT NULL,
  `VAR_COUNT_` int NULL DEFAULT NULL,
  `ID_LINK_COUNT_` int NULL DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EXEC_BUSKEY`(`BUSINESS_KEY_` ASC) USING BTREE,
  INDEX `ACT_IDC_EXEC_ROOT`(`ROOT_PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_EXEC_REF_ID_`(`REFERENCE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_EXE_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_EXE_PARENT`(`PARENT_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_EXE_SUPER`(`SUPER_EXEC_` ASC) USING BTREE,
  INDEX `ACT_FK_EXE_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_execution_ibfk_1` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_execution_ibfk_2` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_execution_ibfk_3` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `act_ru_execution_ibfk_4` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_external_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_external_job`;
CREATE TABLE `act_ru_external_job`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `RETRIES_` int NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EXTERNAL_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_EXTERNAL_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_EXTERNAL_JOB_CORRELATION_ID`(`CORRELATION_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_EJOB_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_EJOB_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_EJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  CONSTRAINT `act_ru_external_job_ibfk_1` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_external_job_ibfk_2` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_external_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_history_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_history_job`;
CREATE TABLE `act_ru_history_job`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `RETRIES_` int NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ADV_HANDLER_CFG_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_history_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_USER`(`USER_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_GROUP`(`GROUP_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_ATHRZ_PROCEDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_TSKASS_TASK`(`TASK_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_IDL_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_identitylink_ibfk_1` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_identitylink_ibfk_2` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_identitylink_ibfk_3` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `RETRIES_` int NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_JOB_CORRELATION_ID`(`CORRELATION_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_JOB_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_JOB_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_JOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_JOB_EXECUTION`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_JOB_PROC_DEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_job_ibfk_1` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_job_ibfk_2` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_job_ibfk_3` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_job_ibfk_4` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_job_ibfk_5` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_suspended_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_suspended_job`;
CREATE TABLE `act_ru_suspended_job`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `RETRIES_` int NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_SUSPENDED_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_SUSPENDED_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_SUSPENDED_JOB_CORRELATION_ID`(`CORRELATION_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_SJOB_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_SJOB_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_SJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_EXECUTION`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_PROC_DEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_suspended_job_ibfk_1` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_suspended_job_ibfk_2` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_suspended_job_ibfk_3` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_suspended_job_ibfk_4` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_suspended_job_ibfk_5` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_suspended_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DELEGATION_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PRIORITY_` int NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  `FORM_KEY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CLAIM_TIME_` datetime(3) NULL DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint NULL DEFAULT NULL,
  `VAR_COUNT_` int NULL DEFAULT NULL,
  `ID_LINK_COUNT_` int NULL DEFAULT NULL,
  `SUB_TASK_COUNT_` int NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_TASK_CREATE`(`CREATE_TIME_` ASC) USING BTREE,
  INDEX `ACT_IDX_TASK_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_TASK_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_TASK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_TASK_EXE`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_TASK_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_TASK_PROCDEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_task_ibfk_1` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_task_ibfk_2` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_task_ibfk_3` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_timer_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_timer_job`;
CREATE TABLE `act_ru_timer_job`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `RETRIES_` int NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_CORRELATION_ID`(`CORRELATION_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_DUEDATE`(`DUEDATE_` ASC) USING BTREE,
  INDEX `ACT_IDX_TJOB_SCOPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_TJOB_SUB_SCOPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_TJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_EXECUTION`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_PROC_DEF`(`PROC_DEF_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_timer_job_ibfk_1` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_timer_job_ibfk_2` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_timer_job_ibfk_3` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_timer_job_ibfk_4` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_timer_job_ibfk_5` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_timer_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable`  (
  `ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `REV_` int NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_VAR_SCOPE_ID_TYPE`(`SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_IDX_RU_VAR_SUB_ID_TYPE`(`SUB_SCOPE_ID_` ASC, `SCOPE_TYPE_` ASC) USING BTREE,
  INDEX `ACT_FK_VAR_BYTEARRAY`(`BYTEARRAY_ID_` ASC) USING BTREE,
  INDEX `ACT_IDX_VARIABLE_TASK_ID`(`TASK_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_VAR_EXE`(`EXECUTION_ID_` ASC) USING BTREE,
  INDEX `ACT_FK_VAR_PROCINST`(`PROC_INST_ID_` ASC) USING BTREE,
  CONSTRAINT `act_ru_variable_ibfk_1` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_variable_ibfk_2` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `act_ru_variable_ibfk_3` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Table structure for ai_chat_conversation
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_conversation`;
CREATE TABLE `ai_chat_conversation`  (
  `id` bigint NOT NULL DEFAULT 1111111111111111111 COMMENT '主键ID',
  `conversation_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '会话id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会话标题',
  `content` json NULL COMMENT '内容',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天对话' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_chat_conversation
-- ----------------------------

-- ----------------------------
-- Table structure for ai_chat_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_knowledge`;
CREATE TABLE `ai_chat_knowledge`  (
  `id` bigint NOT NULL DEFAULT 1111111111111111111 COMMENT '主键ID',
  `doc_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '文档文件ID',
  `doc_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文档标题',
  `doc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文档地址',
  `ref_doc_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '向量数据库ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天知识库文档' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_chat_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for ai_model
-- ----------------------------
DROP TABLE IF EXISTS `ai_model`;
CREATE TABLE `ai_model`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `model_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型编码',
  `model_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型名称',
  `platform_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AI平台编码',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `platform_id`(`platform_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1914636922047549443 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AI模型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_model
-- ----------------------------

-- ----------------------------
-- Table structure for ai_platform
-- ----------------------------
DROP TABLE IF EXISTS `ai_platform`;
CREATE TABLE `ai_platform`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `platform_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ai平台编码',
  `platform_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ai平台名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AI平台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_platform
-- ----------------------------

-- ----------------------------
-- Table structure for flw_channel_definition
-- ----------------------------
DROP TABLE IF EXISTS `flw_channel_definition`;
CREATE TABLE `flw_channel_definition`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION_` int NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IMPLEMENTATION_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_IDX_CHANNEL_DEF_UNIQ`(`KEY_` ASC, `VERSION_` ASC, `TENANT_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_channel_definition
-- ----------------------------

-- ----------------------------
-- Table structure for flw_ev_databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS `flw_ev_databasechangelog`;
CREATE TABLE `flw_ev_databasechangelog`  (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `AUTHOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `MD5SUM` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `COMMENTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LIQUIBASE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CONTEXTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LABELS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_ev_databasechangelog
-- ----------------------------
INSERT INTO `flw_ev_databasechangelog` VALUES ('1', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2025-04-15 13:55:51', 1, 'EXECUTED', '9:63268f536c469325acef35970312551b', 'createTable tableName=FLW_EVENT_DEPLOYMENT; createTable tableName=FLW_EVENT_RESOURCE; createTable tableName=FLW_EVENT_DEFINITION; createIndex indexName=ACT_IDX_EVENT_DEF_UNIQ, tableName=FLW_EVENT_DEFINITION; createTable tableName=FLW_CHANNEL_DEFIN...', '', NULL, '4.29.2', NULL, NULL, '4696547632');
INSERT INTO `flw_ev_databasechangelog` VALUES ('2', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2025-04-15 13:55:51', 2, 'EXECUTED', '9:dcb58b7dfd6dbda66939123a96985536', 'addColumn tableName=FLW_CHANNEL_DEFINITION; addColumn tableName=FLW_CHANNEL_DEFINITION', '', NULL, '4.29.2', NULL, NULL, '4696547632');
INSERT INTO `flw_ev_databasechangelog` VALUES ('3', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2025-04-15 13:55:51', 3, 'EXECUTED', '9:d0c05678d57af23ad93699991e3bf4f6', 'customChange', '', NULL, '4.29.2', NULL, NULL, '4696547632');

-- ----------------------------
-- Table structure for flw_ev_databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `flw_ev_databasechangeloglock`;
CREATE TABLE `flw_ev_databasechangeloglock`  (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime NULL DEFAULT NULL,
  `LOCKEDBY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_ev_databasechangeloglock
-- ----------------------------
INSERT INTO `flw_ev_databasechangeloglock` VALUES (1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for flw_event_definition
-- ----------------------------
DROP TABLE IF EXISTS `flw_event_definition`;
CREATE TABLE `flw_event_definition`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION_` int NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_IDX_EVENT_DEF_UNIQ`(`KEY_` ASC, `VERSION_` ASC, `TENANT_ID_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_event_definition
-- ----------------------------

-- ----------------------------
-- Table structure for flw_event_deployment
-- ----------------------------
DROP TABLE IF EXISTS `flw_event_deployment`;
CREATE TABLE `flw_event_deployment`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_event_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for flw_event_resource
-- ----------------------------
DROP TABLE IF EXISTS `flw_event_resource`;
CREATE TABLE `flw_event_resource`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `RESOURCE_BYTES_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_event_resource
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', '1565314987957145601:TRIGGER_NAME', 'DEFAULT', '*/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', '1565314987957145602:TRIGGER_NAME', 'DEFAULT', '*/20 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', '1565314987957145603:TRIGGER_NAME', 'DEFAULT', '*/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', '9223372036854775807:TRIGGER_NAME', 'DEFAULT', '*/3 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', '1565314987957145601:JOB_NAME', 'DEFAULT', NULL, 'com.breeze.boot.quartz.conf.DisallowConcurrentExecutionJob', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400084A4F425F444154417372002A636F6D2E627265657A652E626F6F742E71756172747A2E646F6D61696E2E53797351756172747A4A6F6200000000000000010200074C0009636C617A7A4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000C6A6F6247726F75704E616D6571007E00094C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E000A4C000673746174757371007E000A78720023636F6D2E627265657A652E626F6F742E636F72652E626173652E426173654D6F64656CA0CB7674700FCCDB0200094C0008637265617465427971007E00094C000A6372656174654E616D6571007E00094C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000864656C657465427971007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C0008697344656C65746571007E000A4C0008757064617465427971007E00094C000A7570646174654E616D6571007E00094C000A75706461746554696D6571007E000C78720035636F6D2E62616F6D69646F752E6D796261746973706C75732E657874656E73696F6E2E6163746976657265636F72642E4D6F64656C00000000000000010200007870707070707372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787015B91DB02AC7D001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E001100000000707070740032627265657A654A6F62732E64656D6F4A6F62282274657374222C20312C2033442C20344C2C20747275652C2066616C73652971007E001474000E2A2F3130202A202A202A202A203F74000744454641554C54740020E6A0B7E4BE8B2D4265616EE5908DE4BA94E7A78DE4B88DE5908CE58F82E695B07371007E00130000000171007E00147800);
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', '1565314987957145602:JOB_NAME', 'DEFAULT', NULL, 'com.breeze.boot.quartz.conf.DisallowConcurrentExecutionJob', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400084A4F425F444154417372002A636F6D2E627265657A652E626F6F742E71756172747A2E646F6D61696E2E53797351756172747A4A6F6200000000000000010200074C0009636C617A7A4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000C6A6F6247726F75704E616D6571007E00094C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E000A4C000673746174757371007E000A78720023636F6D2E627265657A652E626F6F742E636F72652E626173652E426173654D6F64656CA0CB7674700FCCDB0200094C0008637265617465427971007E00094C000A6372656174654E616D6571007E00094C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000864656C657465427971007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C0008697344656C65746571007E000A4C0008757064617465427971007E00094C000A7570646174654E616D6571007E00094C000A75706461746554696D6571007E000C78720035636F6D2E62616F6D69646F752E6D796261746973706C75732E657874656E73696F6E2E6163746976657265636F72642E4D6F64656C00000000000000010200007870707070707372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787015B91DB02AC7D002737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00110000000070707074001A627265657A654A6F62732E64656D6F4A6F62282274657374222971007E001474000E2A2F3230202A202A202A202A203F74000744454641554C54740020E6A0B7E4BE8B2D4265616EE5908DE8B083E794A8E58D95E4B8AAE58F82E695B07371007E00130000000271007E00147800);
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', '1565314987957145603:JOB_NAME', 'DEFAULT', NULL, 'com.breeze.boot.quartz.conf.DisallowConcurrentExecutionJob', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400084A4F425F444154417372002A636F6D2E627265657A652E626F6F742E71756172747A2E646F6D61696E2E53797351756172747A4A6F6200000000000000010200074C0009636C617A7A4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000C6A6F6247726F75704E616D6571007E00094C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E000A4C000673746174757371007E000A78720023636F6D2E627265657A652E626F6F742E636F72652E626173652E426173654D6F64656CA0CB7674700FCCDB0200094C0008637265617465427971007E00094C000A6372656174654E616D6571007E00094C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000864656C657465427971007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C0008697344656C65746571007E000A4C0008757064617465427971007E00094C000A7570646174654E616D6571007E00094C000A75706461746554696D6571007E000C78720035636F6D2E62616F6D69646F752E6D796261746973706C75732E657874656E73696F6E2E6163746976657265636F72642E4D6F64656C00000000000000010200007870707070707372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787015B91DB02AC7D003737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00110000000070707074004E636F6D2E627265657A652E636C6F75642E71756172747A2E6A6F622E427265657A654A6F62732E64656D6F4A6F62282274657374222C20312C2033442C20344C2C20747275652C2066616C73652971007E001474000E2A2F3135202A202A202A202A203F74000744454641554C54740023E6A0B7E4BE8B2DE585A8E7B1BBE5908D2DE4BA94E7A78DE4B88DE5908CE58F82E695B07371007E0013FFFFFFFF71007E00147800);
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', '9223372036854775807:JOB_NAME', 'DEFAULT', NULL, 'com.breeze.boot.quartz.conf.DisallowConcurrentExecutionJob', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400084A4F425F444154417372002A636F6D2E627265657A652E626F6F742E71756172747A2E646F6D61696E2E53797351756172747A4A6F6200000000000000010200074C0009636C617A7A4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000C6A6F6247726F75704E616D6571007E00094C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E000A4C000673746174757371007E000A78720023636F6D2E627265657A652E626F6F742E636F72652E626173652E426173654D6F64656CA0CB7674700FCCDB0200094C0008637265617465427971007E00094C000A6372656174654E616D6571007E00094C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000864656C657465427971007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C0008697344656C65746571007E000A4C0008757064617465427971007E00094C000A7570646174654E616D6571007E00094C000A75706461746554696D6571007E000C78720035636F6D2E62616F6D69646F752E6D796261746973706C75732E657874656E73696F6E2E6163746976657265636F72642E4D6F64656C00000000000000010200007870707070707372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078707FFFFFFFFFFFFFFF737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E001100000000707070740036636F6D2E627265657A652E636C6F75642E71756172747A2E6A6F622E427265657A654A6F62732E64656D6F4A6F62282274657374222971007E001474000D2A2F33202A202A202A202A203F74000744454641554C5474001DE6A0B7E4BE8B2DE585A8E7B1BBE5908D2DE58D95E4B8AAE58F82E695B07371007E00130000000171007E00147800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('clusteredScheduler', 'LAPTOP-FEJOU78J1688451113107', 1688456361412, 10000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_task_history
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_task_history`;
CREATE TABLE `qrtz_task_history`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_ID` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRE_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TASK_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TASK_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `FIRED_TIME` bigint NULL DEFAULT NULL,
  `FIRED_WAY` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `COMPLETE_TIME` bigint NULL DEFAULT NULL,
  `EXPEND_TIME` bigint NULL DEFAULT NULL,
  `REFIRED` int NULL DEFAULT NULL,
  `EXEC_STATE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LOG` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`FIRE_ID`) USING BTREE,
  INDEX `IDX_QRTZ_TK_S`(`SCHED_NAME` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_task_history
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME` ASC, `CALENDAR_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', '1565314987957145601:TRIGGER_NAME', 'DEFAULT', '1565314987957145601:JOB_NAME', 'DEFAULT', NULL, 1688451120000, -1, 5, 'PAUSED', 'CRON', 1688451113000, 0, NULL, 1, '');
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', '1565314987957145602:TRIGGER_NAME', 'DEFAULT', '1565314987957145602:JOB_NAME', 'DEFAULT', NULL, 1688451120000, -1, 5, 'PAUSED', 'CRON', 1688451113000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', '1565314987957145603:TRIGGER_NAME', 'DEFAULT', '1565314987957145603:JOB_NAME', 'DEFAULT', NULL, 1688451120000, -1, 5, 'PAUSED', 'CRON', 1688451113000, 0, NULL, -1, '');
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', '9223372036854775807:TRIGGER_NAME', 'DEFAULT', '9223372036854775807:JOB_NAME', 'DEFAULT', NULL, 1688451114000, -1, 5, 'PAUSED', 'CRON', 1688451113000, 0, NULL, 1, '');

-- ----------------------------
-- Table structure for sys_audit_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_audit_log`;
CREATE TABLE `sys_audit_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_id` bigint NULL DEFAULT NULL COMMENT '日志ID',
  `field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改的字段名',
  `previous` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上一次的值',
  `now` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前值',
  `time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态',
  `batch` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改批次',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1890254327932006402 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统审计记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_audit_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_db
-- ----------------------------
DROP TABLE IF EXISTS `sys_db`;
CREATE TABLE `sys_db`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '租户ID',
  `db_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源CODE',
  `db_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库名称',
  `driver` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驱动',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_db
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL DEFAULT 1111111111111111111 COMMENT '主键ID',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '上级部门ID',
  `dept_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1565314987957145600, 1111111111111111111, 'GS', '总公司', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-04-15 13:54:50', 0, 1);
INSERT INTO `sys_dept` VALUES (1565314987957145609, 1565314987957145600, 'DSB', '董事办', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dept` VALUES (1581851971500371970, 1565314987957145600, 'IT', 'IT研发部门', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dept` VALUES (1601579918477983745, 1581851971500371970, 'Java1', '研发组1', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dept` VALUES (1601579970948726786, 1581851971500371970, 'Java2', 'Java2', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `dict_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典编码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用 0 关闭 1 启用',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `delete_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除人编码',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1599032827285213185, '性别', 'SEX', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599033063277727745, '菜单类型', 'MENU_TYPE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599034133752188930, '日志类型', 'LOG_TYPE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599034233434017794, '操作类型', 'DO_TYPE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599035056616509442, '日志结果', 'LOG_RESULT', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599035447399813121, '消息级别', 'MSG_LEVEL', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599035906529300481, '消息类型', 'MSG_TYPE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599036245466812417, '开关', 'DICT_STATUS', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599036494331645953, '缓存', 'KEEPALIVE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599036771814215681, '显示隐藏', 'HIDDEN', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599037134667649025, '路由外链', 'HREF', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599218032822394881, '结果', 'RESULT', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599288041217064962, '锁定', 'IS_LOCK', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1599292998100058114, '读取状态', 'MARK_READ', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1601793691449020417, '数据权限固定编码', 'PERMISSION_CODE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1637619982970351618, '任务策略', 'MISFIRE_POLICY', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1637621879726895105, '并发', 'CONCURRENT', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1637622109440536577, '任务状态', 'JOB_STATUS', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1639508202175832066, '任务组', 'JOB_GROUP', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658030824311799809, '身份验证方法', 'CLIENT_AUTHENTICATION_METHODS', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658030901172420609, '授权许可类型', 'AUTHORIZATION_GRANT_TYPES', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658031007506415617, '权限范围', 'SCOPES', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658031278974353410, 'JWT签名算法', 'TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658031392233144321, 'ID-TOKEN签名算法', 'ID_TOKEN_SIGNATURE_ALGORITHM', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658031447681843201, '访问令牌格式', 'ACCESS_TOKEN_FORMAT', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, NULL, 1);
INSERT INTO `sys_dict` VALUES (1658303277953040385, '重定向Uris', 'REDIRECT_URIS', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1764226737312161793, '任务执行结果', 'JOB_RESULT', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1807339348534599681, '流程暂停', 'FLOW_SUSPENDED', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1826494101642350593, '消息读取状态', 'MSG_READ', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (1826496135615549441, '消息关闭状态', 'MSG_CLOSE', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `dict_id` bigint NULL DEFAULT NULL COMMENT '字典ID',
  `value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '字典项的值',
  `label` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项的名称',
  `type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'default' COMMENT '类型，对应elementUI 的类型',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1599033180131037186, 1599033063277727745, '0', '文件夹', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599033573409951745, 1599033063277727745, '1', '菜单', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599033675105046530, 1599033063277727745, '2', '按钮', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599033861437001729, 1599032827285213185, '1', '男', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599033925890871297, 1599032827285213185, '0', '女', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599034388589711362, 1599034133752188930, '0', ' 普通日志', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599034442968862721, 1599034133752188930, '1', ' 登录日志', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599034596094513154, 1599034233434017794, '0', '添加', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599034610313203714, 1599034233434017794, '1', '删除', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599034627035893761, 1599034233434017794, '2', '修改', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599034642189914113, 1599034233434017794, '3', '查询', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599035133351301122, 1599035056616509442, '0', '失败', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599035172446408705, 1599035056616509442, '1', ' 成功', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599035484666204161, 1599035447399813121, 'warning', '警示消息', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599035496510918657, 1599035447399813121, 'info', '一般消息', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599035508733120513, 1599035447399813121, 'error', '紧急消息', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036005007364098, 1599035906529300481, '0', ' 通知', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036025513316353, 1599035906529300481, '1', '公告', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036294716329985, 1599036245466812417, '1', '开启', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036318925852674, 1599036245466812417, '0', '关闭', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036529471524866, 1599036494331645953, '0', '不缓存 ', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036549079896065, 1599036494331645953, '1', '缓存', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036842584707074, 1599036771814215681, '0', '显示', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599036909861343234, 1599036771814215681, '1', '隐藏', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599037293560467457, 1599037134667649025, '1', '外链', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599037318663376897, 1599037134667649025, '0', '路由', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599218200087044097, 1599218032822394881, '1', '成功', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599218217967362049, 1599218032822394881, '0', '失败', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599288066458386434, 1599288041217064962, '0', '正常', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599288094061101058, 1599288041217064962, '1', '锁定', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599293037463601154, 1599292998100058114, '1', '已读', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1599293192749318145, 1599292998100058114, '0', '未读', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1601793891890614273, 1601793691449020417, 'ALL', '全部', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1601793991845072897, 1601793691449020417, 'OWN', '自己', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1601794253766774785, 1601793691449020417, 'DEPT_LEVEL', '部门范围权限', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637620045218017282, 1637619982970351618, '1', ' 执行一次（默认）', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637620093146329089, 1637619982970351618, '-1', '立刻执行', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637620131188666370, 1637619982970351618, '2', '放弃执行', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637621918473875458, 1637621879726895105, '1', '并发', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637621961926864897, 1637621879726895105, '0', '串行', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637622141858312194, 1637622109440536577, '1', '开启', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1637622196887580673, 1637622109440536577, '0', '暂停', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1639508998309257217, 1639508202175832066, 'DEFAULT', '默认', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1639510731953512449, 1639508202175832066, 'SYSTEM', '系统', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658273848363065345, 1658030901172420609, 'refresh_token', 'refresh_token', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658273881355460610, 1658030901172420609, 'client_credentials', 'client_credentials', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658273913035038722, 1658030901172420609, 'authorization_code', 'authorization_code', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658273946912432130, 1658030901172420609, 'password', 'password', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658273985827184642, 1658030901172420609, 'sms_code', 'sms_code', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274057008717825, 1658030901172420609, 'email_code', 'email_code', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274486081822721, 1658030824311799809, 'client_secret_post', 'client_secret_post', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274530675662850, 1658030824311799809, 'client_secret_jwt', 'client_secret_jwt', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274569879822338, 1658030824311799809, 'private_key_jwt', 'private_key_jwt', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274604130508801, 1658030824311799809, 'client_secret_basic', 'client_secret_basic', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274676369006593, 1658030824311799809, 'none', 'none', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274708270882817, 1658030824311799809, 'basic', 'basic', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274853272166402, 1658030824311799809, 'post', 'post', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658274975850700801, 1658031007506415617, 'openid', 'openid', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275001364652033, 1658031007506415617, 'profile', 'profile', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275025167327233, 1658031007506415617, 'email', 'email', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275048814813185, 1658031007506415617, 'address', 'address', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275076136509441, 1658031007506415617, 'phone', 'phone', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275140422606849, 1658031007506415617, 'user_info', 'user_info', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275179521908738, 1658031007506415617, 'read', 'read', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275224136720385, 1658031007506415617, 'write', 'write', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658275335071866882, 1658031392233144321, 'RS256', 'RS256', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275360300605442, 1658031392233144321, 'RS384', 'RS384', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275382203260930, 1658031392233144321, 'RS512', 'RS512', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275403925561345, 1658031392233144321, 'ES256', 'ES256', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275430785884162, 1658031392233144321, 'ES384', 'ES384', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275451379916802, 1658031392233144321, 'ES512', 'ES512', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275473043496962, 1658031392233144321, 'PS256', 'PS256', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275489254481922, 1658031392233144321, 'PS384', 'PS384', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275510104367106, 1658031392233144321, 'PS512', 'PS512', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275743236366338, 1658031447681843201, 'self-contained', 'JWT', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658275781895266306, 1658031447681843201, 'reference', '字符串', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276043741470721, 1658031278974353410, 'RS256', 'RS256', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276067271516162, 1658031278974353410, 'RS384', 'RS384', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276093951483906, 1658031278974353410, 'RS512', 'RS512', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276120979578882, 1658031278974353410, 'ES256', 'ES256', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276146409644033, 1658031278974353410, 'ES384', 'ES384', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276164621312002, 1658031278974353410, 'ES512', 'ES512', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276184783331330, 1658031278974353410, 'PS256', 'PS256', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276208971882498, 1658031278974353410, 'PS384', 'PS384', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658276255755149313, 1658031278974353410, 'PS512', 'PS512', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 1, 1);
INSERT INTO `sys_dict_item` VALUES (1658303476360396801, 1658303277953040385, 'http://www.baidu.com', 'http://www.baidu.com', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658303552222773249, 1658303277953040385, 'http://127.0.0.1:8080/authorized', 'http://127.0.0.1:8080/authorized', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1658303639636262913, 1658303277953040385, 'http://127.0.0.1:8080/login/oauth2/code/breeze-oidc', 'http://127.0.0.1:8080/login/oauth2/code/breeze-oidc', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1659034973417574401, 1658303277953040385, 'http://127.0.0.1:8070/login/oauth2/code/breeze-pkce', 'http://127.0.0.1:8070/login/oauth2/code/breeze-pkce', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1764516521943339010, 1764226737312161793, '1', '成功', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1764516593724657665, 1764226737312161793, '0', '失败', 'danger', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1807339456084942850, 1807339348534599681, '1', '启用', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1807339550830075906, 1807339348534599681, '2', '暂停', 'warning', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1826457340010622977, 1599035447399813121, 'success', '日常消息', 'success', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1826494236204011522, 1826494101642350593, '1', '已读', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1826494281070481410, 1826494101642350593, '0', '未读', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1826496198303617026, 1826496135615549441, '1', '正常', 'primary', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_dict_item` VALUES (1826496237046403073, 1826496135615549441, '0', '关闭', 'info', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_email
-- ----------------------------
DROP TABLE IF EXISTS `sys_email`;
CREATE TABLE `sys_email`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `smtp_host` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `port` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `encoding` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `smtp_socket_factory_class` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `smtp_debug` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `auth` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `protocol` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ssl` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT 1,
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮箱消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_email
-- ----------------------------
INSERT INTO `sys_email` VALUES (1, 'smtp.qq.com', '587', '1900381390@qq.com', 'otsymnaoqzsfbjbj', 'UTF-8', 'javax.net.ssl.SSLSocketFactory', 'true', 'true', 'smtp', 'true', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 0);
INSERT INTO `sys_email` VALUES (2, 'smtp.qq.com', '587', '1900381390@qq.com', 'otsymnaoqzsfbjbj', 'UTF-8', 'javax.net.ssl.SSLSocketFactory', 'true', 'true', 'smtp', 'true', 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 0);

-- ----------------------------
-- Table structure for sys_email_subject
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_subject`;
CREATE TABLE `sys_email_subject`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `subject` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT 1,
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮箱消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_email_subject
-- ----------------------------
INSERT INTO `sys_email_subject` VALUES (1812002763429498882, '测试邮箱', '你好世界', '1111111111111111111,1111111111111111112,1111111111111111113,1111111111111111114,1111111111111111115', '', 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `format` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件扩展名',
  `content_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传格式',
  `bucket` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '桶',
  `object_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象名称',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'uri',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务ID',
  `biz_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `store_type` tinyint(1) NOT NULL COMMENT '存储方式 0 本地 1 minio',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `system_module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统模块',
  `log_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `log_type` tinyint(1) NULL DEFAULT 0 COMMENT '日志类型 0 普通日志 1 登录日志',
  `request_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型  GET  POST  PUT DELETE ',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `do_type` tinyint(1) NULL DEFAULT 3 COMMENT '操作类型 0 添加 1 删除 2 修改 3 查询 4 doLogin',
  `browser` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
  `param_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
  `result` tinyint(1) NULL DEFAULT 0 COMMENT '结果 0 失败 1 成功',
  `result_msg` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果信息',
  `time` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用时',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL DEFAULT 1111111111111111111 COMMENT '主键ID',
  `platform_id` bigint NULL DEFAULT NULL COMMENT '平台ID',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '上一级的菜单ID',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '组件名称',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '类型 0 文件夹 1 菜单 2 按钮',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `permission` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `href` tinyint(1) NULL DEFAULT 0 COMMENT '是否外链 0 路由 1 外链',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '是否缓存 0 不缓存 1 缓存',
  `hidden` tinyint(1) NULL DEFAULT 1 COMMENT '是否隐藏 1 隐藏 0 显示',
  `sort` int NULL DEFAULT NULL COMMENT '顺序',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (702418736619065344, 1111111111111111111, 1914584039822196738, 'AI平台', 'AiPlatform', 1, 'AiPlatform', '/platform', '/ai/platform/index', 'ai:platform:list', 0, 0, 0, 10, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418736619065345, 1111111111111111111, 702418736619065344, '添加', '', 2, NULL, NULL, NULL, 'ai:platform:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418736619065346, 1111111111111111111, 702418736619065344, '详情', '', 2, NULL, NULL, NULL, 'ai:platform:info', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418736619065347, 1111111111111111111, 702418736619065344, '修改', '', 2, NULL, NULL, NULL, 'ai:platform:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418736619065348, 1111111111111111111, 702418736619065344, '删除', '', 2, NULL, NULL, NULL, 'ai:platform:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418737747333120, 1111111111111111111, 1914584039822196738, '聊天知识库', 'AiChatKnowledge', 1, '', '/chatKnowledge ', '/ai/chatKnowledge/index', 'ai:chatKnowledge:list', 0, 0, 0, 10, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418737747333121, 1111111111111111111, 702418737747333120, '添加', '', 2, NULL, NULL, NULL, 'ai:chatKnowledge:create', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418737747333122, 1111111111111111111, 702418737747333120, '详情', '', 2, NULL, NULL, NULL, 'ai:chatKnowledge:info', 0, 0, 0, 3, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418737747333123, 1111111111111111111, 702418737747333120, '修改', '', 2, NULL, NULL, NULL, 'ai:chatKnowledge:modify', 0, 0, 0, 2, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418737747333124, 1111111111111111111, 702418737747333120, '删除', '', 2, NULL, NULL, NULL, 'ai:chatKnowledge:delete', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418738401644544, 1111111111111111111, 1914584039822196738, 'AI模型', 'AiModel', 1, NULL, '/model', '/ai/model/index', 'ai:model:list', 0, 0, 0, 10, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418738401644545, 1111111111111111111, 702418738401644544, '添加', '', 2, NULL, NULL, NULL, 'ai:model:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418738401644546, 1111111111111111111, 702418738401644544, '详情', '', 2, NULL, NULL, NULL, 'ai:model:info', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418738401644547, 1111111111111111111, 702418738401644544, '修改', '', 2, NULL, NULL, NULL, 'ai:model:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (702418738401644548, 1111111111111111111, 702418738401644544, '删除', '', 2, NULL, NULL, NULL, 'ai:model:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340612321378, 1111111111111111111, 1594135789623184129, '任务日志', 'JLog', 1, 'el-icon-s-comment', '/jLog', '/system/job/jlog/index', 'sys:jLog:list', 0, 0, 1, 6, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340612325378, 1111111111111111111, 1856576921613942786, '系统日志', 'SysLog', 1, 'log', '/sysLog', '/system/log/sysLog/index', 'sys:sysLog:list', 0, 0, 0, 9, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340620713987, 1111111111111111111, 1578702340683628545, '修改', '', 2, NULL, NULL, NULL, 'auth:user:modify', 0, 0, 0, 5, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340620713988, 1111111111111111111, 1578702340683628545, '删除', '', 2, NULL, NULL, NULL, 'auth:user:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908290, 1111111111111111111, 1578702340683628545, '添加', '', 2, NULL, NULL, NULL, 'auth:user:create', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908291, 1111111111111111111, 1578702340683628546, '修改', '', 2, NULL, NULL, NULL, 'auth:menu:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908292, 1111111111111111111, 1578702340683628546, '删除', '', 2, NULL, NULL, NULL, 'auth:menu:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340624908293, 1111111111111111111, 1578702340683628546, '添加', '', 2, NULL, NULL, NULL, 'auth:menu:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340633296898, 1111111111111111111, 1578702340662657026, '删除', '', 2, NULL, NULL, NULL, 'auth:dept:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340633296899, 1111111111111111111, 1578702340662657026, '添加', '', 2, NULL, NULL, NULL, 'auth:dept:create', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340641685505, 1111111111111111111, 1578702340654268418, '修改', '', 2, NULL, NULL, NULL, 'auth:role:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340641685506, 1111111111111111111, 1578702340654268418, '删除', '', 2, NULL, NULL, NULL, 'auth:role:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074114, 1111111111111111111, 1578702340654268418, '添加', '', 2, NULL, NULL, NULL, 'auth:role:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074115, 1111111111111111111, 1578702340662657027, '修改', '', 2, NULL, NULL, NULL, 'sys:dict:modify', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074116, 1111111111111111111, 1578702340662657027, '删除', '', 2, NULL, NULL, NULL, 'sys:dict:delete', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340650074117, 1111111111111111111, 1578702340662657027, '添加', '', 2, NULL, NULL, NULL, 'sys:dict:create', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268411, 1111111111111111111, 1578702340612325378, '清空表', '', 2, NULL, NULL, NULL, 'sys:sysLog:truncate', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268412, 1111111111111111111, 1578702340666851329, '租户管理', 'Tenant', 1, 'tenant', '/tenant', '/auth/tenant/index', 'auth:tenant:list', 0, 0, 0, 10, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268416, 1111111111111111111, 1578702340666851329, '岗位管理', 'Post', 1, 'post', '/post', '/auth/post/index', 'auth:post:list', 0, 0, 0, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268417, 1111111111111111111, 1578702340612325378, '删除', '', 2, NULL, NULL, NULL, 'sys:sysLog:delete', 0, 0, 0, 2, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340654268418, 1111111111111111111, 1578702340666851329, '角色管理', 'Role', 1, 'role', '/role', '/auth/role/index', 'auth:role:list', 0, 0, 0, 5, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340662657026, 1111111111111111111, 1578702340666851329, '部门管理', 'Dept', 1, 'dept', '/dept', '/auth/dept/index', 'auth:dept:list', 0, 0, 0, 9, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340662657027, 1111111111111111111, 1637297406628823041, '字典管理', 'Dict', 1, 'dict', '/dict', '/system/dict/index', 'sys:dict:list', 0, 0, 0, 6, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340666851329, 1111111111111111111, 1111111111111111111, '权限管理', '', 0, 'auth', '/auth', '', '', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340671045634, 1111111111111111111, 1578702340666851329, '平台管理', 'Platform', 1, 'platform', '/platform', '/auth/platform/index', 'auth:platform:list', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340671045635, 1111111111111111111, 1578702340671045634, '添加', '', 2, NULL, NULL, NULL, 'auth:platform:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340679434241, 1111111111111111111, 1578702340671045634, '修改', '', 2, NULL, NULL, NULL, 'auth:platform:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340679434243, 1111111111111111111, 1578702340671045634, '删除', '', 2, NULL, NULL, NULL, 'auth:platform:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340683628545, 1111111111111111111, 1578702340666851329, '用户管理', 'User', 1, 'user', '/user', '/auth/user/index', 'auth:user:list', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1578702340683628546, 1111111111111111111, 1578702340666851329, '菜单管理', 'Menu', 1, 'menu', '/menu', '/auth/menu/index', 'auth:menu:list', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1580357263003439106, 1111111111111111111, 1111111111111111111, '流程管理', '', 0, 'bpm_manage', '/bpm', NULL, NULL, 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1580357773622202370, 1111111111111111111, 1580357263003439106, '流程分类', 'Category', 1, 'bpm_category', '/category', '/bpm/def/category/index', 'bpm:category:list', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1581843318345035778, 1111111111111111111, 1578702340662657026, '修改', '', 2, NULL, NULL, NULL, 'auth:dept:modify', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1581965904601088001, 1111111111111111111, 1581966349440581634, '测试KeepAive', 'KeepAlive', 1, 'client', '/keepAlive', '/test/keepalive/index', 'keep:create', 0, 1, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1581965904601088002, 1111111111111111111, 1581966349440581634, '测试外部链接', '', 1, 'icon-test', 'http://ww.baidu.com', NULL, NULL, 1, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1581966349440581634, 1111111111111111111, 1111111111111111111, '相关测试', '', 0, 'test', '/test', NULL, NULL, 0, 0, 0, 8, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1582554585967800321, 1111111111111111111, 1111111111111111111, '监控平台', '', 0, 'monitor', '/monitor', NULL, NULL, 0, 0, 0, 7, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1582555155344568321, 1111111111111111111, 1582554585967800321, 'swagger', '', 1, 'swagger', 'http://localhost:9000/doc.html#/home', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1582558188828790785, 1111111111111111111, 1582554585967800321, '德鲁伊', '', 1, 'durid', 'http://localhost:9000/druid/login.html', NULL, NULL, 1, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1582607135668621314, 1111111111111111111, 1581966349440581634, '掘金', '', 1, 'icon-test', 'https://juejin.cn/', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1586717542633123841, 1111111111111111111, 1578702340683628545, '角色分配', 'UserRole', 1, NULL, '/userRole', '/auth/user/role/index', 'auth:role:list', 0, 1, 1, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1586717542633123843, 1111111111111111111, 1578702340662657027, '字典项', 'DictItem', 1, NULL, '/dictItem', '/system/dict/item/index', 'sys:item:list', 0, 0, 1, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1587692336744742913, 1111111111111111111, 1578702340683628545, '详情', '', 2, NULL, NULL, NULL, 'auth:user:info', 0, 0, 0, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230018781, 1111111111111111111, 9223372036854775119, '修改', '', 2, NULL, NULL, NULL, 'sys:msg:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230018782, 1111111111111111111, 9223372036854775120, '修改', '', 2, NULL, NULL, NULL, 'sys:msgUser:modify', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048172, 1111111111111111111, 9223372036854775119, '删除', '', 2, NULL, NULL, NULL, 'sys:msg:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048178, 1111111111111111111, 9223372036854775120, '删除', '', 2, NULL, NULL, NULL, 'sys:msgUser:delete', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048770, 1111111111111111111, 1578702340654268416, '添加', '', 2, NULL, NULL, NULL, 'auth:post:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048771, 1111111111111111111, 1578702340654268416, '修改', '', 2, NULL, NULL, NULL, 'auth:post:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048772, 1111111111111111111, 1578702340654268416, '删除', '', 2, NULL, NULL, NULL, 'auth:post:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048778, 1111111111111111111, 1594135789623984129, '删除', '', 2, NULL, NULL, NULL, 'sys:file:delete', 0, 0, 0, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048781, 1111111111111111111, 1578702340654268412, '添加', '', 2, NULL, NULL, NULL, 'auth:tenant:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048782, 1111111111111111111, 1578702340654268412, '修改', '', 2, NULL, NULL, NULL, 'auth:tenant:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230048783, 1111111111111111111, 1578702340654268412, '详情', '', 2, NULL, NULL, NULL, 'auth:tenant:info', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589181822230049781, 1111111111111111111, 9223372036854775119, '添加', '', 2, NULL, NULL, NULL, 'sys:msg:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1589789746153263106, 1111111111111111111, 9223372036854775807, '添加', '', 2, NULL, NULL, NULL, 'auth:rowPermission:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1594135789623184129, 1111111111111111111, 1637297406628823041, '任务管理', 'Job', 1, 'job', '/job', '/system/job/index', 'sys:job:list', 0, 0, 0, 5, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1594135789623984129, 1111111111111111111, 1637297406628823041, '文件管理', 'File', 1, 'file', '/file', '/system/file/index', 'sys:file:list', 0, 0, 0, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1594531945449451666, 1111111111111111111, 1594135789623984129, '预览', '', 2, NULL, NULL, NULL, 'sys:file:preview', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1594532038764326666, 1111111111111111111, 1594135789623984129, '文件上传', '', 2, NULL, NULL, NULL, 'sys:file:upload', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1594532038764326913, 1111111111111111111, 1594135789623984129, '下载', '', 2, NULL, NULL, NULL, 'sys:file:download', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1598222373868695551, 1111111111111111111, 1813429194697031681, '删除', '', 2, NULL, NULL, NULL, 'auth:menuColumn:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1598222373868695553, 1111111111111111111, 9223372036854775807, '删除', '', 2, NULL, NULL, NULL, 'auth:rowPermission:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1598222575933485057, 1111111111111111111, 9223372036854775807, '修改', '', 2, NULL, NULL, NULL, 'auth:rowPermission:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1599935876379897858, 1111111111111111111, 1586717542633123841, '用户增加角色', '', 2, NULL, NULL, NULL, 'auth:user:set:role', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1599936405688479746, 1111111111111111111, 1586717542633123841, '重置密码', '', 2, NULL, NULL, NULL, 'auth:user:reset', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1601081152259891202, 1111111111111111111, 1578702340683628545, '导出', '', 2, NULL, NULL, NULL, 'auth:user:export', 0, 0, 0, 6, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459915239, 1111111111111111111, 1578702340612321378, '删除', '', 2, NULL, NULL, NULL, 'sys:jLog:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935231, 1111111111111111111, 1594135789623184129, '添加', '', 2, NULL, NULL, NULL, 'sys:job:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935232, 1111111111111111111, 1594135789623184129, '删除', '', 2, NULL, NULL, NULL, 'sys:job:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935234, 1111111111111111111, 1580357773622202370, '添加', '', 2, NULL, NULL, NULL, 'bpm:category:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935235, 1111111111111111111, 1580357773622202370, '删除', '', 2, NULL, NULL, NULL, 'bpm:category:delete', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935236, 1111111111111111111, 1578702340612321378, '清空', '', 2, NULL, NULL, NULL, 'sys:jLog:truncate', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935239, 1111111111111111111, 1594135789623184129, '修改', '', 2, NULL, NULL, NULL, 'sys:job:modify', 0, 0, 0, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632642093459935256, 1111111111111111111, 1580357773622202370, '修改', '', 2, NULL, NULL, NULL, 'bpm:category:modify', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632925687029903361, 1111111111111111111, 9223372036854775121, '查看', '', 2, NULL, NULL, NULL, 'bpm:definition:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632925792583757826, 1111111111111111111, 9223372036854775121, '删除', '', 2, NULL, NULL, NULL, 'bpm:definition:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1632950163226464257, 1111111111111111111, 9223372036854775121, '启动', '', 2, NULL, NULL, NULL, 'bpm:instance:start', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1633285580421271553, 1111111111111111111, 1580357263003439106, '流程实例', 'Instance', 1, 'bpm_instance', '/instance', '/bpm/def/instance/index', 'bpm:instance:list', 0, 0, 0, 5, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1633338860669214722, 1111111111111111111, 9223372036854775121, '流程挂起', '', 2, NULL, NULL, NULL, 'bpm:definition:suspended', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1637297406628823041, 1111111111111111111, 1111111111111111111, '系统管理', '', 0, 'system', '/system', NULL, NULL, 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1637647486464438273, 1111111111111111111, 1594135789623184129, '运行一次', '', 2, NULL, NULL, NULL, 'sys:job:run', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1648569698801393666, 1111111111111111111, 1578702340666851329, '客户端管理', 'Client', 1, 'client', '/client', '/auth/client/index', 'auth:client:list', 0, 0, 0, 11, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1657464120406532098, 1111111111111111111, 1648569698801393666, '添加', '', 2, NULL, NULL, NULL, 'auth:client:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1657464313466150914, 1111111111111111111, 1648569698801393666, '修改', '', 2, NULL, NULL, NULL, 'auth:client:modify', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1657464432802488321, 1111111111111111111, 1648569698801393666, '删除', '', 2, NULL, NULL, NULL, 'auth:client:delete', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1658346521789206529, 1111111111111111111, 1648569698801393666, '重置密钥', '', 2, NULL, NULL, NULL, 'auth:client:resetClientSecret', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1664159611618799618, 1111111111111111111, 1582554585967800321, 'springdoc', '', 1, 'springdoc', 'http://localhost:9000/swagger-ui/index.html', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1753664334832967682, 1111111111111111111, 1637297406628823041, '站内信', '', 0, 'message', '/msg', NULL, NULL, 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1761982833619738625, 1111111111111111111, 1578702340683628546, '详情', '', 2, NULL, NULL, NULL, 'auth:menu:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1761982923168129025, 1111111111111111111, 1578702340671045634, '详情', '', 2, NULL, NULL, NULL, 'auth:platform:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1761982995855417346, 1111111111111111111, 1578702340662657026, '详情', '', 2, NULL, NULL, NULL, 'auth:dept:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353074732359681, 1111111111111111111, 1578702340654268416, '详情', '', 2, NULL, NULL, NULL, 'auth:post:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353132718612482, 1111111111111111111, 1578702340654268418, '详情', '', 2, NULL, NULL, NULL, 'auth:role:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353207431749631, 1111111111111111111, 1813429194697031681, '详情', '', 2, NULL, NULL, NULL, 'auth:menuColumn:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353207431749633, 1111111111111111111, 9223372036854775807, '详情', '', 2, NULL, NULL, NULL, 'auth:rowPermission:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353511967580162, 1111111111111111111, 1578702340654268412, '删除', '', 2, NULL, NULL, NULL, 'auth:tenant:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353732504084481, 1111111111111111111, 1648569698801393666, '详情', '', 2, NULL, NULL, NULL, 'auth:client:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353800099487746, 1111111111111111111, 1594135789623184129, '详情', '', 2, NULL, NULL, NULL, 'sys:job:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353849600663553, 1111111111111111111, 1578702340662657027, '详情', '', 2, NULL, NULL, NULL, 'auth:dict:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762353899760345089, 1111111111111111111, 1578702340612325378, '详情', '', 2, NULL, NULL, NULL, 'sys:sysLog:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1762354008057274370, 1111111111111111111, 1586717542633123843, '详情', '', 2, NULL, NULL, NULL, 'auth:dictItem:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1764834376870072321, 1111111111111111111, 1594135789623984129, '修改', '', 2, NULL, NULL, NULL, 'sys:file:edit', 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1769605991864541186, 1111111111111111111, 9223372036854775119, '详情', '', 2, NULL, NULL, NULL, 'sys:msg:info', 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1769913478174011393, 1111111111111111111, 1578702340654268418, '设置菜单权限', '', 2, NULL, NULL, NULL, 'auth:menu:permission:modify', 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1769932578489217025, 1111111111111111111, 1578702340654268418, '设置行权限', '', 2, NULL, NULL, NULL, 'auth:row:permission:modify', 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1771092462060519425, 1111111111111111111, 1578702340654268418, '删除列级别权限', '', 2, NULL, NULL, NULL, 'auth:role:column:permission:remove', 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1772119073333514242, 1111111111111111111, 9223372036854775119, 'send', '', 2, NULL, NULL, NULL, 'sys:msg:send', 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1773244400852008961, 1111111111111111111, 1581966349440581634, '二级菜单', '', 0, 'auth', '/test', NULL, NULL, 1, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1773244571417575425, 1111111111111111111, 1773244400852008961, '三级菜单', 'Test3', 1, 'add', '/test3', '/system/file/index', NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1793490122163617793, 1111111111111111111, 1581966349440581634, 'wangEditor', 'WangEditor', 1, NULL, '/wangEditor', '/test/wangEditor/index', NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1795343069638156290, 1111111111111111111, 9223372036854775121, '流程设计', '', 2, NULL, NULL, NULL, 'bpm:definition:design', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1795361472646008833, 1111111111111111111, 9223372036854775121, '部署', '', 2, NULL, NULL, NULL, 'bpm:definition:deploy', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1806128900648783874, 1111111111111111111, 1111111111111111111, '任务管理', '', 0, 'task_manager', '/task', NULL, NULL, 0, 0, 0, 5, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1806941904676159490, 1111111111111111111, 1806128900648783874, '待办任务', 'Todo', 1, 'todo', '/todo', '/bpm/task/todo/index', NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1806942129591517186, 1111111111111111111, 1806128900648783874, '已办任务', 'Completed', 1, 'completed_tasks', '/completed', '/bpm/task/completed/index', NULL, 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1806942202333331457, 1111111111111111111, 1806128900648783874, '我的提交', 'Apply', 1, 'history_task', '/apply', '/bpm/task/apply/index', NULL, 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1806949939763052546, 1111111111111111111, 1111111111111111111, '工单管理', '', 0, 'wo', '/wo', NULL, NULL, 0, 0, 0, 6, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1806950505004236802, 1111111111111111111, 1806949939763052546, '请假管理', 'Leave', 1, 'leave', '/leave', '/wo/leave/index', 'wo:leave:list', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1807352428140244993, 1111111111111111111, 1581966349440581634, 'bpmnViewer', 'BpmnViewer', 1, NULL, '/bpmnViewer', '/test/bpmnViewer/index', NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1810587679377408001, 1111111111111111111, 1813418484919291905, '邮箱配置管理', 'EmailConfig', 1, 'email_config', '/config', '/system/email/config/index', 'sys:emailConfig:list', 0, 0, 0, 7, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811244854244306945, 1111111111111111111, 1810587679377408001, '添加', '', 2, NULL, NULL, NULL, 'sys:emailConfig:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811245062084653057, 1111111111111111111, 1810587679377408001, '修改', '', 2, NULL, NULL, NULL, 'sys:emailConfig:modify', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811245262626910210, 1111111111111111111, 1810587679377408001, '删除', '', 2, NULL, NULL, NULL, 'sys:emailConfig:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811276032536031234, 1111111111111111111, 1810587679377408001, '详情', '', 2, NULL, NULL, NULL, 'sys:emailConfig:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811295279165304834, 1111111111111111111, 1813418484919291905, '邮箱主题管理', 'MSubject', 1, 'email_subject', '/msubject', '/system/email/msubject/index', 'sys:mSubject:list', 0, 0, 0, 8, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811295626692751361, 1111111111111111111, 1811295279165304834, '添加', '', 2, NULL, NULL, '', 'sys:mSubject:create', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811295707286302721, 1111111111111111111, 1811295279165304834, '详情', '', 2, NULL, NULL, '', 'sys:mSubject:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811295805265244162, 1111111111111111111, 1811295279165304834, '删除', '', 2, NULL, NULL, '', 'sys:mSubject:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1811295880271982594, 1111111111111111111, 1811295279165304834, '修改', '', 2, NULL, NULL, '', 'sys:mSubject:modify', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1812002047382114305, 1111111111111111111, 1813418484919291905, '邮箱发送人详情', 'UserEmail', 1, NULL, '/userEmail', '/system/email/userEmail/index', NULL, 0, 0, 1, 9, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1813418484919291905, 1111111111111111111, 1637297406628823041, '邮箱管理', '', 0, 'email', '/email', NULL, NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1813427994547273729, 1111111111111111111, 1578702340666851329, '数据权限', '', 0, 'permission', '/permission', NULL, NULL, 0, 0, 0, 12, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1813429194697031681, 1111111111111111111, 1813427994547273729, '菜单加密列管理', 'MenuColumn', 1, 'column_permission', '/menuColumn', '/auth/permission/menuColumn/index', 'auth:menuColumn:list', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1814127259431047169, 1111111111111111111, 1111111111111111111, '开发管理', '', 0, 'dev', '/dev', NULL, NULL, 0, 0, 0, 4, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1814127396630925314, 1111111111111111111, 1814127259431047169, '表结构管理', 'Tables', 1, 'tables', '/tables', '/dev/tables/index', 'gen:db:tables', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1814129777775054850, 1111111111111111111, 1814127396630925314, '字段查询', '', 2, NULL, NULL, NULL, 'gen:db:columns', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1818527209413775362, 1111111111111111111, 1633285580421271553, '查看', '', 2, NULL, NULL, NULL, 'bpm:instance:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1818527280947630081, 1111111111111111111, 1633285580421271553, '删除', '', 2, NULL, NULL, NULL, 'bpm:instance:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1818532399135133698, 1111111111111111111, 1633285580421271553, '挂起', '', 2, NULL, NULL, NULL, 'bpm:instance:suspended', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1820728998888316929, 1111111111111111111, 1581966349440581634, 'todoTask', 'TodoTask', 1, NULL, '/todoTask', '/test/todoTask/index', NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1821000447351885826, 1111111111111111111, 1806941904676159490, '审批', '', 2, NULL, NULL, NULL, 'task:todo:approval', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1823638956944486401, 1111111111111111111, 1806941904676159490, '用户列表', '', 2, NULL, NULL, NULL, 'bpm:user:list', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1823645296190382081, 1111111111111111111, 1806941904676159490, '用户组列表', '', 2, NULL, NULL, NULL, 'bpm:group:list', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1826078313903144962, 1111111111111111111, 1581966349440581634, 'VTable', 'Vtable', 1, NULL, '/vTable', '/test/vTable/index', NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1856576921613942786, 1111111111111111111, 1637297406628823041, '日志管理', '', 0, 'log', '/log', NULL, NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1856578644298481665, 1111111111111111111, 1856576921613942786, '登录日志', 'LoginLog', 1, 'log', '/loginLog', '/system/log/loginLog/index', 'sys:loginLog:list', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1856579291630583810, 1111111111111111111, 1856578644298481665, '清空表', '', 2, NULL, NULL, NULL, 'sys:loginLog:truncate', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1856579490654502913, 1111111111111111111, 1856578644298481665, '详情', '', 2, NULL, NULL, NULL, 'sys:loginLog:info', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1856579646208655362, 1111111111111111111, 1856578644298481665, '删除', '', 2, NULL, NULL, NULL, 'sys:loginLog:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1856583791607070721, 1111111111111111111, 1578702340666851329, '在线用户', 'OnlineUser', 1, 'user', '/onlineUser', '/auth/onlineUser/index', NULL, 0, 0, 0, 6, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1890266759748399105, 1111111111111111111, 1856576921613942786, '审计日志', 'AuditLog', 1, NULL, '/auditLog', '/system/log/auditLog/index', 'sys:auditLog:list', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1890272437051281410, 1111111111111111111, 1890266759748399105, '删除', '', 2, NULL, NULL, NULL, 'sys:auditLog:delete', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', NULL, NULL, '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1890272514473938945, 1111111111111111111, 1890266759748399105, '详情', '', 2, NULL, NULL, NULL, 'sys:auditLog:info', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1890272739901001730, 1111111111111111111, 1890266759748399105, '清空表', '', 2, NULL, NULL, NULL, 'sys:auditLog:truncate', 0, 0, 0, 1, NULL, NULL, '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1912778724000317442, 1111111111111111111, 1814127396630925314, '生成代码', '', 2, NULL, NULL, NULL, 'gen:dev:code', 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', NULL, NULL, '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (1914584039822196738, 1111111111111111111, 1111111111111111111, 'AI', '', 0, NULL, '/ai', NULL, NULL, 0, 0, 0, 1, 'admin', 'admin', '2025-03-02 10:04:48', NULL, NULL, '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775119, 1111111111111111111, 1753664334832967682, '消息公告', 'Msg', 1, 'msg_template', '/msg', '/system/messages/msg/index', 'sys:msg:list', 0, 0, 0, 3, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775120, 1111111111111111111, 1753664334832967682, '用户消息', 'UserMsg', 1, 'user_msg', '/msgUser', '/system/messages/msgUser/index', 'sys:msgUser:list', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775121, 1111111111111111111, 1580357263003439106, '流程定义', 'Definition', 1, 'bpm_definition', '/definition', '/bpm/def/definition/index', 'bpm:definition:list', 0, 0, 0, 2, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_menu` VALUES (9223372036854775807, 1111111111111111111, 1813427994547273729, '行数据权限', 'RowPermission', 1, 'row_permission', '/rowPermission', '/auth/permission/rowPermission/index', 'auth:rowPermission:list', 0, 0, 0, 8, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_menu_column
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_column`;
CREATE TABLE `sys_menu_column`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `menu` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `column` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单字段',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单加密字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu_column
-- ----------------------------
INSERT INTO `sys_menu_column` VALUES (1818470250547298305, 'Platform', 'platformCode', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1822199069229768706, 'Apply', 'procDefKey', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1856584613678071809, 'User', 'displayName', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1856584616802828289, 'User', 'avatar', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1856584770654093313, 'Post', 'postName', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1856584836978622465, 'User', 'email', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007608100585473, 'SysLog', 'logTitle', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007608494850049, 'SysLog', 'logType', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007613637066753, 'SysLog', 'browser', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007618460516354, 'SysLog', 'system', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007622176669697, 'SysLog', 'doType', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007623791476737, 'SysLog', 'requestType', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007626278699010, 'SysLog', 'result', 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_menu_column` VALUES (1924007629801914370, 'SysLog', 'ip', 'admin', 'admin', '2025-03-02 10:04:48');

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '数据权限使用',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息标题',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编码',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 0 通知 1 公告',
  `level` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息级别 error 紧急消息 info 一般消息 warning 警示消息 success 正常消息',
  `user_id` bigint NULL DEFAULT NULL COMMENT '消息发送人',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_msg
-- ----------------------------
INSERT INTO `sys_msg` VALUES (1594154596111454210, NULL, '你好世界', 'Halo3', 1, 'success', NULL, '你好', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_msg` VALUES (1594154596111454211, NULL, '你好世界', 'Halo2', 1, 'warning', NULL, '你好', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_msg` VALUES (1594154596111454212, NULL, '你好世界', 'Halo1', 1, 'error', NULL, '你好', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_msg` VALUES (1595966082236538882, NULL, '你好世界', 'Halo', 1, 'info', NULL, '你好', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_msg_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg_user`;
CREATE TABLE `sys_msg_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `msg_id` bigint NULL DEFAULT NULL COMMENT '消息ID',
  `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '标记已读 0 未读 1 已读',
  `is_close` tinyint(1) NULL DEFAULT 0 COMMENT '标记关闭 0 未关闭 1 已关闭',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '信息所属部门ID（数据权限使用）',
  `user_id` bigint NULL DEFAULT NULL COMMENT '接收消息的用户ID （数据权限使用）',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1897904024105590787 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_msg_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_platform`;
CREATE TABLE `sys_platform`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `platform_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台名称',
  `platform_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_platform
-- ----------------------------
INSERT INTO `sys_platform` VALUES (1111111111111111111, '后台管理中心', 'pc', '后台管理中心', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_platform` VALUES (1580099387022348289, '微信小程序', 'mini', '微信小程序', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `post_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `post_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1591377257933819906, 'CEO', '首席执行官', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_post` VALUES (1630094545759137794, 'BZ', '搬砖', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_post` VALUES (1637420262796746753, 'HR', '人力总监', NULL, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job`  (
  `id` bigint NOT NULL DEFAULT 2 COMMENT '主键ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '任务组名',
  `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `clazz_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '任务类名',
  `misfire_policy` tinyint(1) NULL DEFAULT 1 COMMENT 'misfire策略 1 执行一次（默认）-1 立刻执行 2 放弃执行',
  `concurrent` tinyint(1) NULL DEFAULT 0 COMMENT '是否并发 0 不并发 1 并发',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0 关闭 1 开启',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'quartz任务调度' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
INSERT INTO `sys_quartz_job` VALUES (1565314987957145601, '样例-Bean名五种不同参数', 'DEFAULT', '0/10 * * * * ? *', 'breezeJobs.demoJob(\"test\", 1, 3D, 4L, true, false)', 1, 0, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_quartz_job` VALUES (1565314987957145602, '样例-Bean名调用单个参数', 'DEFAULT', '0/20 * * * * ? *', 'breezeJobs.demoJob(\"test\")', 2, 0, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_quartz_job` VALUES (1565314987957145603, '样例-全类名-五种不同参数', 'DEFAULT', '0/15 * * * * ? *', 'com.breeze.boot.quartz.job.BreezeJobs.demoJob(\"test\", 1, 3D, 4L, true, false)', -1, 0, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_quartz_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job_log`;
CREATE TABLE `sys_quartz_job_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `job_id` bigint NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `cron_expression` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前的cron',
  `clazz_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标类名',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `job_message` varchar(510) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `job_result` tinyint(1) NULL DEFAULT 0 COMMENT '执行结果 0 失败 1 成功',
  `exception_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1912785979408068612 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'quartz任务调度日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_quartz_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_registered_client`;
CREATE TABLE `sys_registered_client`  (
  `id` bigint NOT NULL COMMENT '主键',
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `client_id_issued_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户端发布日期',
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端访问密钥',
  `client_secret_expires_at` datetime NULL DEFAULT NULL COMMENT '客户端加密到期时间',
  `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端使用的身份验证方法；[client_secret_basic, client_secret_post, private_key_jwt, client_secret_jwt, none]',
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端支持的授权许可类型(grant_type)，可选值包括authorization_code,password,refresh_token,client_credentials,注意：password在auth2.1弃用了，我们自定义了【password】【sms_code】，若支持多个授权许可类型用逗号,分隔',
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端重定向URI，当grant_type为authorization_code时, 在Oauth2.0流程中会使用并检查，不在此列将被拒绝，使用IP或者域名，不能使用localhost',
  `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端申请的权限范围，若有多个权限范围用逗号【,】分隔',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_registered_client
-- ----------------------------
INSERT INTO `sys_registered_client` VALUES (1657300993757003778, 'breeze', '2025-07-01 10:25:00', 'CUi+0sdni4FoCqkqfTv0QA==', '2025-12-30 00:00:00', 'breeze', 'client_secret_post,client_secret_basic', 'refresh_token,email_code,password,client_credentials,sms_code,authorization_code,sms_pwd', 'http://127.0.0.1:8080/authorized,http://www.baidu.com,http://127.0.0.1:9000/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/login/oauth2/code/breeze-oidc', 'read,address,user_info,phone,openid,profile,write,email,oidc', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `role_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `row_permission_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识 ALL：全部 DEPT_LEVEL：部门级别 SUB_DEPT_LEVEL：子部门级别 OWN：个人 CUSTOMIZES：自定义（自定义时此字段无意义）',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1565322827518140417, 'ROLE_ADMIN', '超级管理员', 'CUSTOMIZES', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1589074115103707138, 'ROLE_SIMPLE', '普通用户', 'DEPT_LEVEL', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1591282373843464193, 'ROLE_MINI', '小程序游客登录用户', 'OWN', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1644533464768704514, 'ROLE_AUTH', 'Auth登录用户', 'CUSTOMIZES', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1814157530326999042, 'ROLE_DB_CONTROL', '数据权限管理者', 'ALL', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1821474143493505026, 'ROLE_1', '审批角色1', 'ALL', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1821474216864464898, 'ROLE_2', '审批角色2', 'ALL', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1821474319369060354, 'ROLE_3', '审批角色3', 'ALL', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_role` VALUES (1821724954446331906, 'ROLE_4', '审批角色4', 'ALL', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `INDEX_ROLE_ID`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1813449294644875265, 1581966349440581634, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875266, 1773244400852008961, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875267, 1773244571417575425, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875268, 1807352428140244993, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875269, 1793490122163617793, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875270, 1582607135668621314, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875271, 1581965904601088001, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1813449294644875272, 1581965904601088002, 1644533464768704514, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034663710721, 1580357263003439106, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034663710722, 1580357773622202370, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034663710723, 1632642093459935234, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034663710724, 1632642093459935235, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034663710725, 1632642093459935256, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034663710726, 9223372036854775121, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625282, 1795361472646008833, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625283, 1632950163226464257, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625284, 1795343069638156290, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625285, 1632925792583757826, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625286, 1633338860669214722, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625287, 1632925687029903361, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625288, 1633285580421271553, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625289, 1818532399135133698, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625290, 1818527209413775362, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625291, 1818527280947630081, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625293, 1806128900648783874, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625295, 1806941904676159490, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625296, 1821000447351885826, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625297, 1806942129591517186, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625298, 1806942202333331457, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625299, 1806949939763052546, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625300, 1806950505004236802, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625301, 1582554585967800321, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625302, 1664159611618799618, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625303, 1582555155344568321, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625304, 1582558188828790785, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625305, 1581966349440581634, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625306, 1773244400852008961, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625307, 1773244571417575425, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625308, 1807352428140244993, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625310, 1793490122163617793, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625311, 1582607135668621314, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625312, 1820728998888316929, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625313, 1581965904601088001, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1821740034726625314, 1581965904601088002, 1589074115103707138, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133251956738, 1632925687029903361, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871298, 1633285580421271553, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871299, 1818532399135133698, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871300, 1818527209413775362, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871301, 1818527280947630081, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871302, 1806128900648783874, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871303, 1806941904676159490, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871304, 1821000447351885826, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871305, 1823645296190382081, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871306, 1823638956944486401, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871307, 1806942129591517186, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658133314871308, 1806942202333331457, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664257, 1580357263003439106, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664258, 1580357773622202370, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664259, 1632642093459935234, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664260, 1632642093459935235, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664261, 1632642093459935256, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664262, 9223372036854775121, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664263, 1795361472646008833, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664264, 1632950163226464257, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664265, 1795343069638156290, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664266, 1632925792583757826, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664267, 1633338860669214722, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664268, 1632925687029903361, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664269, 1633285580421271553, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171021664270, 1818532399135133698, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578818, 1818527209413775362, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578819, 1818527280947630081, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578821, 1806128900648783874, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578822, 1806941904676159490, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578823, 1821000447351885826, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578824, 1823645296190382081, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578825, 1823638956944486401, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578826, 1806942129591517186, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658171084578827, 1806942202333331457, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326658, 1580357263003439106, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326659, 1580357773622202370, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326660, 1632642093459935234, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326661, 1632642093459935235, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326662, 1632642093459935256, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326663, 9223372036854775121, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326664, 1795361472646008833, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326665, 1632950163226464257, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326666, 1795343069638156290, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326667, 1632925792583757826, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326668, 1633338860669214722, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326669, 1632925687029903361, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326670, 1633285580421271553, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326671, 1818532399135133698, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326672, 1818527209413775362, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326673, 1818527280947630081, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326675, 1806128900648783874, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326676, 1806941904676159490, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326677, 1821000447351885826, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326678, 1823645296190382081, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191888326679, 1823638956944486401, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191959629826, 1806942129591517186, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658191959629827, 1806942202333331457, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644097, 1580357263003439106, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644098, 1580357773622202370, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644099, 1632642093459935234, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644100, 1632642093459935235, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644101, 1632642093459935256, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644102, 9223372036854775121, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644103, 1795361472646008833, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644104, 1632950163226464257, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644105, 1795343069638156290, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644106, 1632925792583757826, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644107, 1633338860669214722, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644108, 1632925687029903361, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644109, 1633285580421271553, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644110, 1818532399135133698, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644111, 1818527209413775362, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644112, 1818527280947630081, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644114, 1806128900648783874, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644115, 1806941904676159490, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644116, 1821000447351885826, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644117, 1823645296190382081, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644118, 1823638956944486401, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644119, 1806942129591517186, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1823658212272644120, 1806942202333331457, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646439869968386, 1578702340666851329, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646441094705154, 1578702340671045634, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646442789203969, 1578702340671045635, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646443909083137, 1761982923168129025, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646445872017410, 1578702340679434241, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646447168057346, 1578702340679434243, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646448543789058, 1578702340683628545, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646450045349890, 1586717542633123841, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646451278475266, 1599935876379897858, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646452788424705, 1599936405688479746, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646454285791233, 1578702340624908290, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646455527305217, 1578702340620713988, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646456768819202, 1587692336744742913, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646458060664833, 1578702340620713987, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646459365093378, 1601081152259891202, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646461298667522, 1578702340683628546, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646462733119490, 1578702340624908293, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646463840415746, 1761982833619738625, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646464956100610, 1578702340624908291, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646466063396866, 1578702340624908292, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646467418157057, 1578702340654268416, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646469116850178, 1589181822230048770, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646470563885057, 1762353074732359681, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646471604072450, 1589181822230048771, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646472589733889, 1589181822230048772, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646473634115585, 1578702340654268418, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646474875629570, 1578702340650074114, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646475945177089, 1762353132718612482, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646477165719554, 1769913478174011393, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646478340124674, 1769932578489217025, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646479439032321, 1771092462060519425, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646480533745665, 1578702340641685505, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646481972391937, 1578702340641685506, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646482991607810, 1856583791607070721, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646484015017986, 1578702340662657026, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646485185228802, 1581843318345035778, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646486233804802, 1761982995855417346, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646487345295362, 1578702340633296899, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646488385482754, 1578702340633296898, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646489425670145, 1578702340654268412, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646490604269570, 1589181822230048781, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646491640262658, 1762353511967580162, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646492672061442, 1589181822230048782, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646493775163394, 1589181822230048783, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646494832128002, 1648569698801393666, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646495872315393, 1657464120406532098, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646496975417346, 1658346521789206529, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646497952690177, 1762353732504084481, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646498984488961, 1657464313466150914, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646500070813698, 1657464432802488321, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646501194887169, 1813427994547273729, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646502411235330, 1813429194697031681, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646503589834753, 1762353207431749631, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646504697131010, 1598222373868695551, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646505804427266, 9223372036854775807, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646506836226049, 1589789746153263106, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646507880607745, 1762353207431749633, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646508996292610, 1598222575933485057, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646513068961793, 1598222373868695553, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646514172063746, 1914584039822196738, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646515136753666, 702418736619065344, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646516223078402, 702418736619065345, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646517196156930, 702418736619065348, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646518299258881, 702418736619065347, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646519406555138, 702418736619065346, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646521679867906, 702418737747333120, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646522707472385, 702418737747333121, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646523873488897, 702418737747333124, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646524976590849, 702418737747333123, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646526020972545, 702418737747333122, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646527061159937, 702418738401644544, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646528109735937, 702418738401644545, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646529087008770, 702418738401644548, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646530261413890, 702418738401644547, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646531289018369, 702418738401644546, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646532262096897, 1637297406628823041, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646533302284289, 1813418484919291905, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646534350860289, 1810587679377408001, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646535399436290, 1811244854244306945, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646536561258497, 1811245062084653057, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646537668554754, 1811245262626910210, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646538830376961, 1811276032536031234, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646539941867522, 1811295279165304834, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646541099495425, 1811295626692751361, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646542143877122, 1811295707286302721, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646543184064514, 1811295805265244162, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646544178114561, 1811295880271982594, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646545205719042, 1812002047382114305, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646546308820993, 1856576921613942786, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646547290288130, 1856578644298481665, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646548280143874, 1856579291630583810, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646549328719873, 1856579490654502913, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646550490542081, 1856579646208655362, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646551526535170, 1890266759748399105, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646552696745985, 1890272437051281410, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646554005368834, 1890272514473938945, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646555121053698, 1890272739901001730, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646556224155649, 1578702340612325378, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646557339840514, 1578702340654268411, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646558388416513, 1762353899760345089, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646559558627329, 1578702340654268417, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646560728838146, 1753664334832967682, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646561727082498, 9223372036854775120, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646562834378754, 1589181822230018782, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646563933286402, 1589181822230048178, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646566051409921, 9223372036854775119, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646567087403010, 1589181822230049781, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646568068870146, 1769605991864541186, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646569239080962, 1772119073333514242, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646570241519618, 1589181822230018781, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646571420119041, 1589181822230048172, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646572523220994, 1594135789623984129, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646573563408386, 1594532038764326666, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646574620372994, 1764834376870072321, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646575752835073, 1594532038764326913, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646576872714241, 1594531945449451666, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646577883541506, 1589181822230048778, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646578848231425, 1594135789623184129, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646579942944770, 1632642093459935231, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646581171875841, 1762353800099487746, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646582384029697, 1637647486464438273, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646584300826626, 1632642093459935232, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646585336819714, 1632642093459935239, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646586368618497, 1578702340612321378, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646587949871106, 1632642093459915239, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646589304631297, 1632642093459935236, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646590764249089, 1578702340662657027, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646592060289025, 1578702340650074115, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646593230499841, 1762353849600663553, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646594551705601, 1578702340650074116, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646596049072129, 1578702340650074117, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646597621936130, 1586717542633123843, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646598632763393, 1762354008057274370, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646599672950786, 1580357263003439106, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646600679583746, 1580357773622202370, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646601749131265, 1632642093459935234, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646602915147777, 1632642093459935235, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646603925975041, 1632642093459935256, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646604945190913, 9223372036854775121, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646605956018177, 1632925687029903361, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646606924902401, 1632925792583757826, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646608158027777, 1632950163226464257, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646609206603777, 1633338860669214722, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646610313900033, 1795343069638156290, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646611316338690, 1795361472646008833, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646612436217857, 1633285580421271553, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646613451239426, 1818527209413775362, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646614462066689, 1818527280947630081, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646615556780033, 1818532399135133698, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646616609550338, 1814127259431047169, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646617754595329, 1814127396630925314, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646618794782722, 1814129777775054850, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646620262789121, 1912778724000317442, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646621227479041, 1806128900648783874, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646622401884162, 1806941904676159490, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646623395934210, 1821000447351885826, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646624394178562, 1823638956944486401, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646625417588737, 1823645296190382081, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646626348724226, 1806942129591517186, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646627367940097, 1806942202333331457, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646628408127490, 1806949939763052546, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646629393788929, 1806950505004236802, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646630429782018, 1582554585967800321, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646631419637761, 1582555155344568321, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646632510156802, 1664159611618799618, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646633537761282, 1582558188828790785, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646634603114497, 1581966349440581634, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646635592970242, 1582607135668621314, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646636591214594, 1773244400852008961, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646637643984897, 1773244571417575425, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646638977773569, 1793490122163617793, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646639984406529, 1807352428140244993, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646641007816706, 1820728998888316929, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646642018643970, 1826078313903144962, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646643016888321, 1581965904601088001, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu` VALUES (1915646644090630146, 1581965904601088002, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');

-- ----------------------------
-- Table structure for sys_role_menu_column
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_column`;
CREATE TABLE `sys_role_menu_column`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `menu` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单列权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu_column
-- ----------------------------
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486852, 'UserRole', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486854, 'Post', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486856, 'Dept', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486858, 'Client', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486860, 'MenuColumn', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486861, 'RowPermission', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486864, 'EmailConfig', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486866, 'UserEmail', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486868, 'Msg', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486869, 'File', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486870, 'Job', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486871, 'JLog', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486872, 'Dict', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486873, 'DictItem', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486874, 'Log', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486875, 'Category', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486876, 'Definition', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486877, 'Instance', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486879, 'Completed', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486880, 'History', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_role_menu_column` VALUES (1818128686977486881, 'Leave', 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');

-- ----------------------------
-- Table structure for sys_role_row_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_row_permission`;
CREATE TABLE `sys_role_row_permission`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `permission_id` bigint NULL DEFAULT NULL COMMENT '规则权限ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_row_permission
-- ----------------------------
INSERT INTO `sys_role_row_permission` VALUES (1891369439775363073, 1770375030740193281, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');

-- ----------------------------
-- Table structure for sys_row_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_row_permission`;
CREATE TABLE `sys_row_permission`  (
  `id` bigint NOT NULL,
  `permission_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `permission_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识 ALL 全部 DEPT_LEVEL 部门 DEPT_AND_LOWER_LEVEL 部门和子部门 OWN 自己 DIY_DEPT 自定义部门 DIY 自定义',
  `customizes_type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'USER DEPT',
  `permissions` json NULL COMMENT '权限',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行级数据权限规则' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_row_permission
-- ----------------------------
INSERT INTO `sys_row_permission` VALUES (1770375030740193281, '用户数据权限测试', 'USER_TEST', 'USER', '[\"1111111111111111111\"]', 'admin', 'admin', '2025-06-21 15:57:04', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_row_permission` VALUES (1770381218118750209, '部门数据权限测试', 'DEPT_TEST', 'DEPT', '[\"1581851971500371970\", \"1601579970948726786\", \"1601579918477983745\"]', 'admin', 'admin', '2025-06-21 15:57:04', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `tenant_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户编码',
  `tenant_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, 'GS', '公司', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0);
INSERT INTO `sys_tenant` VALUES (1643796095560044546, 'FDS', '分公司', 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
  `user_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
  `display_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账户名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `avatar_file_id` bigint NULL DEFAULT NULL COMMENT '头像文件ID',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `post_id` bigint NULL DEFAULT NULL COMMENT '岗位ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `sex` int NULL DEFAULT NULL COMMENT '性别 0 女性 1 男性',
  `id_card` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信OpenID',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '用户类型',
  `is_lock` int NULL DEFAULT 0 COMMENT '锁定',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人编码',
  `update_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  `tenant_id` bigint NOT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1823526671223726083 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1111111111111111111, 'admin', 'admin', '超级管理员', '', NULL, '{bcrypt}$2a$10$e3wi2/FJdX8Fb8FCQUi4UuKEYMQbjaHfLLR5UHmIqUVmZVLk5TG8S', 1591377257933819906, 1581851971500371970, 1, '1231312312', '17812341234', NULL, 'b***********@foxmail.com', NULL, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_user` VALUES (1111111111111111112, 'user1', 'user1', '审批用户1', NULL, NULL, '{bcrypt}$2a$10$e3wi2/FJdX8Fb8FCQUi4UuKEYMQbjaHfLLR5UHmIqUVmZVLk5TG8S', 1630094545759137794, 1581851971500371970, 1, NULL, '17812341235', NULL, 'breeze-cloud@foxmail.com', NULL, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_user` VALUES (1111111111111111113, 'user2', 'user2', '审批用户2', NULL, NULL, '{bcrypt}$2a$10$e3wi2/FJdX8Fb8FCQUi4UuKEYMQbjaHfLLR5UHmIqUVmZVLk5TG8S', 1630094545759137794, 1581851971500371970, 1, NULL, '17812341236', NULL, 'breeze-cloud@foxmail.com', NULL, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_user` VALUES (1111111111111111114, 'user3', 'user3', '审批用户3', NULL, NULL, '{bcrypt}$2a$10$e3wi2/FJdX8Fb8FCQUi4UuKEYMQbjaHfLLR5UHmIqUVmZVLk5TG8S', 1630094545759137794, 1581851971500371970, 1, NULL, '17812341237', NULL, 'breeze-cloud@foxmail.com', NULL, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);
INSERT INTO `sys_user` VALUES (1111111111111111115, 'user4', 'user4', '审批用户4', NULL, NULL, '{bcrypt}$2a$10$e3wi2/FJdX8Fb8FCQUi4UuKEYMQbjaHfLLR5UHmIqUVmZVLk5TG8S', 1630094545759137794, 1581851971500371970, 1, NULL, '17812341238', NULL, 'breeze-cloud@foxmail.com', NULL, 0, 'admin', 'admin', '2025-03-02 10:04:48', 'admin', 'admin', '2025-03-02 10:04:48', 0, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1772137760035819521, 1111111111111111112, 1821474143493505026, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_user_role` VALUES (1821725303441784834, 1111111111111111113, 1821474216864464898, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_user_role` VALUES (1821725349231001602, 1111111111111111114, 1821474319369060354, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_user_role` VALUES (1821725376007438337, 1111111111111111115, 1821724954446331906, 'admin', 'admin', '2025-03-02 10:04:48');
INSERT INTO `sys_user_role` VALUES (1891370826273861634, 1111111111111111111, 1565322827518140417, 'admin', 'admin', '2025-03-02 10:04:48');

-- ----------------------------
-- Table structure for wo_level
-- ----------------------------
DROP TABLE IF EXISTS `wo_level`;
CREATE TABLE `wo_level`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请假标题',
  `reason` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请假原因',
  `start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `is_delete` tinyint(1) NULL DEFAULT NULL,
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'oa请假记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wo_level
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
