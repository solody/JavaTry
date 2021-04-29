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

现在我们尝试把上文中的 `./People.class` 和 `./People.java` 移到 `./cp` 或基它任意目录，

```bash
mkdir cp
mv People.java ./cp/People.java
mv People.class ./cp/People.class
```

并重新编译 `./Hello.java`：

```bash
java Hello.java
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

## 把 class 打包为 jar