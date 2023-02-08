# Open Feign trying

[OpenFeign/feign](https://github.com/OpenFeign/feign)

如官方文档所说，Feign 是为了使得 RESTFul 接口的调用更加方便。

通常我们要调用 http 接口，会使用序列化和反序列化包，如 Gson，
还要使用 Http Client 包，如 okhttp。
而现在 Feign 已经把它们都整合在一起了，你只要描述一下接口的调用规则，
就可以像在本地调用类方法一样调用远程 http 接口服务了。

## 代码说明

代码分为两部分：
- 一个简单的 Spring Web 应用，创建了两个 http 接口；
- 一个简单的命令行应用，使用 Feign 调用上述的 http 接口。

### Spring Web 应用
入口文件是
`org/example/TryOpenFeign/SpringWebApplication.java`，
然后在控制器 `org/example/TryOpenFeign/controller/HelloController.java` 中定义了两个接口。

### 命令行应用

入口文件是 `org/example/TryOpenFeign/Main.java`，
在 `org/example/TryOpenFeign/MyFeign.java` 接口中，
向 Feign 描述了如何调用 Spring Web 应用中定义的接口，
在 Main 入口的 main 方法中，会创建 MyFeign 的对象实际，
并调用接口。