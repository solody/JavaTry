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

### 把资源获取服务注入到对象

- 让目标对象的类实现 `ResourceLoaderAware` 接口。
- 把类通过 `ApplicationContext` 容器实例化，容器会认出`ResourceLoaderAware` 接口，并把自己作为依赖注入到其中。

```java
public interface ResourceLoaderAware {

    void setResourceLoader(ResourceLoader resourceLoader);
}
```

### 把资源直接作为依赖注入

如果资源地址是静态的，我们还可以把资源直接作为依赖注入到所需要的对象内部。

假设我们有这样的一个类：
```java
package example;

public class MyBean {

    private Resource template;

    public setTemplate(Resource template) {
        this.template = template;
    }
}
```

那么我们可以在 `ApplicationContext` 的配置中这样写：
```xml
<bean id="myBean" class="example.MyBean">
    <property name="template" value="some/resource/path/myTemplate.txt"/>
</bean>
```
获者使用 url 前缀：
```xml
<property name="template" value="classpath:some/resource/path/myTemplate.txt"/>
```
```xml
<property name="template" value="file:///some/resource/path/myTemplate.txt"/>
```

如果是使用 Java 注解配置，可以使用 `@Value` 注解从应用配置项读入 url：
```java
@Component
public class MyBean {

    private final Resource template;

    public MyBean(@Value("${template.path}") Resource template) {
        this.template = template;
    }
}
```
资源 url 写在应用的 `.properties` 文件：
```yaml
template:
  path: file:///some/resource/path/myTemplate.txt
```

## 数据绑定、类型转换、验证

提到数据绑定，有前端开发经验的朋友会马上联想到数据与UI的绑定，
不是这个事啊，不要想多了哈。
Spring 的数据绑定是泛指字符串值与对象实例之间的映射。

### Validator

###  DataBinder

### BeanWrapper

BeanWrapper 用来包装一个 Java Bean 对象，用于代理对象属性的读写操作。

```java
public class DataBindingApp {
    public static void main(String[] args) {
        BeanWrapper person  = new BeanWrapperImpl(new Person());
        person.setPropertyValue("name", "Kent");
        System.out.println(person.getPropertyValue("name"));
    }
}
```

### PropertyEditor

当使用数据绑定时，字符串值与对象之前的转换过程是由 `PropertyEditor` 来执行的，
因此这个过程又称为 `Editing`。

Spring 中两个使用 `PropertyEditor` 的主要地方是：
- 把 XML 容器配置文件中的数据转换 `Bean`。比如把XML配置中的类名转换为一个对象实例，把XML中配置的属性文本值设置为 `Bean` 的属性。
- 把 http 请求中的请求参数解析为 MVC framework 的控制器对象。

Spring 自带大量 PropertyEditor，它们全都在 `org.springframework.beans.propertyeditors` 包下，
大部分会被 `BeanWrapperImpl` 默认自动加载，当然开发者也可以编写并注册自定义的 PropertyEditor，
来[覆盖这些默认的行为](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beans-conversion-customeditor-registration )。

### Spring 类型转换

Spring 3 引入了一个类型转换系统，放在 `core.convert` 包下，
这个系统可以把外部文本转换为所需要的特定类型对象。
通过 Spring 容器，这个系统还可以用来代替 PropertyEditor 的工作，
但更多的作用是，把它用到任何你的应用中需要进行类型转换的地方。

这个系统服务的 SPI（Service Provider Interface）：
```java
package org.springframework.core.convert.converter;

public interface Converter<S, T> {

    T convert(S source);
}
```

任何实现这个接口的类都是一个转换器：
```java
package org.springframework.core.convert.support;

final class StringToInteger implements Converter<String, Integer> {

    public Integer convert(String source) {
        return Integer.valueOf(source);
    }
}
```

服务调用 API：
```java
package org.springframework.core.convert;

public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);

    boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType);

    Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType);
}
```

用 XML 方式配置一个类型转换服务：
```xml
<bean id="conversionService"
    class="org.springframework.context.support.ConversionServiceFactoryBean"/>
```
也可以注册额外的转换器：
```xml
<bean id="conversionService"
        class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <bean class="example.MyCustomConverter"/>
        </set>
    </property>
</bean>
```

用编程的方式使用一个类型转换服务，先注入服务：
```java
@Service
public class MyService {

    public MyService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public void doIt() {
        this.conversionService.convert(...);
    }
}
```

另外 [对象字段 `Formatter` 也有着类似的 API](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#format )。

### Java Bean 验证

```java
Foo target = new Foo();
DataBinder binder = new DataBinder(target);
binder.setValidator(new FooValidator());

// bind to the target object
binder.bind(propertyValues);

// validate the target object
binder.validate();

// get BindingResult that includes any validation errors
BindingResult results = binder.getBindingResult();
```

## DAO 统一数据访问接口
## Spring Expression Language (SpEL) 表达式语言
## 视图技术