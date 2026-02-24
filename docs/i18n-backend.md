Backend i18n 설계 (DB 기반)

범위
- 모든 메시지 리소스는 DB 테이블 `tb_cm_message`에 저장한다.
- 화면 번역은 프론트에서 처리하고, 백엔드는 에러/시스템 알림 등 서버 생성 문구를
  로케일에 맞게 제공한다.

데이터 모델
- 테이블: `tb_cm_message`
- 기본키: `(message_cd, lang_cd, namespace)`
- 추가 인덱스: `(namespace, message_cd, lang_cd)`, `(lang_cd)`
- 필드 의미:
  - `message_cd`: 고정 메시지 키 (예: `error.userEntity.not_found`)
  - `lang_cd`: 로케일 코드 (예: `ko`, `en`, `en-US`)
  - `namespace`: 도메인/컨텍스트 구분 (예: `auth`, `menu`, `userEntity`)
  - `message_txt`: 로케일 문자열

로케일 결정
1) 요청 헤더 `X-Lang` 우선.
2) `Accept-Language` 헤더.
3) 인증 사용자 언어 설정 (예: `User.langCd`).
4) 기본값 (예: `ko`).

메시지 조회
1) 위 규칙으로 `lang_cd` 결정.
2) `(namespace, message_cd, lang_cd)`로 `tb_cm_message` 조회.
3) 없으면 기본 언어로 재조회.
4) 그래도 없으면 키 자체를 문구로 반환.

에러 응답 규격
- 에러 응답은 `messageKey`와 해석된 `message`를 모두 포함:
  - `code`: 고정 에러 코드
  - `messageKey`: `namespace.message_cd`
  - `message`: DB에서 조회한 로케일 문구
  - `locale`: 최종 적용 로케일
  - `path`, `timestamp`, `traceId` (선택)

캐싱
- `(namespace, message_cd, lang_cd)` 기준으로 로컬 캐시(Caffeine) 사용.
- TTL: 5~30분 (갱신 빈도에 따라 조정).
- 관리자 수정 시 해당 키 캐시 삭제.

운영/관리
- `tb_cm_message`에 다국어 메시지 등록/수정.
- 선택 사항: 네임스페이스/로케일 기준 누락 번역 목록 제공.

구현 개요
1) `tb_cm_message`를 조회하는 커스텀 `MessageSource` 구현.
2) 위 로케일 결정 규칙을 반영하는 `LocaleResolver` 추가 + `Content-Language` 설정.
3) `@RestControllerAdvice`로 에러 응답 표준화 후 `MessageSource`로 메시지 해석.



Frontend 전달 내용
백엔드 에이전트가 요약한 i18n관련 구현 요약

•메시지 리소스는 DB tb_cm_message에 저장됨. 키는 (namespace, message_cd, lang_cd) 조합.
•로케일 결정 우선순위: X-Lang 헤더 → Accept-Language → (추후 사용자 언어) → 기본 ko.
•응답 헤더에 Content-Language가 항상 내려감.
•에러 응답은 i18n 메시지를 포함한 표준 포맷으로 내려감.
에러 응답 포맷
{
"code": "NOT_FOUND",
"messageKey": "userEntity.not_found",
"message": "사용자를 찾을 수 없습니다",
"locale": "ko",
"path": "/api/users/1",
"timestamp": "2026-02-09T09:12:34+09:00",
"traceId": null
}

프론트 적용 가이드
•API 호출 시 로케일을 X-Lang 헤더로 전달(예: ko, en, en-US).
•에러 표시 시 message를 우선 사용하고, 필요하면 messageKey로 클라이언트 i18n fallback 가능.
•Content-Language 값으로 서버가 적용한 로케일을 확인 가능.
현재 정의된 메시지 키
•userEntity.not_found
•menu.not_found
•common.internal_error


### I18n 패키지 관련 클래스 정리
1. I18nConfig.java
 - MessageSource, LocaleResolver, 응답 헤더 인터셉터를 스프링에 등록하는 설정 클래스입니다. DB 기반 메시지 소스(DbMessageSource)와 헤더 기반 로케일 해석(HeaderLocaleResolver)을 활성화합니다.
2. DbMessageSource.java
 - DB(tb_cm_message)에서 메시지를 조회하는 MessageSource 구현체입니다. 로케일 언어코드 폴백(예: ko-KR → ko → 기본 ko)과 캐시(Caffeine)를 적용합니다.
3. HeaderLocaleResolver.java
 - 요청에서 로케일을 결정합니다. 우선순위는 X-Lang 헤더 → Accept-Language → request attribute userLang → 기본 ko입니다.
4. LocaleResponseHeaderInterceptor.java
 - 응답에 Content-Language 헤더를 설정해 최종 로케일을 클라이언트에 알려줍니다.
5. I18nController.java
 - /api/i18n API로 특정 locale과 namespace에 해당하는 메시지 목록을 조회합니다. 결과는 I18nItemsResponse로 반환됩니다.
6.
src/main/java/com/third/gen_office/global/i18n/I18nItemResponse.java
단일 메시지 항목 DTO입니다. key, value를 가집니다.
7.
src/main/java/com/third/gen_office/global/i18n/I18nItemsResponse.java
메시지 목록 응답 DTO입니다. items 리스트를 가집니다.
8.
src/main/java/com/third/gen_office/global/i18n/MessageKey.java
메시지 키 파서입니다. namespace.messageCd 형식을 분리하고 기본 네임스페이스(common) 및 잘못된 키 처리에 사용됩니다. DbMessageSource에서 내부적으로 사용됩니다.
9.
src/main/java/com/third/gen_office/global/error/GlobalExceptionHandler.java
예외 응답 메시지를 MessageSource로 i18n 처리합니다. ApiException과 일반 예외에 대해 로케일별 메시지를 조회해 응답에 포함합니다.