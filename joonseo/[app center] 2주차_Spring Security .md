# [app center] Spring Security

# Spring Security

<aside>
💡

스프링 기반의 애플리케이션에서 인증(Authentication) 과 인가(Authorization)를 처리하기 위한 보안 프레임워크이다.

</aside>

- **인증 (Authentication)**
    - 사용자가 누구인지 확인하는 절차
    - 로그인 폼, 아이디/비밀번호 검사 등이 여기에 해당된다.
    - 인증이 성공하면 Authentication 객체가 만들어지고, SecurityContext 에 저장된다.
- **인가 (Authorization)**
    - 인증된 사용자가 특정 자원에 접근할 수 있는 권한이 있는지 확인하는 과정
    - 인가가 있으려면 인증이 선행되어야 한다.
    - ex) 관리자는 /admin 접근 가능, 일반 사용자는 불가능

# Spring Security 동작

스프링 시큐리티는 Filter 기반으로 동작하기 때문에 스프링 MVC 와 분리되어 관리 및 동작한다.

**Spring MVC 란?**

- 우리가 일반적으로 만드는 컨트롤러(@Controller, @RestController) 기반 웹 애플리케이션
- 요청이 오면 DispatchServlet 이 받아서 → 컨트롤러로 넘김

```java
브라우저 → [DispatcherServlet] → Controller → Service → Repository
```

Filter 기반의 스프링 시큐리티의 동작방식은 아래와 같다. HTTP 요청이 DispatcherServlet 에 도달하기 전에 가로채서 인증/인가를 먼저 처리하는 구조이다.

```java
HttpRequest (요청) ➡️ Filter ➡️ DispatcherServlet ➡️ Interceptor ➡️ Buisiness Logi
```

요청 → 필터에 도착 → 필터가 토큰 생성 (UsernamePasswordAuthenticationToken) (인증용 객체) → 필터로부터 인증 토큰 전달 받아 실제 인증 로직 처리 (AuthenticationProvider) → 토큰에 들어있는 사용자 정보의 일부를 통해 사용자의 전체 정보를 DB 조회 (UserDetailsService) → UserDetails 를 구현하고 있는 User 클래스 조회 → 인증 결과 반환 

1. **요청 수신**
    - 사용자가 form 을 통해 로그인 정보가 담긴 Request 를 보낸다.

1. **토큰 생성**
    - AuthenticationFilter 가 요청을 받아서 사용자의 ID/PW가 담긴
        
        UsernamePasswordAuthenticationToken토큰 (인증용 객체) 를 생성한다.
        
    - 이 토큰은 해당 요청을 처리할 수 있는 Provider 을 찾는 데 사용한다.
    
    ```java
    UsernamePasswordAuthenticationToken authToken = 
        new UsernamePasswordAuthenticationToken(username, password);
    ```
    

1. **AuthenticationFilter 로부터 토큰을 전달 받는다.**
    - Authentication Manager 에게 처리 위임
    - Authentication Manager 는 List 형태로 Provider 들을 갖고 있다. (Authentication Manager 는 여러 Provider 들을 관리하는 총괄 관리자이다.)
        - 토큰의 타입을 기준으로 어떤 Provider 가 이 토큰을 처리할 수 있을지 결정한다.

1. **Token 을 처리할 수 있는 Authentication Provider 선택**
    - 실제 인증을 할 AuthenticationProvider 에게 인증용 객체를 다시 전달한다.

1. **인증 절차**
    - 인증 절차가 시작되면 AuthenticationProvider 인터페이스가 실행되고 DB 에 있는 사용자의 정보와 화면에서 입력한 로그인 정보를 비교한다.

1. **UserDetailsService 의 loadUserByUsername 메소드 수행**
    - AuthenticationProvider 인터페이스에서는 authenticate() 메소드를 오버라이딩 하게 되는데, 이 메소드의 파라미터인 인증용 객체로 화면에서 입력한 로그인 정보를 가져올 수 있다.
        - UserDetailsService 를 구현한 클래스에서 → loadUserByUsername() 메서드로 DB 에서 유저를 조회한다.
        - 이때 리턴하는 건 UserDetails 타입 객체
    
    ```java
    // 예시: DaoAuthenticationProvider.java
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 로그인 창에서 입력한 username, password 꺼냄
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
    
        // 2. UserDetailsService의 loadUserByUsername 호출
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
        // 3. 패스워드 비교 (보통 PasswordEncoder 사용)
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
    
        // 4. 인증 성공 시, 인증된 객체 반환
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    
    ```
    

---

| 역할 | 구현 클래스 | 설명 |
| --- | --- | --- |
| 사용자 정보 조회 | `UserDetailsService` | `loadUserByUsername()` 구현해서 DB에서 사용자 조회 |
| 인증 처리 (비번 확인) | `DaoAuthenticationProvider` | `authenticate()` 내부에서 사용자 조회 후 비밀번호 비교 |
| 인증 매니징 | `AuthenticationManager` | 적절한 Provider를 찾아서 인증 처리 위임 |

**cf. DAO 란?**

- Data Access Object (데이터 접근 객체) 로, DB 와 직접 통신해서 데이터 (유저 정보, 게시글, 주문 등) 를 읽고 쓰는 역할을 하는 객체이다.
- Spring Security 에서 DaoAuthenticationProvider >> DB 에서 유저 정보를 꺼내서 인증하는 방식이기 때문에 DAO 방식