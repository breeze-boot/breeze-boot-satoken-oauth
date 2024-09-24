# 基础镜像
FROM openjdk:17

# 作者信息
LABEL maintainer="breeze-cloud@foxmail.com"

# 挂载目录
VOLUME ./breeze

# 创建目录
RUN mkdir -p /usr/local/boot/breeze && \
    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo 'Asia/Shanghai' >/etc/timezone

# 指定路径
WORKDIR /usr/local/boot/breeze

# 复制jar文件到路径
COPY ./target/breeze-boot.jar /usr/local/boot/breeze/breeze-boot.jar

# 启动应用
ENTRYPOINT ["java","-jar","-Xms1024m","-Xmx1024m","-Duser.timezone=GMT+08","breeze-boot.jar","--spring.profiles.active=dev"]
