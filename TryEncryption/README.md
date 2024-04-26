# 数据加密

## 编码算法

- URL 编码
- Base64编码

### URL 编码

```java
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String encoded = URLEncoder.encode("中文!", StandardCharsets.UTF_8);
        System.out.println(encoded);
    }
}
```

```java
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String decoded = URLDecoder.decode("%E4%B8%AD%E6%96%87%21", StandardCharsets.UTF_8);
        System.out.println(decoded);
    }
}
```

### Base64编码

> base64 是一种数据编码技术，并不是加密算法。

```java
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        byte[] input = new byte[]{(byte) 0xe4, (byte) 0xb8, (byte) 0xad};
        String b64encoded = Base64.getEncoder().encodeToString(input);
        System.out.println(b64encoded);
        // Or
        String originalStr = "Hello!";
        byte[] input2 = originalStr.getBytes(StandardCharsets.UTF_8);
        String b64encoded2 = Base64.getEncoder().encodeToString(input2);
        System.out.println(b64encoded2);
    }
}
```

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        byte[] output = Base64.getDecoder().decode("5Lit");
        System.out.println(Arrays.toString(output)); // [-28, -72, -83]
    }
}
```

## 有损加密，不可逆加密

### Hash 哈希算法

又叫摘要算法，是一种有损加密方法。

> https://www.liaoxuefeng.com/wiki/1252599548343744/1304227729113121

```java
import java.math.BigInteger;
import java.security.MessageDigest;

public class Main {
    public static void main(String[] args) throws Exception {
        // 创建一个MessageDigest实例:
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 反复调用update输入数据:
        md.update("Hello".getBytes("UTF-8"));
        md.update("World".getBytes("UTF-8"));
        byte[] result = md.digest(); // 16 bytes: 68e109f0f40ca72a15e05cc22786f8e6
        System.out.println(new BigInteger(1, result).toString(16));
    }
}
```
SHA-1 SHA-256 只需要把 `MessageDigest.getInstance("MD5")` 中的 MD5 简单替换就可以了。

### 无损加密，可逆加密

## 对称加密

> https://www.liaoxuefeng.com/wiki/1252599548343744/1304227762667553

## 非对称加密

> https://www.liaoxuefeng.com/wiki/1252599548343744/1304227873816610