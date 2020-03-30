## Quick Start

1. `pom.xml`
```xml
<dependency>
    <groupId>com.vczyh</groupId>
    <artifactId>miniframework</artifactId>
    <version>1.1.0</version>
</dependency>
```

2.创建 `mini.properties`

```properties
mini.base = org.example
mini.jsp = /WEB-INF/jsp/
```
3.创建 `TestController`

```java
@Controller
public class TestController {

    @Action("GET:/data")
    public Data test1(Param param) {
        return new Data(param.getParamMap());
    }

    @Action("GET:/view")
    public View test2(Param param) {
        return new View("view.jsp");
    }
}
```
3.启动Tomcat

## 参数说明

| 参数        | 说明                                |
| :---------- | ----------------------------------- |
| mini.base   | 要扫描的包路径                      |
| mini.jsp    | jsp文件的位置，可选                 |
| @Controller | 注明该类接受web路由                 |
| @Action     | 请求方法和请求路径组成的路由        |
| Param       | 封装 `?a=123&b=456 `的Map           |
| Data        | 告诉Servlet将数据按 `json` 格式返回 |
| View        | 告诉Servlet跳转到某个 `jsp` 页面    |

