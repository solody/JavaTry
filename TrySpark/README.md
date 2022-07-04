# Spark

[Official Site](https://spark.apache.org/docs/latest/)

首先，得先有一个用于运行 Spark 任务的计算机集群。

- `Spark 原生计算集群` 可以按[官方文档](https://spark.apache.org/docs/latest/spark-standalone.html)来搭建一个。
- `Hadoop YARN` 如果你已经有一个 hadoop 的集群，可以把它集成到 Spark 来运行任务。

如果是开发调试，可以使用本地多线程运行 `spark` 任务。

## 命令行交互环境

一般来说，我们会把 spark 任务写成代码，想直接在命令行中操作数据，可以参考 [Interactive Analysis with the Spark Shell
Basics](https://spark.apache.org/docs/latest/quick-start.html#interactive-analysis-with-the-spark-shell)。

但个人认为这没什么用，浪费时间。

## 自包含应用

与上面提到的 `命令行交互环境` 的操作方式相对的，是把 Spark 任务写成代码，官方文当把这种代码叫 `Self-Contained Applications`。

支持 `scala` `python` `java` 语言编写，但这里只关注 `java`。

## 一个简单的 Java Spark 应用

https://spark.apache.org/docs/latest/quick-start.html#self-contained-applications

## RDD 计算

RDD for `resilient distributed dataset`。
是Spark 核心提供的基本分布式计算模型，我们可以把任意需要处理的数据转换为一个 RDD 对象，
然后对该对象进行数据操作，从而实现对数据的处理。

## Spark SQL

从 `SQL` 兼容数据库读取数据集。

## Spark Streaming

从外部流式服务读取数据集，比如 Kafka。