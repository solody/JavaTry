FROM openjdk:8-jdk-slim

RUN apt -y update
RUN apt -y install ssh rsync net-tools lsof curl
RUN curl -sfL $(curl -s https://api.github.com/repos/powerman/dockerize/releases/latest | grep -i /dockerize-$(uname -s)-$(uname -m)\" | cut -d\" -f4) | install /dev/stdin /usr/local/bin/dockerize

RUN groupadd hadoop && useradd -m -g hadoop hdfs && useradd -m -g hadoop yarn && useradd -m -g hadoop hive

ADD hadoop-2.8.5 /hadoop
ADD apache-hive-2.3.9-bin /hive
RUN rm -rf /hadoop/share/doc

COPY core-site.xml /hadoop/etc/hadoop/core-site.xml
COPY hdfs-site.xml /hadoop/etc/hadoop/hdfs-site.xml
COPY yarn-site.xml /hadoop/etc/hadoop/yarn-site.xml
COPY hive-site.xml /hive/conf/hive-site.xml

ADD hadoop-global-env.sh /etc/profile.d/hadoop-global-env.sh
ADD hadoop-env.sh /hadoop/etc/hadoop/hadoop-env.sh
ADD start_namenode.sh /hadoop/start_namenode.sh
ADD start_datanode.sh /hadoop/start_datanode.sh
ADD start_hive.sh /hive/start_hive.sh
ADD keep_running.sh /hadoop/keep_running.sh
ADD generate_ssh_key.sh /hadoop/generate_ssh_key.sh
ADD start_resource_manager.sh /hadoop/start_resource_manager.sh
ADD su-test.sh /hadoop/su-test.sh

RUN chown hdfs:hadoop /hadoop && chmod ug+rwx /hadoop
RUN chmod ugo+rx /hadoop/*.sh
RUN chmod ugo+rx /hive/*.sh
RUN chmod ugo+rx /etc/profile.d/hadoop-global-env.sh
RUN sed -i '$a StrictHostKeyChecking no' /etc/ssh/ssh_config
RUN sed -i '$a UserKnownHostsFile /dev/null' /etc/ssh/ssh_config

USER hdfs
RUN /hadoop/generate_ssh_key.sh
USER yarn
RUN /hadoop/generate_ssh_key.sh
USER hive
RUN /hadoop/generate_ssh_key.sh
USER root
RUN /hadoop/generate_ssh_key.sh

ENV HADOOP_PREFIX=/hadoop
ENV HADOOP_YARN_HOME=/hadoop
ENV HADOOP_CONF_DIR=/hadoop/etc/hadoop
ENV HADOOP_HOME=/hadoop
ENV HIVE_HOME=/hive