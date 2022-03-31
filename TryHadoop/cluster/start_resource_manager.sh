#!/usr/bin/env bash

service ssh start

su - yarn -c "$HADOOP_YARN_HOME/sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR start resourcemanager"
su - yarn -c "$HADOOP_YARN_HOME/sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR start proxyserver"
su - yarn -c "$HADOOP_YARN_HOME/sbin/mr-jobhistory-daemon.sh --config $HADOOP_CONF_DIR start historyserver"

$HADOOP_PREFIX/keep_running.sh