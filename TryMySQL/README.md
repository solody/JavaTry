# JAVA 连接数据库

JAVA 自带的关系型数据库连接组件，被称作 JDBC 。
关于 JDBC 的知识，可以上网搜索下，到处都是。

使用 JAVA 程序访问数据库时，JAVA 代码并不是直接通过TCP连接去访问数据库，
而是通过 JDBC 接口来访问，而 JDBC 接口则通过JDBC驱动来实现真正对数据库的访问。

JDBC接口是Java标准库自带的，所以可以直接编译。
而具体的JDBC驱动是由数据库厂商提供的，需要额外引入，
例如，连接 MySQL 需要引入由 Oracle 提供的 JDBC 驱动。

因此，访问某个具体的数据库，我们只需要引入该厂商提供的 JDBC 驱动，
就可以通过JDBC接口来访问，这样保证了Java程序编写的是一套数据库访问代码，
却可以访问各种不同的数据库，因为他们都提供了标准的JDBC驱动。

## 连接 MySQL

驱动包是 [mysql/mysql-connector-java](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

我们找到适当的版本，把它引入到 Maven POM 配置中：
```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.26</version>
</dependency>
```

## 使用连接池

