# Spring Framework

[官方参参考文档](https://docs.spring.io/spring-framework/docs/current/reference/html/)

[中文文档](https://www.cntofu.com/book/95/index.html)

## IoC 容器

`IoC` 的概念请自行网上了解。

简单地说，Spring 会创建一个应用上下文对象 `Application Context`，
这个对象在初始化时会根据给定的配置创建一系列的对象，当应用中的业务代码
需要使用某个对象时，直接向应用上下文对象获取，而不必再去一一创建。

这个应用上下文对象，其实就是 `IoC 容器`，它容纳了一堆可供随时调用的对象。

## 数据绑定、类型转换、验证
## 资源访问
## DAO 统一数据访问接口
## Spring Expression Language (SpEL) 表达式语言
## 视图技术