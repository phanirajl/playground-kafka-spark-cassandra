FROM openjdk:8u222-jre-slim

ARG SPARK_VERSION=2.4.0
ARG HADOOP_VERSION=2.7

RUN apt-get -y update
RUN apt-get -y install curl

# Download, Unzip and Move Apache Spark
RUN curl -L -O https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz && \
    tar -xzf spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz && \
    mv spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} spark-${SPARK_VERSION}

RUN rm -f spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz

ENV SPARK_HOME /spark-2.4.0
ENV JAVA_HOME /usr/local/openjdk-8

WORKDIR $SPARK_HOME
