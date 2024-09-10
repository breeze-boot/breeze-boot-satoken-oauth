# Breeze-Boot-satoken-oauth

### 平台简介

##### Breeze-boot-satoken-oauth 是基于RBAC的权限管理系统，包括用户管理、角色管理、菜单管理、权限管理，使用bpmnJS绘制Flowable流程图等功能，适合Java开发者入门学习或者直接用此项目。

- 前端采用Vue、Element UI PLUS、VITE、TypeScript、bpmn-process-designer（bpmjs）。
- 后端采用Spring Boot3 JDK17、sa-token（oauth模式）Flowable（审批流程）、Redis、Jwt、Druid。 
- 支持按钮级别的权限控制
- 支持加载动态权限菜单
- 前端代码，请移步
    - [github：vue + vite + TS 版本](https://github.com/Memory1998/breeze-vite-ui.git)
    - [gitee: vue + vite + TS 版本]( https://gitee.com/memoryGiter/breeze-vite-ui)

### 特别鸣谢：
- [验证码： https://gitee.com/anji-plus/captcha](https://gitee.com/anji-plus/captcha)
- [流程设计器： https://gitee.com/MiyueSC/bpmn-process-designer](https://gitee.com/MiyueSC/bpmn-process-designer)
- [流程设计器：VUE3版本 https://gitee.com/xlys998/bpmn-vue3](https://gitee.com/xlys998/bpmn-vue3)
- [cron表达式编辑器：VUE3版本 https://github.com/wuchuanpeng/no-vue3-cron](https://github.com/wuchuanpeng/no-vue3-cron)

# 请遵循 Apache 2.0 协议

### 代码结构

- breeze-boot \
  ├─ breeze-boot \
  │ ├─ src \
  │ │ ├─ src\java\com\breeze\boot\..\modules\bpm    【任务流相关的包】 \
  │ │ ├─ src\java\com\breeze\boot\..\modules\system 【系统相关的包】 \
  │ │ ├─ src\java\com\breeze\boot\..\modules\auth   【权限相关的包】 \
  ├─ breeze-base \
  │ ├─ breeze-base-core \
  │ ├─ breeze-base-log \
  │ ├─ breeze-base-mail \
  │ ├─ breeze-base-oss-s3 \
  │ ├─ breeze-base-sa-token \
  │ ├─ breeze-base-validator \
  │ ├─ breeze-base-message\
  │ ├─ breeze-base-redis-cache\
  │ ├─ breeze-base-mail \
  │ ├─ breeze-base-xss  \
  │ ├─ breeze-base--doc  \
  │ ├─ breeze-...
  ├─ breeze-monitor                                  监控服务（SpringBoot项目）】 \

### 前端界面相关

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




