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

## 重要资源

我们一般会去 https://mvnrepository.com/
搜索 maven 包，然后根据包主页上的指引，把包引用到自己的项目中去。

当我们想知道应用如何使用一个包时，一般是点开包主页上的 HomePage 链接，
就能打开项目的官方主页了，在上面一般能找到相关的使用文档。
大多数包的项目主页都是填写 github 仓库，点开会显示仓库根目录的 README.md 文档。
然而也有很多包根本没有 HomePage 链接，这时，我们可以自行 Google，
找到项目官网文档。 