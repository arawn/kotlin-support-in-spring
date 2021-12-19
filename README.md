# Kotlin support in spring
스프링5의 코틀린 지원 기능을 사용해 작성된 예제코드로 당근 SERVER 밋업을 위해 작성되었습니다.
밋업에서 사용된 슬라이드(slide)는 [여기](https://www.slideshare.net/arawnkr/12-2-250865357/arawnkr/12-2-250865357)에 공개되어 있습니다.

[🥕 #살아있다 #자프링외길12년차 #코프링2개월생존기](https://festa.io/events/1985)
* 자프링(자바 + 스프링) 외길 12년차 서버 엔지니어가 코프링(코틀린 + 스프링)을 만난 후
* 코틀린의 특징과 스프링의 코틀린 지원을 알아가며 코프링 월드에서 살아남은 이야기…

## 사용된 코틀린 지원
* Spring Framework
  - Extensions
    - [Extension for PropertyResolver](https://docs.spring.io/spring-framework/docs/5.3.x/kdoc-api/spring-core/org.springframework.core.env/index.html)
    - [Extension for JdbcOperations](https://docs.spring.io/spring-framework/docs/5.3.x/kdoc-api/spring-jdbc/org.springframework.jdbc.core/index.html)
  - [Bean Definition DSL](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#kotlin-bean-definition-dsl)
  - [Null-safety](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#kotlin-null-safety)
  - Web
    - [Router DSL](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#router-dsl)
    - [MockMvc DSL](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#mockmvc-dsl)
  - [Coroutines](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#coroutines)
  - Spring Projects in Kotlin
    - [Final by Default](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#final-by-default)
    - [Testing: Constructor injection](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#constructor-injection)
* [Spring Fu](https://github.com/spring-projects-experimental/spring-fu)
  - KoFu: Kotlin DSL

## 개발환경

- Kotlin 1.5.31
- Frameworks: Spring Boot 2.6.1, Spring Web, Spring Data JDBC
- Tools: Gradle 7.2, IntelliJ IDE 2021.2
