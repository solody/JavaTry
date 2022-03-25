FROM openjdk:8-jdk-slim

RUN apt -y update
RUN apt -y install ssh pdsh net-tools lsof
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
RUN cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
RUN chmod 0600 ~/.ssh/authorized_keys

ADD hadoop-3.3.2 /hadoop
RUN rm -rf /hadoop/share/doc
COPY core-site.xml /hadoop/etc/hadoop/core-site.xml
COPY hdfs-site.xml /hadoop/etc/hadoop/hdfs-site.xml

ADD startup.sh /hadoop/startup.sh
