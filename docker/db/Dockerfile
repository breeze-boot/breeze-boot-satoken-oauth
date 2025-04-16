FROM mysql/mysql-server:8.0.32

MAINTAINER gaoweixuan (breeze-cloud@foxmail.com)

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

COPY ./breeze_boot_17.sql /docker-entrypoint-initdb.d
