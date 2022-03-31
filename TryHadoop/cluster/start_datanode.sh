#!/usr/bin/env bash

service ssh start

# echo java-home is $JAVA_HOME

chown hdfs:hadoop /home/hdfs/dfs && chmod 0755 /home/hdfs/dfs

su - hdfs -c "$HADOOP_PREFIX/sbin/hadoop-daemons.sh --config $HADOOP_CONF_DIR --script $HADOOP_PREFIX/hdfs start datanode"
su - yarn -c "$HADOOP_YARN_HOME/sbin/yarn-daemons.sh --config $HADOOP_CONF_DIR start nodemanager"

$HADOOP_PREFIX/keep_running.sh