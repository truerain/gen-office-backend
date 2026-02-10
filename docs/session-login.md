# 세션/로그인 가이드 (Spring Boot + React)

## 목표
- 사내 Backoffice 기준의 세션 기반 인증 설계를 정리한다.
- ID/비밀번호 로그인 + 서버 메모리 세션을 기본으로 한다.
- 백엔드/프런트엔드 역할을 명확히 한다.

## 전제/범위
- 백엔드: Spring Boot (Spring Security).
- 세션 저장소: 서버 메모리 (기본 HttpSession).
- 프런트엔드: React (브라우저 기반).
- 단일 서버 가정. 멀티 인스턴스는 공유 세션 저장소(예: Redis) 필요.

## 기본 정책(권장 기본값)
- 세션 생성: 로그인 성공 시 생성.
- 세션 만료: 비활동 30분, 최대 8시간.
- 동시 로그인: 사용자당 1개 세션(선택 정책).
- 쿠키: `JSESSIONID`에 `HttpOnly`, `SameSite=Lax`, HTTPS면 `Secure`.
- CSRF: 활성화, 상태 변경 요청에 토큰 요구.

## 백엔드 설계 (Spring Boot)
### 인증(Authentication) 구성
- 로그인 성공 시 `HttpSession` 생성, `JSESSIONID` 쿠키 발급.
- `UserDetailsService`가 사용자 조회를 담당.
- `UserPrincipal`이 인증 사용자 정보를 보유.
- 비밀번호는 `BCrypt`로 해시 저장/검증.

### 권한(Authorization) 구성
- 권한은 `ROLE_` 접두어를 사용하는 상수 기반으로 관리.
- 기본 권한은 `USER`, 필요 시 `ADMIN` 추가.
- URL 패턴 또는 `@PreAuthorize`로 접근 제어.
- 권한 매핑은 DB의 `role`, `user_role` 테이블로 관리.

### 1) 엔드포인트
- `POST /api/auth/login` : ID/비밀번호 로그인.
- `POST /api/auth/logout` : 로그아웃(세션 무효화).
- `GET /api/auth/me` : 세션 기반 현재 사용자 정보.
- `GET /api/auth/csrf` : CSRF 토큰 조회.

### 2) 세션 데이터
- 필수: `userId`, `roles`, `dept`, `loginTime`.
- 선택: `lastAccess`, `ip`, `userAgent`.

### 3) 인증 흐름
1) 클라이언트가 ID/비밀번호 전송.
2) 서버가 검증 후 `HttpSession` 생성.
3) 서버가 `Set-Cookie: JSESSIONID=...` 반환.
4) 이후 요청은 세션 기반으로 인증/인가 처리.

### 4) 인가
- Spring Security 필터 체인 사용.
- 역할 기반 접근은 `@PreAuthorize` 또는 URL 패턴으로 처리.
- 미인증은 `401`, 권한 부족은 `403`.

### 6) 메서드 보안(@PreAuthorize)
- 설정: `@EnableMethodSecurity` 활성화 필요.
- 예시: `@PreAuthorize("hasRole('ADMIN')")` → `ROLE_ADMIN` 보유 시 통과.
- 예시: `@PreAuthorize("hasAnyRole('ADMIN','USER')")` → 둘 중 하나면 통과.

### 5) 로그아웃/만료
- 로그아웃 시 세션 무효화 + 쿠키 만료.
- 세션 만료 시 `401`(또는 정책에 따라 `440`) 응답.
- 최대 세션 수명 초과 시 세션 무효화 후 `401` 응답.

## 프런트엔드 설계 (React)
### 역할
- 세션 자체는 저장/관리하지 않는다.
- 쿠키 기반 인증을 사용한다.
- `401`/`440` 수신 시 로그인 화면으로 이동한다.

### API 호출
- `fetch`는 `credentials: "include"`.
- `axios`는 `withCredentials: true`.
- CSRF 사용 시 토큰을 헤더에 포함한다.

### UX 가이드
- 세션 만료 시 안내 후 로그인 화면으로 이동.
- 로그아웃 시 클라이언트 상태 초기화 후 로그인 화면으로 이동.

## 보안 체크리스트
- 비밀번호 해시: `BCrypt`.
- 운영 환경에서는 TLS 필수.
- 세션 쿠키에 `HttpOnly` 적용.
- 감사 로그: 로그인 성공/실패, 로그아웃, 세션 만료.
- `/login`에 대한 rate limit 적용.

## 추가 구조(Backoffice에서 흔한 확장)
- 계정 잠금 정책(예: 5회 실패 시 30분 잠금).
- 비밀번호 정책(예: 길이 10 이상, 복잡도 규칙).
- 사내망 IP 허용 목록.
- 관리자 행위 감사 로그.
- 권한 변경/비밀번호 초기화 시 강제 로그아웃.

## 패키지 구성
- 인증: `infrastructure/authentication` (로그인, 세션, CSRF)
- 권한: `infrastructure/authorization` (UserDetails, Role)

## 서버 메모리 세션 유의사항
- 단일 서버에서는 OK, 재시작 시 세션 소멸.
- 확장 시 Redis 세션으로 전환 필요.

## 확정 필요 항목
- 비활동 만료: ___ 분
- 최대 세션 수명: ___ 시간
- 동시 로그인 정책: 허용 / 단일
- CSRF 정책: 활성 / 비활성
- 만료 응답 코드: 401 / 440


## 프런트엔드 에이전트 공유용 요약입니다.  
• 인증 방식: 서버 세션 기반, JSESSIONID 쿠키로 인증  
• API 호출: fetch는 credentials: "include", axios는 withCredentials: true  
• 세션 만료 처리: 401(또는 440) 수신 시 로그인 화면으로 이동  
• CSRF 정책: 활성화 시 토큰을 헤더에 포함(POST/PUT/DELETE 등)  
• 로그아웃 UX: /api/auth/logout 호출 후 클라이언트 상태 초기화 및 로그인 화면 이동  


### 구현내용요약 
세션/로그인 문서에 맞춰 Spring Boot 쪽 세션 기반 로그인, CSRF 토큰 조회, 세션 최대 수명 필터, 비밀번호 해시 처리까지 구현했어요. src/main/java/com/third/gen_office/config/SecurityConfig.java에서 세션/CSRF/인증 정책과 필터를 설정했고, src/main/java/com/third/gen_office/mis/auth/AuthController.java에 /api/auth/login, /api/auth/logout, /api/auth/me, /api/auth/csrf 엔드포인트를 추가했습니다. src/main/java/com/third/gen_office/mis/admin/user/UserService.java에서는 비밀번호를 BCrypt로 해시하도록 변경했습니다.  
• 인증/세션: src/main/java/com/third/gen_office/config/SecurityConfig.java에서 세션 기반 인증, 단일 세션, CSRF 쿠키 토큰, 절대 세션 만료 필터를 구성  
• 로그인/세션 데이터: src/main/java/com/third/gen_office/mis/auth/AuthController.java에서 로그인 성공 시 세션에 userId, roles, dept, loginTime 저장  
• 사용자 인증: src/main/java/com/third/gen_office/mis/auth/UserDetailsServiceImpl.java, src/main/java/com/third/gen_office/mis/auth/UserPrincipal.java로 Spring Security 인증 연동  
• 비밀번호 해시: src/main/java/com/third/gen_office/mis/admin/user/UserService.java에서 신규/변경 시 BCrypt 적용  
• 세션 정책: src/main/resources/application.yaml에 server.servlet.session.timeout: 30m, same-site: Lax 추가  
• 문서 보강: docs/session-login.md에 CSRF 토큰 엔드포인트와 절대 만료 처리 추가

다음 단계가 필요하면 알려줘요.
1) 프런트에서 CSRF 토큰 요청/헤더 설정 예시 추가
2) 로그인 실패/만료 응답 포맷을 전사 에러 스키마로 통일
3) Swagger 접근 허용 정책 추가 (예: /swagger-ui/**, /v3/api-docs/**)
