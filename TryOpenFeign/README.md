# Open Feign trying

[OpenFeign/feign](https://github.com/OpenFeign/feign)

如官方文档所说，Feign 是为了使得 RESTFul 接口的调用更加方便。

通常我们要调用 http 接口，会使用序列化和反序列化包，如 Gson，
还要使用 Http Client 包，如 okhttp。
而现在 Feign 已经把它们都整合在一起了，你只要描述一下接口的调用规则，
就可以像在本地调用类方法一样调用远程 http 接口服务了。