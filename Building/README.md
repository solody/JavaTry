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

下面的命令，使用 `maven-archetype-quickstart` 这个 Archetype 创建一个模板工程：
```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```
### 构建过程
[Introduction to the Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)

### 重要资源

我们一般会去 https://mvnrepository.com/
搜索 maven 包，然后根据包主页上的指引，把包引用到自己的项目中去。

当我们想知道应用如何使用一个包时，一般是点开包主页上的 HomePage 链接，
就能打开项目的官方主页了，在上面一般能找到相关的使用文档。
大多数包的项目主页都是填写 github 仓库，点开会显示仓库根目录的 README.md 文档。
然而也有很多包根本没有 HomePage 链接，这时，我们可以自行 Google，
找到项目官网文档。 

## Gradle

Gradle 是一个面向多种语言平台应用的构建工具，
因为被作为 Android 应用的构建工具而被大家熟知。

看看 [Gradle 都可以构建哪些应用](https://docs.gradle.org/current/samples/index.html) 。

现在的 JAVA 应用，基本上都是使用 Gradle 构建，别问为什么，
大家都用那肯定是好东西，用便是了。

Gradle 构建 JAVA 应用，并不是要取代 Maven，
它是继续使用 Maven 的中央仓库来做依赖管理，
融入 Gradle 的工具特性，并对构建过程进行了改进。

最大的变化是，使用 Groove 代码来替代了 XML 配置，
轻便得来同时增加了灵活性。

从这里 [开始学习使用 Gradle](https://docs.gradle.org/current/userguide/getting_started.html) 。

使用 Gradle 创建一个 Java 工程：
```bash
mkdir my-gradle-project
cd my-gradle-project
gradle init
# Gradle v7.0.2 下，依次按提示选择：Application Java no Groovy JUnit4
# 其他版本类似
```

上面创建的工程中，会发现有一个 gradlew 的脚本，
它允许您在工程内使用不同于本机全局安装的 Gradle 版本，
只需要在 `gradle/wrapper/gradle-wrapper.properties`
文件中指定你想使用的版本，它默认指定了和创建工程时的 Gradle 相同的版本。

运行项目：
```bash
./gradlew run
```

构建项目：
```bash
./gradlew build
```

在 `app/build/distributions` 目录下会生成压缩包。
解压后，直接运行 `bin/app` 脚本，即可运行应用。