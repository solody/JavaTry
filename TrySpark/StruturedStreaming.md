# Structured Streaming 编程指南

> 本文是 Spark 官方文档 [Structured Streaming Programming Guide](https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html)
的翻译。

## 概述

`Structured Streaming` 是一个基于 `Spark SQL` 引擎构建的流处理引擎，它是可伸缩的，并且具有容错能力。
你可以像表达对静态数据的批处理计算一样去表达对动态流的计算。`Spark SQL` 引擎会帮你增量地持续运行你所表达的计算，当源源不断地流数据到达时，计算结果会不断地被更新。你可以通过 `Scala` `Java` `Python` 或者 `R` 语言来使用 [Dataset/DataFrame API](https://spark.apache.org/docs/latest/sql-programming-guide.html)
表达你的流数据`聚合、事件时间窗口，流到批处量的转换???`，等等。你所表达的流计算会使用相同的已优化的 `Spark SQL` 引擎来运行。最后，系统会通过 `checkpointing` 和 `Write-Ahead logs` 机制保证流计算的 `连续性，一次性，容错性`。
简单地说，`Structured Streaming` 为开发者提供快速、可伸缩、容错、连续的一次性流处理能力，而无需开发者考虑流处理的底层细节。

> [What is Write-Ahead-logging?](https://www.cnblogs.com/bianqi/p/12183538.html)