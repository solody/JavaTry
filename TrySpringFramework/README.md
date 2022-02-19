# Spring Framework

[官方参参考文档](https://docs.spring.io/spring-framework/docs/current/reference/html/)

[中文文档](https://www.cntofu.com/book/95/index.html)

## IoC 容器

`控制反转 IoC (Inversion of Control)` 
又被称为 `依赖注入 dependency injection (DI)` ，
其概念请自行网上了解。

简单地说，Spring 会创建一个应用上下文对象 `Application Context`，
这个对象在初始化时会根据给定的配置创建一系列的对象，当应用中的业务代码
需要使用某个对象时，直接向应用上下文对象获取，而不必再去一一创建。

实例化一个简单对象的成本可能不高，但是如果这个要实例化的对象中包含大量其他对象，
而且这些其他对象同时也可能要依赖更多的其他对象，这时创建对象的成本就很高了。
正因为这样 `IoC` 又称为 `DI` 依赖注入。

这个应用上下文对象，其实就是 `IoC 容器`，它容纳了一堆可供随时调用的对象。

创建应用上下文对象可以
[使用 XML 配置](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-instantiation )，
也可以 
[使用代码配置](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java )：
```java
class MyApp {
    public static void main(String[] args) {
        // 使用 XML 配置
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
        // 使用 Java 类配置
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
```
## 资源访问

通常 Java 使用标准库中的 `java.net.URL` 类来处理资源访问，它本身可以处理多种前缀的 url 的读取任务，
比如 `http` `https` `file` `ftp` 等，而且同时还能对其进行扩展，以支持更多前缀。

但是 `java.net.URL` 对资源类型的支持还是不足够全面，比如不能读取 `Class Path` 资源，
而且它的扩展方法也比较麻烦，而且它也缺少一些功能，比如检查 url 指向的资源是否存在。

所以 Spring 提供了一个资源抽象层来解决这个问题。

可以调用 `ApplicationContext` 对象的 `getResource()` 方法来使用 Spring 的资源抽象层进行资源访问：

```java
class MyApp {
    public static void main(String[] args) {
        Resource template = ctx.getResource("classpath:some/resource/path/myTemplate.txt");
        Resource template = ctx.getResource("file:///some/resource/path/myTemplate.txt");
        Resource template = ctx.getResource("https://myhost.com/resource/path/myTemplate.txt");

        // 读取文件内容
        Resource resource = context.getResource("https://www.baidu.com/index.php");
        Scanner sanner = new Scanner(resource.getInputStream());
        while (sanner.hasNextLine()) {
            System.out.println(sanner.nextLine());
        }
    }
}
```

`getResources()` 方法可以使用 `通配符(*)` 来一次读取多个符合条件的资源：
```java
class MyApp {
    public static void main(String[] args) {
        Resource[] templates = ctx.getResources("https://abc.com/*/myTemplate.txt");
    }
}
```

### 把资源作为依赖注入

todo: 需要理解数据绑定作为前提

## 数据绑定、类型转换、验证

Validator

DataBinder

BeanWrapper

```java
public class DataBindingApp {
    public static void main(String[] args) {
        BeanWrapper person  = new BeanWrapperImpl(new Person());
        person.setPropertyValue("name", "Kent");
        System.out.println(person.getPropertyValue("name"));
    }
}
```

PropertyEditor

## DAO 统一数据访问接口
## Spring Expression Language (SpEL) 表达式语言
## 视图技术