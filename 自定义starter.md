# 自定义starter
## 1. 创建自定义Starter项目

1. **创建Maven项目**：创建一个新的Maven项目，通常命名为`your-starter-name-spring-boot-starter`。
2. **定义核心依赖**：在`pom.xml`中添加Spring Boot相关依赖以及其他必要的依赖。
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>

    <artifactId>spring-boot-autoconfigure</artifactId>

</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>

    <artifactId>spring-boot-configuration-processor</artifactId>

    <optional>true</optional> <!-- 用于生成配置元数据 -->
</dependency>


```
## 2. 编写自动配置类

1. **创建配置类**：在`src/main/java`下创建一个自动配置类，并使用`@Configuration`和`@ConditionalOn...`注解来控制配置的加载。
```java
@Configuration
@ConditionalOnClass(SomeClass.class)  // 当类路径下存在SomeClass时才加载
@EnableConfigurationProperties(YourProperties.class)  // 绑定属性
public class YourAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean  // 当容器中没有相同类型的Bean时才创建
    public SomeService someService() {
        return new SomeService();
    }
}
```

2. **编写配置属性类**：定义一个`@ConfigurationProperties`类来封装用户可以配置的属性。
```java
@ConfigurationProperties(prefix = "your.starter")
public class YourProperties {
    private String property1;
    private int property2;

    // getters and setters
}
```
## 3. 添加Spring Factories文件

1. **创建**`spring.factories`**文件**：在`src/main/resources/META-INF`目录下创建`spring.factories`文件，声明自动配置类。
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.yourstarter.YourAutoConfiguration
```

## 4.使用自定义的starter
```xml
<dependency>
    <groupId>com.lt</groupId>

    <artifactId>your-starter-name-spring-boot-starter</artifactId>

    <version>1.0.0</version>

</dependency>

```


## 5、案例
案例功能：自定义注解实现参数校验，并打印相关的日志信息
> #### 相关自定义注解实现参数校验代码地址：[https://github.com/rookiesnewbie/annotation](https://github.com/rookiesnewbie/annotation)

### 5.1、创建自定义Starter项目
![image-20240828131952316.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824582533-8b626fcb-624b-41e2-bfbf-a93cf4bf6778.png#averageHue=%23212932&clientId=u3879bfd2-9f15-4&from=ui&id=gfwAH&originHeight=642&originWidth=767&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=49375&status=done&style=none&taskId=u71f13789-44af-4930-91f7-fa162550673&title=)
### 5.2、导入相关的maven依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>

        <artifactId>spring-boot-starter-web</artifactId>

        <version>2.2.2.RELEASE</version>

    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>

        <artifactId>spring-boot-starter-aop</artifactId>

        <version>2.2.2.RELEASE</version>

    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>

        <artifactId>lombok</artifactId>

        <version>1.18.34</version>

    </dependency>

    
    <dependency>
        <groupId>org.springframework.boot</groupId>

        <artifactId>spring-boot-autoconfigure</artifactId>

        <version>2.2.2.RELEASE</version>

    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>

        <artifactId>spring-boot-configuration-processor</artifactId>

        <version>2.2.2.RELEASE</version>

        <optional>true</optional> <!-- 用于生成配置元数据 -->
    </dependency>

</dependencies>

```

### 5.3、编写自动配置类
```java
@Configuration
@Import(LogProperties.class)
@ConditionalOnClass({ControllerLogAspect.class, GlobalOperationAspect.class})
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean  //没有ControllerLogAspect bean的时候创建
    public ControllerLogAspect controllerLogAspect(){
       return new ControllerLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalOperationAspect globalOperationAspect(){
        return new GlobalOperationAspect();
    }
}
```
### 5.4、编写`spring.factories`文件
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.lt.autoconfigure.LogAutoConfiguration
```

### 5.5、使用自定义的starter
#### 5.5.1、创建一个新的工程使用自定义的starter
![image-20240828132501948.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824629647-c3124e68-e89b-49b0-99e8-fa39249a5215.png#averageHue=%23212933&clientId=u3879bfd2-9f15-4&from=ui&id=u26f4021e&originHeight=447&originWidth=799&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=34673&status=done&style=none&taskId=u0a2d1e48-f6d6-4fe4-ae9c-202690014ea&title=)
#### 5.5.2、导入自定义starter
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>

        <artifactId>spring-boot-starter-web</artifactId>

        <version>2.2.2.RELEASE</version>

    </dependency>

    <dependency>
        <groupId>com.lt</groupId>

        <artifactId>log-validation-boot-starter</artifactId>

        <version>1.0.0</version>

    </dependency>

    
    <dependency>
        <groupId>org.projectlombok</groupId>

        <artifactId>lombok</artifactId>

        <version>1.18.34</version>

        <scope>compile</scope>

    </dependency>

</dependencies>

```

#### 5.5.3、测试结果
1、测试自定义注解是否生生效
![image-20240828132836470.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824655191-bd0f92da-6b35-4311-8ff5-d8d132305989.png#averageHue=%23fdfcfc&clientId=u3879bfd2-9f15-4&from=ui&id=u33b34dd7&originHeight=561&originWidth=679&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=47006&status=done&style=none&taskId=u6356cae9-d700-4b9f-86eb-e5012ad5710&title=)
![image-20240828132959596.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824669473-880bd230-77ca-4713-8bb6-f762b491e1f5.png#averageHue=%23fcfcfc&clientId=u3879bfd2-9f15-4&from=ui&id=ucaf8a702&originHeight=549&originWidth=1378&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=53997&status=done&style=none&taskId=ue93e89e6-d0a2-4f9a-a3d2-1c31c46846c&title=)

![image-20240828133442231.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824683130-14002870-2cfa-4820-89c2-a74ce50ab021.png#averageHue=%23fdfdfc&clientId=u3879bfd2-9f15-4&from=ui&id=ufc47e9bb&originHeight=549&originWidth=693&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=34417&status=done&style=none&taskId=uc2a9dd92-afc6-422a-8a49-d342d7c5e2d&title=)
![image-20240828135903271.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824754509-502482ee-7b8b-4b45-916d-2310cc2c81df.png#averageHue=%23fdfcfc&clientId=u3879bfd2-9f15-4&from=ui&id=u287cce7b&originHeight=727&originWidth=721&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=47742&status=done&style=none&taskId=u7ed3306d-3af0-464b-9cad-bb7ea733801&title=)
2、测试日志输出
![image-20240828133631983.png](https://cdn.nlark.com/yuque/0/2024/png/29648551/1724824705186-b78a73e2-eef5-43b7-80df-8da6e6f77c87.png#averageHue=%2325313b&clientId=u3879bfd2-9f15-4&from=ui&id=ued7885b5&originHeight=767&originWidth=1816&originalType=binary&ratio=1.100000023841858&rotation=0&showTitle=false&size=336555&status=done&style=none&taskId=uc944393e-932d-41b4-aebf-6a2e45f2271&title=)

> ### 案例代码地址：[https://github.com/rookiesnewbie/boot-starter](https://github.com/rookiesnewbie/boot-starter)

