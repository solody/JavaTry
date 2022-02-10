先 [安装 Spring 命令行](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.installing.cli.homebrew)

然后创建一个 groove 脚本 `app.groovy`：

```groovy
@RestController
class ThisWillActuallyRun {

    @RequestMapping("/")
    String home() {
        "Hello World!"
    }

}
```

再运行以下命令：

```bash
spring run app.groovy
```

打开 `http://localhost:8080/`