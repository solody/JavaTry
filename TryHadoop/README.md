# Hadoop ecosystem trying

Deploy a hive cluster instance base on hadoop hdfs and yarn.


```bash
$HIVE_HOME/bin/beeline -u jdbc:hive2://localhost:10000/default
INSERT INTO pokes (foo, bar) VALUES(1, 'hello');
```