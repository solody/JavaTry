FROM openjdk:8-jdk-slim

RUN apt -y update

# Install basic tools.
RUN apt -y install ssh rsync net-tools lsof curl

# Install dockerize for container service waiting.
# Hadoop must be complete started up before HiveServer2 start.
RUN curl -sfL $(curl -s https://api.github.com/repos/powerman/dockerize/releases/latest | grep -i /dockerize-$(uname -s)-$(uname -m)\" | cut -d\" -f4) | install /dev/stdin /usr/local/bin/dockerize

# Create hdfs,yarn,hive users with hadoop group.
# These users is used to run appropriate process.
RUN groupadd hadoop && useradd -m -g hadoop hdfs && useradd -m -g hadoop yarn && useradd -m -g hadoop hive

# Add Hadoop and Hive binarys into image.
ADD hadoop-2.8.5 /hadoop
ADD apache-hive-2.3.9-bin /hive
# Remove the hadoop documentation files so it can save the space.
RUN rm -rf /hadoop/share/doc

# Overrides the configs.
COPY core-site.xml /hadoop/etc/hadoop/core-site.xml
COPY hdfs-site.xml /hadoop/etc/hadoop/hdfs-site.xml
COPY yarn-site.xml /hadoop/etc/hadoop/yarn-site.xml
COPY hive-site.xml /hive/conf/hive-site.xml

# Add helper script to init the needed environment variables for hadoop.
ADD hadoop-global-env.sh /etc/profile.d/hadoop-global-env.sh
# Add helper script to start hadoop namenode and datanodes.
ADD start_namenode.sh /hadoop/start_namenode.sh
ADD start_datanode.sh /hadoop/start_datanode.sh
# Add helper script to start hadoop yarn resourcemanager, proxyserver and historyserver.
ADD start_resource_manager.sh /hadoop/start_resource_manager.sh
# Add helper script to start hive.
ADD start_hive.sh /hive/start_hive.sh
# Add helper script to keep container running.
ADD keep_running.sh /hadoop/keep_running.sh
# Add helper script to generate ssh key for non-password ssh access.
ADD generate_ssh_key.sh /hadoop/generate_ssh_key.sh

# Set appropriate permissions.
RUN chown hdfs:hadoop /hadoop && chmod ug+rwx /hadoop
RUN chmod ugo+rx /hadoop/*.sh
RUN chmod ugo+rx /hive/*.sh
RUN chmod ugo+rx /etc/profile.d/hadoop-global-env.sh

# Turn off the host key checking for ssh.
RUN sed -i '$a StrictHostKeyChecking no' /etc/ssh/ssh_config
RUN sed -i '$a UserKnownHostsFile /dev/null' /etc/ssh/ssh_config

# Greante ssh keys for every users.
USER hdfs
RUN /hadoop/generate_ssh_key.sh
USER yarn
RUN /hadoop/generate_ssh_key.sh
USER hive
RUN /hadoop/generate_ssh_key.sh
USER root
RUN /hadoop/generate_ssh_key.sh

# Defind necessary environment variables.
ENV HADOOP_PREFIX=/hadoop
ENV HADOOP_YARN_HOME=/hadoop
ENV HADOOP_CONF_DIR=/hadoop/etc/hadoop
ENV HADOOP_HOME=/hadoop
ENV HIVE_HOME=/hive