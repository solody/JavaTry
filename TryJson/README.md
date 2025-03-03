# Json 处理

把 json 字段串转换成 Java 对象，或者把 Java 对象序列化为 json 字段串。
方法应该有很多，这里直接在 [json.org](https://json.org) 找到了官方提供的工具包 `org.json:json`。

此工具包比较简单，提供了两个关键的类来对应 json 对象和数组：
- `org.json.JSONObject`
- `org.json.JSONArray` 

原始数据类型会直接用 `java.lang.*` 包下的类型来对应。

另外比较流行的是 Gson 库，是由 google 提供的一个 json 处理工具。