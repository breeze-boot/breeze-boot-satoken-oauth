FROM centos:centos7

MAINTAINER gaoweixuan

#TODO
ADD ./jdk/jdk-8u202-linux-x64.tar.gz /usr/local/java

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV LC_ALL=en_US.UTF-8
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US.UTF-8

COPY ./fonts/* /usr/share/fonts/
COPY ./fonts/* /usr/local/java/jdk1.8.0_202/jre/lib/fonts/

ENV TIME_ZONE Asia/Shangha
ENV JAVA_HOME /usr/local/java/jdk1.8.0_202
ENV JRE_HOME /usr/local/java/jdk1.8.0_202/jre
ENV PATH $JAVA_HOME/bin:$PATH
