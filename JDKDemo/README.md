## javac 编译运行

创建代码类 `./Hello.java`：
```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
```

运行JDK的 `javac` 命令编译：
```bash
javac Hello.java
```

结果在当前目录下生成一个 `./Hello.class` 字节码类，
它可以直接由 java 命令行执行：

```bash
# 此命令会在当前目录下查找 Hello.class 文件并执行它
java Hello
# 将输出 Hello!
```

## 在一个类中使用其它类

创建一个新类 `./People.java`：

```java
public class People {
    private String name;

    People(String name) {
        this.name = name;
    }
    public People setName(String name) {
        this.name = name;
        return this;
    }
    public String getName() {
        return this.name;
    }
}
```
编译它：
```bash
javac People.java
```
生成 `./People.class` 字节码类。

现在回到 `./Hello.java` 中，添加两行新代码：

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello!");

        // 实例化一个 People 类，并使用它
        People people = new People("小明");
        System.out.println("Hello! " + people.getName());
    }
}
```

重新编译并运行它：
```bash
javac Hello.java
java Hello
```
结果输出：
```bash
Hello!
Hello! 小明
```

在这次的 `java Hello` 命令运行中，jvm 会在当前目录下查找 People 类，
并把它引用到 Hello 类中使用。

## 类的查找路径 Class Path

现在我们尝试把上文中的 `./People.class` 和 `./People.java`
移到 `./cp` 或其它任意目录：

```bash
mkdir cp
mv People.java ./cp/People.java
mv People.class ./cp/People.class
```

并重新编译 `./Hello.java`：

```bash
javac Hello.java
```

这时会提示找不到 `People` 类，这时可以指定类查找目录：

```bash
javac -cp ./cp Hello.java
```

编译通过，但执行时要同时指定两个类查找目录，不然还是会找不到类：
```bash
# 同时指定 当前目录和 cp 目录
java -cp ./:./cp Hello
```

## 包 package

从上面看到，把类文件分放到不同的目录中，编译和运行的时候就会特别麻烦，
明显不利于开发和管理。
但类文件很多时，把它们都放在同一个目录，看起来也会很乱，
于是 java 提供了包(package)的概念。

我们把上面的 `People.java` 改一下，在文件的开头添加一行：
```java
package my;
```

同时把 `People.java` 文件所在的目录 `cp` 重命命为 `my`，
这样我们就得到了一个名为 `my` 的包，这个包中有一个 `People` 类，
这个类的全名现在叫 `my.People`。

重新编译这个类：
```bash
javac my/People.java
```
结果会发现编译出来的 `People.class` 文件会被自动放到 `my` 目录下。
现在我们回到 `Hello.java` 类，把：
```java
People people = new People("小明");
```
改为：
```java
my.People people = new my.People("小明");
```

重新编译 `Hello.java`：
```bash
javac Hello.java
```
这时会提示 `my.People` 类的构造方法必须声明为 `public`，才能被引用了，
所以我们再改一下 `People.java`：
```java
public People(String name) {
    this.name = name;
}
```
并重新编译两个类：
```bash
javac my/People.java
javac Hello.java
```
运行：
```bash
java Hello
```

我们发现，不再需要指定 classpath，jvm 会在当前目录下自动查找包和其中的类。

## 把 class 打包为 jar

为了方便应用程序的分发和管理，java 提供了把编译后的多个class文件打包为单个
jar 文件的机制。

在打包前，我们先写一个文本文件 `mainfest.mf`，作为 jar 文件的类清单：

```text
Manifest-Version: 1.0
Main-Class: Hello
Created-By: Kent
```
这里起主要作用的是第二行，它指定了 jar 文件的主类，主类将作为 jar 文件执行的入口。

然后通过 JDK 提供的 jar 命令打包
```bash
jar --create \
--file Hello.jar \
--manifest mainfest.mf \
Hello.class my/People.class
```
运行 jar 包：
```bash
java -jar Hello.jar
```

对于 jar 打包命令的更多用法，请查看它的命令行帮助：
```bash
jar --help
```