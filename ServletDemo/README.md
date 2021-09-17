# Java web 应用

在 `JDKDemo` 一文中，我们学习的是 Java 命令行应用程序，
而 Web 应用同样是 Java 的拿手好戏，本文不谈 maven gradle 这些构建工具，
不谈 spring 这些框架，使用最单纯的 JavaEE 来学习 Java Web 开发。

## Servlet 和 Web 容器

Java 的 Web 应用，不能像命令行应用那样可以独立运行，必须把其放到一种称为
`Web 容器` 的应用中去运行，最流行的 `Web 容器` 是 `Apache Tomcat`。

`Web 容器` 实现 HTTP 基础设施，接受 HTTP 请求，并把请求转发给
Java Web 应用，Java Web 应用处理请求并返回处理结果给 `Web 容器`，
`Web 容器` 再响应数据给 HTTP 客户端。

`Servlet` 是最重要的 Java Web 应用，本文将开发一个简单的 `Servlet` 应用，
并把它放到 `Apache Tomcat` 中运行。

## 用 Docker 启动 `Apache Tomcat`

新建 `./docker-compose.yml` ：
```yaml
version: '3.1'

services:
  tomcat:
    restart: always
    image: tomcat:jdk15-adoptopenjdk-openj9
    container_name: tomcat
    ports:
      - 8080:8080
    volumes:
      - .:/usr/local/tomcat/webapps/ROOT
    environment:
      TZ: Asia/Shanghai
```
我们把当前目录映射为 `/usr/local/tomcat/webapps/ROOT`，
这个目录将作为 Tomcat 的默认站点目录，我们后面写的 Java Web 应用代码将会放到这里。
命令启动它：
```bash
docker-compose up -d
```
用浏览器访问 `http://localhost:8080`

现在没有放任何代码到 Tomcat 中，所以我们会看到一个 Tomcat 生成的 404 页面。

## Tomcat 作为静态 Web 服务器

Tomcat 实际上具有基本的 Web 服务器功能，像 Nginx 和 Apache 一样，
可以直接在站点目录中放 `html\CSS\JS\图像` 等静态 Web 资源文件，
客户端即可直接访问它们。

我们创建一个 `./indexhtml` 文件：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
hello!
</body>
</html>
```

再次访问 `http://localhost:8080`，看到返回了 `./indexhtml` 文件的内容。

## Tomcat 运行 Servlet 程序

新建目录 `./WEB-INF/classes`，因为Servlet 程序必需放在这个目录下：
```bash
mkdir -p WEB-INF/classes
```

新建 `./WEB-INF/classes/MyServlet.java` 文件：

```java
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;

@WebServlet("/hello")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet MoodServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("hello servlet!");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
```

`javax.servlet.*` 类是由 Java Web 容器提供的，
我们可以在 Tomcat 的 lib 目录中找到 `servlet-api.jar`包，
要成功编译 `MyServlet.java` 我们必须指定该包的路径，
为了可以使用 tomcat 的包进行编译，我们进入 tomcat 容器内部运行编译命令：

```bash
docker-compose exec tomcat bash
javac -cp /usr/local/tomcat/lib/servlet-api.jar webapps/ROOT/WEB-INF/classes/MyServlet.java
```

这时会生成 `WEB-INF/classes/MyServlet.class`，重启 Tomcat，这个 Servlet 程序将会被加载。

访问 `http://localhost:8080/hello`，会看到 Servlet 程序处理了 GET 请求。

> 查看官方文档了解更多关于 Servlet 的知识 https://javaee.github.io/tutorial/servlets.html

## JSP 页面

JSP 是类似 PHP 的一种 Java Web 应用，
本质上它会被容器自动编译为 Servlet 运行。

创建 `./index.jsp` 文件：
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<%="Hello JSP!"%>
</body>
</html>
```

访问 `http://localhost:8080/index.jsp`，Tomcat 会自动编译并运行该文件，
如果修改了 jsp，Tomcat 也会自动重新编译。