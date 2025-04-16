# Breeze Boot SaToken OAuth 项目

## 项目简介
Breeze Boot SaToken OAuth 是一个基于 Spring Boot 3 和 SaToken 的 OAuth 认证授权项目，采用 JDK 17 开发。项目提供了完整的认证授权解决方案，并集成了多种常用功能模块。

## 技术栈
### 后端
- **核心框架**: Spring Boot 3
- **安全框架**: SaToken
- **数据库**: 支持Mysql数据库
- **缓存**: Redis
- **任务调度**: Quartz, XXL-JOB
- **验证码**: 集成 [滑动验证码： https://gitee.com/anji-plus/captcha](https://gitee.com/anji-plus/captcha)
- **日志**: 自定义日志模块
- **文档**: 集成文档模块
### 前端
- **前端框架**: Vue 3 + Element Plus + TypeScript
- **状态管理**: Pinia
- **路由管理**: Vue Router
- **构建工具**: Vite
- **API 请求**: Axios
- **图表**: ECharts
- **编辑器**: Monaco Editor
- **文件上传**: 支持多种文件上传方式
- **bpmn-process-designer（bpmjs）**:  [流程设计器： https://gitee.com/MiyueSC/bpmn-process-designer](https://gitee.com/MiyueSC/bpmn-process-designer)

- 前端代码，请移步。
    - [github：vue + vite + TS 版本](https://github.com/breeze-boot/breeze-vite-ui-satoken-oauth.git)
    - [gitee: vue + vite + TS 版本](https://gitee.com/breeze-boot/breeze-vite-ui-satoken-oauth.git)

## 快速开始
1. 克隆项目
   ```bash
   git clone https://github.com/breeze-boot/breeze-boot-satoken-oauth.git
   ```
2.配置环境
  - 安装 JDK 17
  - 配置 Redis
  - 配置数据库
3. 导入数据库
   - 执行 `db/breeze-boot.sql` 文件导入数据库
4. 修改配置
   - 修改 `application.yml` 中的数据库配置
5. 启动项目
   ```bash
   mvn clean install
   cd breeze-boot
   mvn spring-boot:run
   ```

## 功能特性
- 完整的 OAuth 2.0 认证授权流程
- 多租户支持
- 可配置的验证码功能
- 分布式任务调度
- 完善的日志记录
- 监控和健康检查
- 支持按钮级别的权限控制
- 支持加载动态权限菜单

## 模块介绍
### 认证授权模块
- **用户管理**: 支持用户注册、登录、注销等操作
- **角色管理**: 管理用户角色和权限
- **令牌管理**: 管理访问令牌的生成、验证和刷新
- **租户管理**: 管理多租户信息
- **岗位管理**: 管理系统岗位信息
- **角色管理**: 管理系统角色及权限分配
- **部门管理**: 管理组织架构
- **平台管理**: 管理多平台信息
- **在线用户**: 查看当前在线用户
### 系统管理模块
- **字典管理**: 管理系统中的字典数据
- **定时任务**: 管理系统中的定时任务
- **日志管理**: 管理系统日志
- **文件管理**: 管理系统中的文件
### 流程管理
- **流程设计**: 可视化流程设计器
- **流程分类**: 管理流程分类
- **流程实例**: 管理流程实例
- **任务管理**: 管理流程任务
  - **待办任务**: 查看待处理任务
  - **已办任务**: 查看已完成任务
  - **我的提交**: 查看用户提交的任务
- **流程挂起**: 支持流程挂起操作
- **流程审批**: 支持流程审批操作
### 消息管理
- **站内信**: 管理系统内部消息
- **消息公告**: 发布系统公告
- **用户消息**: 管理用户消息
### 开发管理
- **表结构管理**: 管理系统表结构

### 代码结构
```
├── breeze-boot             # 主项目
├── breeze-base
│   ├── breeze-base-mybatis # 数据库访问层
│   ├── breeze-base-validator # 校验器
│   ├── breeze-base-core    # 核心工具类
│   ├── breeze-base-log     # 日志模块
│   ├── breeze-base-sa-token # SaToken集成
│   ├── breeze-base-redis-cache # Redis缓存
│   ├── breeze-base-xxljob  # XXL-JOB集成
├── breeze-bpm              # 流程管理模块
├── breeze-monitor          # 监控模块
├── breeze-quartz           # Quartz任务调度
├── breeze-ai               # AI相关功能
```

### 特别鸣谢：
- [验证码： https://gitee.com/anji-plus/captcha](https://gitee.com/anji-plus/captcha)
- [流程设计器： https://gitee.com/MiyueSC/bpmn-process-designer](https://gitee.com/MiyueSC/bpmn-process-designer)
- [流程设计器：VUE3版本 https://gitee.com/xlys998/bpmn-vue3](https://gitee.com/xlys998/bpmn-vue3)
- [cron表达式编辑器：VUE3版本 https://github.com/wuchuanpeng/no-vue3-cron](https://github.com/wuchuanpeng/no-vue3-cron)

### 前端界面
### 登录页
![login.png](doc/images/login.png)
### 首页
![home.png](doc/images/home.png)
### 布局1
![img_1.png](doc/images/img_1.png)
### 布局2
![img_2.png](doc/images/img_2.png)
### 布局3
![img_3.png](doc/images/img_3.png)
### 布局4
![img_4.png](doc/images/img_4.png)
### flowable bpmn
![flowable bpmn](doc/images/flowable.jpg)

## 开源协议
本项目采用 Apache License 2.0 开源协议，详情请查看 LICENSE 文件。

## 联系方式
如有任何问题，请联系： your-email@example.com

