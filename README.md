## Spring Boot application with JDBC and H2

Prerequisites:

- Java 1.8
- Maven

With Spring Boot, run plain old Java program (`public static void main...`) rather than using a container.

To fire up Spring Boot

```java
public static void main(String[] args) {
		SpringApplication.run(SpringBoot2JdbcWithH2Application.class, args);
	}
```

## Start with command line:
```
java -jar app.jar
```

### Available url:

 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
 
 Make sure that you use jdbc:h2:mem:testdb as JDBC URL.