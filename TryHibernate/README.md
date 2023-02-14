# ORM 技术：JPA 和 Hibernate

> [Hibernate Getting Started Guide](https://docs.jboss.org/hibernate/orm/6.1/quickstart/html_single/)

基本功能只要添加 `org.hibernate:hibernate-core` 依赖就可以了。

- 创建 `hibernate.cfg.xml` 文件：主要是配置JDBC 连接，和 hibernate 的行为选项。
- 创建实体类：需要映射到数据库表的类。
- 为实体类提供映射元信息：指明一个类具体如何映射到数据库表。
- 创建 `SessionFactory` 对象，对实体对象进行数据持久化和检索。
