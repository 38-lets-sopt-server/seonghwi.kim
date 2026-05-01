# 1 주차 키워드 과제

## 1. `ERD` 란 무엇인가요?
ERD(Entity Relationship Diagram, 개체 관계 다이어그램)는 데이터베이스에 저장할 데이터 구조와 테이블 간 관계를 시각적으로 표현한 설계도입니다.

ERD를 작성하면 어떤 테이블이 필요한지, 각 테이블이 어떤 컬럼을 가지는지, 테이블 사이가 1:1, 1:N, N:M 중 어떤 관계인지 한눈에 파악할 수 있습니다.

이번 과제에서는 `users` 테이블과 `posts` 테이블을 설계했습니다. 한 명의 유저는 여러 게시글을 작성할 수 있고, 하나의 게시글은 한 명의 유저를 작성자로 가지므로 `User 1 : N Post` 관계로 설계했습니다.

## 2. 구현한 내용에 대해 ERD 구조도를 ERD Cloud에 그려서 올려주세요.
### ERD
[ERD CLOUD](https://www.erdcloud.com/d/XT2NDpTMa9b739gLm)  

<img width="925" height="570" alt="ERD_CLOUD" src="https://github.com/user-attachments/assets/3211ac6a-1b8b-48b0-a810-9ea1dd9a0e1a" />

현재 구현한 `User`와 `Post` 엔티티를 기준으로 ERD를 작성했습니다.

#### users 테이블
| 컬럼명        | 타입           | 제약조건               |
| ---------- | ------------ | ------------------ |
| id         | BIGINT       | PK, AUTO_INCREMENT |
| nickname   | VARCHAR(20)  | NOT NULL           |
| email      | VARCHAR(100) | NOT NULL, UNIQUE   |
| created_at | DATETIME     | NOT NULL           |
| updated_at | DATETIME     | NOT NULL           |

#### posts 테이블
| 컬럼명         | 타입           | 제약조건            |
| ----------- | ------------ | --------------- |
| id          | BIGINT       | PK, AUTO_INCREMENT |
| board_type       | VARCHAR  | NOT NULL        |
| user_id     | BIGINT | FK, NOT NULL      |
| title |    VARCHAR(50)   | NOT NULL        |
|  content |   VARCHAR(500)    | NOT NULL        |
| is_anonymous     | BOOLEAN       |  NOT NULL    |
| created_at  | DATETIME     | NOT NULL        |
| updated_at  | DATETIME     | NOT NULL        |


- `users`와 `posts`는 1:N 관계입니다.
- 한 명의 사용자는 여러 게시글을 작성할 수 있습니다.
- 하나의 게시글은 반드시 한 명의 작성자를 가집니다.
- FK는 N쪽인 `posts.user_id`에 위치하도록 설계했습니다.

## 3. QueryDSL이란 무엇이고, JPQL과 어떤 차이가 있나요?
JPQL(Java Persistence Query Language)은 JPA에서 사용하는 객체 지향 쿼리 언어입니다. SQL처럼 테이블을 대상으로 조회하는 것이 아니라, 엔티티 객체를 대상으로 쿼리를 작성합니다.

예를 들어 `Post` 엔티티를 제목으로 검색할 때 JPQL은 다음처럼 문자열 기반으로 작성합니다.

```java
@Query("select p from Post p join fetch p.user where p.title like concat('%', :keyword, '%')")
List<Post> searchByTitle(@Param("keyword") String keyword);
```

JPQL은 SQL과 비슷해서 이해하기 쉽지만, 문자열로 쿼리를 작성하기 때문에 컴파일 시점에 오타를 잡기 어렵습니다. 필드명이 바뀌어도 문자열 안의 쿼리는 자동으로 변경되지 않기 때문에 런타임 오류가 발생할 수 있습니다.

QueryDSL은 Java 코드로 타입 안전하게 쿼리를 작성할 수 있게 도와주는 기술입니다. 문자열이 아니라 생성된 Q타입(엔티티 클래스를 기반으로 자동 생성되는 메타데이터 클래스)을 사용해서 쿼리를 작성하기 때문에, 필드명 오타나 타입 오류를 컴파일 시점에 어느 정도 잡을 수 있습니다.

또한 검색 조건이 여러 개이고, 값이 있을 때만 조건을 붙여야 하는 동적 쿼리 작성에 유리합니다.

정리하면 다음과 같습니다.

| 구분       | JPQL        | QueryDSL       |
| -------- | ----------- | -------------- |
| 작성 방식    | 문자열 기반 쿼리   | Java 코드 기반 쿼리  |
| 타입 안정성   | 낮음          | 높음             |
| 오타 발견 시점 | 주로 런타임      | 컴파일 시점         |
| 단순 쿼리    | 간단하게 작성 가능  | 설정이 필요해 다소 무거움 |
| 동적 쿼리    | 조건 조합이 번거로움 | 조건 조합이 비교적 편리함 |

이번 과제에서는 제목 검색처럼 단순한 검색은 JPQL로도 충분하지만, '제목의 키워드'와 '작성자의 닉네임'을 동시에 받아 조건을 동적으로 조합해야 하는 경우에는 QueryDSL을 사용하는 것이 더 적합합니다.