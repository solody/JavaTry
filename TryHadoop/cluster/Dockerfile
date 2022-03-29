FROM openjdk:8-jdk-slim

RUN apt -y update
RUN apt -y install ssh rsync net-tools lsof

RUN groupadd hadoop && useradd -m -g hadoop hdfs && useradd -m -g hadoop yarn

ADD hadoop-2.8.5 /hadoop
RUN rm -rf /hadoop/share/doc

COPY core-site.xml /hadoop/etc/hadoop/core-site.xml
COPY hdfs-site.xml /hadoop/etc/hadoop/hdfs-site.xml

ADD hadoop-global-env.sh /etc/profile.d/hadoop-global-env.sh
ADD hadoop-env.sh /hadoop/etc/hadoop/hadoop-env.sh
ADD start_namenode.sh /hadoop/start_namenode.sh
ADD start_datanode.sh /hadoop/start_datanode.sh
ADD keep_running.sh /hadoop/keep_running.sh
ADD generate_ssh_key.sh /hadoop/generate_ssh_key.sh
ADD su-test.sh /hadoop/su-test.sh

RUN chown hdfs:hadoop /hadoop && chmod ug+rwx /hadoop
RUN chmod ugo+rx /hadoop/*.sh
RUN chmod ugo+rx /etc/profile.d/hadoop-global-env.sh
RUN sed -i '$a StrictHostKeyChecking no' /etc/ssh/ssh_config
RUN sed -i '$a UserKnownHostsFile /dev/null' /etc/ssh/ssh_config

USER hdfs
RUN /hadoop/generate_ssh_key.sh
USER yarn
RUN /hadoop/generate_ssh_key.sh
USER root
RUN /hadoop/generate_ssh_key.sh

ENV HADOOP_PREFIX=/hadoop
ENV HADOOP_CONF_DIR=/hadoop/etc/hadoop