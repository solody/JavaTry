#!/usr/bin/env bash

service ssh start

su - hdfs -c "$HADOOP_PREFIX/sbin/hadoop-daemons.sh --config $HADOOP_CONF_DIR --script $HADOOP_PREFIX/hdfs start datanode"
su - yarn -c "$HADOOP_YARN_HOME/sbin/yarn-daemons.sh --config $HADOOP_CONF_DIR start nodemanager"

$HADOOP_PREFIX/keep_running.sh