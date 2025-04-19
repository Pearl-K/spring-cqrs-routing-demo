# spring-cqrs-routing-demo
A practical Kotlin project demonstrating CQRS-based read/write routing with Spring.

---
## 1. CQRS Concepts & Routing 
### ✅ CQRS
- **Command Query Responsibility Segregation**
- Read와 Write를 명확히 분리해서 책임을 나누는 아키텍처 패턴
- 읽기 트래픽이 많은 대규모 시스템에서 효과적

### ✅ R/W routing
- `AbstractRoutingDataSource` 기반의 `ReplicationRoutingDataSource`
- `ThreadLocal` 기반의 컨텍스트 저장소 (`DbContextHolder`)
- `@ReadOnly` 어노테이션과 AOP로 라우팅 키 세팅


---
## 2. Work Flow
### Spring MVC 기반의 동작

```
[1] 클라이언트 HTTP 요청 →


[2] Tomcat Thread Pool에서 하나의 스레드 할당 →


[3] AOP @Before (@ReadOnly 붙은 메서드 진입 전) → ThreadLocal에 "read" or "write" 키 저장 → 


[4] @Transactional 진입 → 


[5] DataSource.getConnection() 호출
    → AbstractRoutingDataSource.determineCurrentLookupKey()
    → DataSource 선택 (read/write)


[6] 선택된 DataSource 내부 HikariCP Connection Pool에서 연결 획득 → 


[7] DB 쿼리 수행 후 결과 반환 → 


[8] AOP @After 를 통해 ThreadLocal 초기화 (remove)
```

---

## 3. TO DO (향후 확장)
### 🔹 R/O와 R/W 간 정합성 보장 전략 구현
### 🔹 Coroutine 기반 구조로 변경
> `ThreadLocal` 사용 불가능, 이는 Tomcat 스레드 풀에서 요청받았을 때 요청 per 스레드가 할당되는 현재 상황에서만 가능함
> 
>  코루틴 전환 시 Continuation 객체 단위로 스레드를 옮겨다니면서 코루틴 작업의 중단과 재개가 일어날 수 있기 때문에 Thread 기반의 처리 사용 불가능
- `CoroutineContext` 기반의 컨텍스트 저장 구조로 변경
- `withContext(ReadRoutingContext)` 방식으로 context 전파
- `suspend` 함수 내에서 `coroutineContext[RoutingKey]` 등으로 추출하여 라우팅

