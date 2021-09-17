# Java web 应用

在 `JDKDemo` 一文中，我们学习的是 Java 命令行应用程序，
而 Web 应用同样是 Java 的拿手好戏，本文不谈 maven gradle 这些构建工具，
不谈 spring 这些框架，使用最单纯的 JavaEE 来学习 Java Web 开发。

## Servlet 和 Web 容器

Java 的 Web 应用，不能像命令行应用那样可以独立运行，必须把其放到一种称为
`Web 容器` 的应用中去运行，最流行的 `Web 容器` 是 `Apache Tomcat`。

`Web 容器` 实现 HTTP 基础设施，接受 HTTP 请求，并把请求转发给
Java Web 应用，Java Web 应用处理请求并返回处理结果给 `Web 容器`，
`Web 容器` 再响应数据给 HTTP 客户端。

`Servlet` 是最重要的 Java Web 应用，本文将开发一个简单的 `Servlet` 应用，
并把它放到 `Apache Tomcat` 中运行。

## 用 Docker 启动 `Apache Tomcat`