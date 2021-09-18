#  构建 Java 应用程序

构建，指的是把 java 代码编译、打包成最终应用程序的一系列过程。
从 `JDKDemo` 和 `ServletDemo` 两文的学习中，
我们发现这是一个很麻烦的过程。

为了把构建过程变得容易，大家创建了专门的构建工具：
- `Apache ant` 已经过时了，忽略它，如果实在遇到旧项目在使用，就去网上自行了解吧。
- `Apache Maven` 应用最广泛，最流行的构建工具。
- `gradle` 基于 Maven 进行了改进，越来越流行。

## Apache Maven
最好的文档是，Maven 官方网站上的
[Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
。

### 源码目录结构
官方参考
[Introduction to the Standard Directory Layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
### POM
[Introduction to the POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
### 项目工程模板
[Maven Archetype Plugin](https://maven.apache.org/archetype/maven-archetype-plugin/index.html)
### 构建过程
[Introduction to the Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)