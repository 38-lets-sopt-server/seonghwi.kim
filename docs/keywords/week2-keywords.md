# 2 주차 키워드 과제

## 1. HTTP의 멱등성(Idempotency)이란 무엇인가요? `GET`, `POST`, `PUT`, `DELETE` 중 멱등한 메서드는 무엇인가요?

**멱등성**이란 같은 요청을 한 번 보내든 여러 번 보내든 서버의 최종 상태가 동일한 성질을 의미합니다.

예를 들어,  
게시글 조회 요청을 여러 번 보내도 데이터가 변경되지 않으므로 `GET`은 멱등합니다.  
게시글 삭제 요청도 같은 게시글을 여러 번 삭제 요청하더라도 최종적으로는 “삭제된 상태”가 유지되므로 `DELETE`는 멱등하다고 볼 수 있습니다.

`PUT`은 리소스 전체를 수정하거나 대체할 때 사용하는 메서드입니다.  
같은 내용으로 여러 번 `PUT` 요청을 보내더라도 리소스는 동일한 상태로 유지되기 때문에 멱등합니다.

반면 `PATCH`는 리소스의 일부만 수정할 때 사용하는 메서드입니다.  
`따라서 `PATCH`는 멱등성을 보장하도록 설계할 수도 있지만, 항상 멱등성이 보장되는 메서드는 아닙니다.

예를 들어 게시글 제목을 특정 값으로 변경하는 `PATCH` 요청은 여러 번 보내도 결과가 같으므로 멱등하다고 볼 수 있습니다.

하지만 조회수를 1 증가시키는 요청처럼 현재 상태를 기준으로 값을 변경하는 `PATCH` 요청은 여러 번 보내면 결과가 계속 달라질 수 있으므로 멱등하지 않습니다.

따라서 `PATCH`는 멱등성 보장하게 설계할 수도 있지만, 멱등성을 보장하지 않게 설계할 수 있습니다.

HTTP 메서드별 멱등성은 다음과 같습니다.

- GET: 멱등함
- DELETE: 멱등함
- PUT: 멱등함


- PATCH: 일반적으로 멱등하지 않음
- POST: 멱등하지 않음

`POST`는 게시글 작성처럼 새로운 리소스를 생성하는 요청에 사용되기 때문에 같은 요청을 여러 번 보내면 게시글이 여러 개 생성될 수 있습니다.

---

## 2. `@Controller`와 `@RestController`의 차이는 무엇인가요?

`@Controller`는 Spring MVC에서 Controller 역할을 하는 클래스를 표시할 때 사용하는 어노테이션입니다.  
주로 HTML View를 반환하는 웹 페이지 Controller에서 사용됩니다.

`@Controller`를 사용하면 메서드가 반환하는 문자열은 기본적으로 View 이름으로 해석될 수 있습니다.  
예를 들어 `"posts"`를 반환하면 `posts.html` 같은 View를 찾아 렌더링하는 방식으로 동작할 수 있습니다.
```java
@Controller
public class PageController {

    @GetMapping("/posts-page")
    public String postsPage() {
        return "posts"; // posts.html 같은 View 이름으로 해석
    }
}
```

반면 `@RestController`는 REST API를 만들 때 주로 사용하는 어노테이션입니다.  
`@RestController`는 `@Controller`와 `@ResponseBody`가 합쳐진 형태입니다.
```java
@RestController
public class PostController {

    @GetMapping("/posts")
    public List<PostResponse> getPosts() {
        return posts; // HTTP 응답 Body에 JSON 형태로 반환
    }
}
```
따라서 `@RestController`를 사용하면 각 메서드에 `@ResponseBody`를 따로 붙이지 않아도, 메서드의 반환값이 View 이름으로 해석되지 않고 HTTP 응답 Body에 직접 담겨 클라이언트에게 전달됩니다.

예를 들어 객체를 반환하면 Spring의 HTTP Message Converter에 의해 일반적으로 JSON 형식으로 변환되어 응답됩니다.

이번 과제에서는 HTML 화면을 반환하는 것이 아닌,  
게시글 데이터를 JSON 형태로 응답하는 REST API를 구현해야 하므로 `@RestController`를 사용했습니다.

---

## 3. Java Record란 무엇인가요? 기존 클래스와 비교해서 어떤 점이 다른가요?

Java Record는 데이터를 담기 위한 불변 객체를 간결하게 만들 수 있는 문법입니다.

기존 class로 DTO를 만들면 필드, 생성자, getter 등을 직접 작성해야 합니다.  
하지만 record를 사용하면 생성자, getter 역할의 메서드, equals, hashCode, toString이 자동으로 생성됩니다.

예를 들어 다음과 같이 작성할 수 있습니다.

```java
public record CreatePostRequest(
        String title,
        String content,
        String author
) {
}
```

이렇게 작성하면 `title()`, `content()`, `author()` 메서드를 통해 값을 꺼낼 수 있습니다.  
이번 과제에서는 요청과 응답 DTO를 간결하게 표현하기 위해 record를 사용했습니다.

단, `record`가 완전한 의미의 깊은 불변성을 보장하는 것은 아닙니다.  
필드가 `List`나 객체처럼 변경 가능한 타입이면, 그 내부 값은 변경될 수 있습니다.

---

## 4. Optional이란 무엇이고, 왜 null 대신 쓰는 건가요?

`Optional`은 값이 있을 수도 있고 없을 수도 있음을 표현하는 Java의 컨테이너 객체입니다.

기존에는 조회 결과가 없을 때 `null`을 반환하는 경우가 많았습니다.  
하지만 `null`을 반환하면 호출하는 쪽에서 null 체크를 놓쳤을 때 `NullPointerException`이 발생할 수 있습니다.

`Optional`을 사용하면 반환 타입만 보고도 “이 값은 없을 수 있다”는 사실을 명확하게 알 수 있습니다.  
즉, 값이 없는 상황을 코드에서 의도적으로 처리하도록 유도할 수 있습니다.

이번 과제에서는 게시글 ID로 게시글을 조회할 때 해당 게시글이 없을 수 있으므로 Repository의 `findById()`에서 `Optional<Post>`를 반환하도록 구현했습니다.  
기존에는 `for`문으로 게시글 목록을 순회하면서 ID가 일치하는 게시글을 찾고, 없으면 `Optional.empty()`를 반환했습니다.  
이후 같은 동작을 Stream API의 `filter()`와 `findFirst()`를 사용해 더 간결하게 작성했습니다.

```java
public Optional<Post> findById(Long id) {
    return postList.stream()
            .filter(post -> post.getId().equals(id))
            .findFirst();
}
```

`findFirst()`는 조건에 맞는 첫 번째 요소를 `Optional`로 반환합니다.  
조건에 맞는 게시글이 있으면 `Optional<Post>` 안에 게시글이 들어 있고, 없으면 `Optional.empty()`가 반환됩니다.

Service에서는 `Optional`의 `orElseThrow()`를 사용해 게시글이 없으면 `PostNotFoundException`을 던지도록 했습니다.

```java
public Post findPostById(Long id) {
    return postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id));
}
```

이렇게 하면 게시글이 없는 경우를 명확하게 예외로 처리할 수 있고, `null`을 직접 다루지 않아도 되기 때문에 `NullPointerException`이 발생할 가능성을 줄일 수 있습니다.

단, `Optional.get()`을 바로 사용하는 것은 권장되지 않습니다.  
값이 없는 상태에서 `get()`을 호출하면 예외가 발생할 수 있기 때문에, 보통 `orElseThrow()`, `orElse()`, `ifPresent()` 같은 메서드를 사용해 값이 없는 경우를 안전하게 처리합니다.

---

## 5. Spring Bean의 생명주기란 무엇인가요?

Spring Bean은 Spring Container가 생성하고 관리하는 객체입니다.  
개발자가 직접 `new` 키워드로 객체를 생성하고 의존성을 연결하는 것이 아니라, Spring Container가 객체를 생성하고 필요한 의존성을 주입해줍니다.

Spring Bean의 기본 생명주기는 다음과 같습니다.

1. Bean 객체 생성
2. 의존성 주입
3. 초기화
4. 애플리케이션 실행 중 사용
5. 애플리케이션 종료 시 소멸

이번 과제에서 `@RestController`, `@Service`, `@Repository`, `@Component`가 붙은 클래스들은 컴포넌트 스캔을 통해 Spring Bean으로 등록됩니다.

예를 들어 `PostService`는 직접 `new PostService()`로 생성하지 않고, Spring이 `PostRepository`와 `PostValidator` Bean을 찾아 생성자에 주입해줍니다.

```java
public PostService(PostRepository postRepository, PostValidator postValidator) {
    this.postRepository = postRepository;
    this.postValidator = postValidator;
}
```

이처럼 생성자를 통해 필요한 객체를 전달받는 방식을 생성자 주입이라고 합니다.  
생성자 주입을 사용하면 PostService가 동작하기 위해 필요한 의존성을 명확하게 표현할 수 있습니다.

Spring Bean은 기본적으로 싱글톤으로 관리되기 때문에, 특별한 설정이 없다면 Spring Container 안에서 하나의 객체가 생성되고 애플리케이션 실행 중 재사용됩니다.

결과적으로 객체 생성과 의존성 관리 책임이 개발자가 아니라 Spring Container로 이동하므로, 클래스 간 결합도를 낮추고 테스트와 유지보수를 더 쉽게 할 수 있습니다.  
이를 제어의 역전(IoC, Inversion of Control)이라고 합니다.

---

## 6. Spring Boot의 구동 원리를 `DispatcherServlet` 동작 방식 위주로 정리해주세요.

Spring Boot 애플리케이션은 `main()` 메서드에서 `SpringApplication.run()`이 실행되면서 시작됩니다.

```java
SpringApplication.run(Main.class, args);
```

이때 `Main.class`는 일반적으로 `@SpringBootApplication`이 붙은 메인 클래스입니다.

Spring Boot 애플리케이션이 실행되면 Spring Container가 생성되고, 컴포넌트 스캔을 통해 `@Controller`, `@RestController`, `@Service`, `@Repository`, `@Component` 등이 붙은 클래스들이 Bean으로 등록됩니다.

또한 `spring-boot-starter-web`을 사용하는 경우 기본적으로 내장 Tomcat이 실행되어 HTTP 요청을 받을 준비를 합니다.

클라이언트가 요청을 보내면 전체 흐름은 다음과 같습니다.

1. 클라이언트가 HTTP 요청을 보냅니다.
2. 내장 Tomcat이 요청을 받습니다.
3. Tomcat이 요청을 Spring MVC의 `DispatcherServlet`으로 전달합니다.
4. `DispatcherServlet`은 `HandlerMapping`을 통해 요청 URL과 HTTP Method에 맞는 Controller 메서드를 찾습니다.
5. `HandlerAdapter`가 해당 Controller 메서드를 실행합니다.
6. Controller는 필요한 경우 Service를 호출합니다.
7. Service는 비즈니스 로직을 처리하고, Repository를 통해 데이터를 조회하거나 저장합니다.
8. 처리 결과가 요청 처리 흐름의 역방향인 `Repository → Service → Controller` 방향으로 반환됩니다.
9. Controller가 반환한 객체는 `HttpMessageConverter`를 통해 JSON 응답으로 변환됩니다.
10. 변환된 응답이 `DispatcherServlet`을 거쳐 클라이언트에게 반환됩니다.

즉, `DispatcherServlet`은 Spring MVC에서 모든 HTTP 요청을 가장 먼저 받아 적절한 Controller로 전달하는 Front Controller 역할을 합니다.

이번 과제에서는 예를 들어 `GET /posts/1` 요청이 들어오면 `DispatcherServlet`이 요청 URL과 HTTP Method를 기준으로 `PostController`의 `getPost()` 메서드에 연결해줍니다.

이후 `getPost()` 메서드는 Service를 호출해 게시글을 조회하고, 조회 결과를 응답 객체로 반환합니다.  
반환된 객체는 JSON으로 변환되어 클라이언트에게 응답됩니다.