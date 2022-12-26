# JDK 管理

开发时，常常需要在不同版本的 JDK 间进行切换。
JDK 下载解压后，本质上就是一个目录，里面包含了一堆可执行程序。

### 环境变量
- `PATH` 这是 Linux 系统本身就有的环境变量，作用是，当用户不指定目录运行某个命令时，系统会尝试在这个 变量中列出的目录中去查找相应的可执行文件来执行。所以如果希望可以直接不指定目录地运行 JDK 中的可执行文件时，那么需要把 JDK 的 bin 目录加入到 `PATH` 这个环境变量中去。
- `JAVA_HOME` 很多基于 Java 开发的软件会根据这个环境变量去查找 JDK，所以设置一下是很有必要的。
- `CLASSPATH` 作用类同 java 命令的 --classpath 参数，设不设都没关系。

### update-alternatives

这是 Linux 下用来管理软连接的工具命令。用于处理 Linux 系统中软件版本的切换，使其多版本共存。

```
$ update-alternatives --help
用法：update-alternatives [<选项> ...] <命令>

命令：
  --install <链接> <名称> <路径> <优先级>
    [--slave <链接> <名称> <路径>] ...
                           在系统中加入一组候选项。
  --remove <名称> <路径>   从 <名称> 替换组中去除 <路径> 项。
  --remove-all <名称>      从替换系统中删除 <名称> 替换组。
  --auto <名称>            将 <名称> 的主链接切换到自动模式。
  --display <名称>         显示关于 <名称> 替换组的信息。
  --query <名称>           机器可读版的 --display <名称>.
  --list <名称>            列出 <名称> 替换组中所有的可用候选项。
  --get-selections         列出主要候选项名称以及它们的状态。
  --set-selections         从标准输入中读入候选项的状态。
  --config <名称>          列出 <名称> 替换组中的可选项，并就使用其中哪一个，征询用户的意见。
  --set <名称> <路径>      将 <路径> 设置为 <名称> 的候选项。
  --all                    对所有可选项一一调用 --config 命令。

<链接> 是指向 /etc/alternatives/<名称> 的符号链接。(如 /usr/bin/pager)
<名称> 是该链接替换组的主控名。(如 pager)
<路径> 是候选项目标文件的位置。(如 /usr/bin/less)
<优先级> 是一个整数，在自动模式下，这个数字越高的选项，其优先级也就越高。
..........
```

#### 注册 --install

当向 update-alternatives 注册连接时，会在 `/etc/alternatives` 目录下生成一个软连接，
这个软件指会指向 `<路径>`。然后再创建一个软连接 `<链接>`，指向 `/etc/alternatives/*` 的软连接记录。

#### 切换 --config

当使用 update-alternatives 切换当前使用的版本时，会更改 软连接 `<链接>` 所指向的`/etc/alternatives/*` 的软连接记录。

### 用 update-alternatives 管理 JDK

update-alternatives 既然是管理软连接的，那么无论是文件还是目录都可以用它管理。

然后把JAVA_HOME 变量指向 `<链接>` 就可以了。