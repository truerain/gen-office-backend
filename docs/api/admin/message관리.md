# MIS > Admin > Message API (tb_cm_message)

## 목적
- 관리자 화면에서 메시지 리소스(`tb_cm_message`)를 등록/수정/삭제/조회한다.
- 런타임 i18n 조회 API(`/api/i18n`)와 분리된 관리용 API이며, 동일한 테이블과 규칙을 사용한다.

## 범위
- 관리 대상: `tb_cm_message`
- 키 구성: `(namespace, message_cd, lang_cd)`이며 i18n 키는 `namespace.message_cd`로 구성된다.
- 런타임 조회 로직과 동일한 네임스페이스/로케일 규칙을 따른다.

## 데이터 모델
- 테이블: `tb_cm_message`
- 기본키: `(message_cd, lang_cd, namespace)`
- 필드 의미:
- `message_cd`: 메시지 코드 (예: `not_found`)
  - `lang_cd`: 로케일 코드 (예: `ko`, `en`, `en-US`)
- `namespace`: 도메인/컨텍스트 구분 (예: `auth`, `menu`, `userEntity`)
  - `message_txt`: 로케일 문자열

## 로케일 규칙 (응답 메시지)
- 관리 API의 에러 메시지도 표준 i18n 규칙을 따른다.
- 로케일 결정 우선순위: `X-Lang` → `Accept-Language` → 사용자 설정 → 기본 `ko`.

## Endpoints
### 1) 메시지 목록 조회
- `GET /api/mis/admin/messages`
- Query params
  - `namespace` (optional)
  - `langCd` (optional)
  - `messageCd` (optional, 부분검색)
  - `q` (optional, message_txt 부분검색)
  - `page` (optional, default 0)
  - `size` (optional, default 20)
  - `sort` (optional, default `namespace, message_cd, lang_cd`)
    - 형식: `field [asc|desc]`를 콤마로 나열
    - 허용 필드: `namespace`, `message_cd`, `lang_cd`, `creation_date`, `last_updated_date`

Response
```json
{
  "items": [
    {
      "namespace": "userEntity",
      "messageCd": "not_found",
      "langCd": "ko",
      "messageTxt": "사용자를 찾을 수 없습니다",
      "createdAt": "2026-02-23T10:00:00",
      "updatedAt": "2026-02-23T10:00:00"
    }
  ],
  "page": 0,
  "size": 20,
  "total": 1
}
```

### 2) 메시지 단건 조회
- `GET /api/mis/admin/messages/{namespace}/{messageCd}/{langCd}`

Response
```json
{
  "namespace": "userEntity",
  "messageCd": "not_found",
  "langCd": "ko",
  "messageTxt": "사용자를 찾을 수 없습니다",
  "createdAt": "2026-02-23T10:00:00",
  "updatedAt": "2026-02-23T10:00:00"
}
```

### 3) 메시지 생성
- `POST /api/mis/admin/messages`

Request
```json
{
  "namespace": "userEntity",
  "messageCd": "not_found",
  "langCd": "ko",
  "messageTxt": "사용자를 찾을 수 없습니다"
}
```

Response
```json
{
  "namespace": "userEntity",
  "messageCd": "not_found",
  "langCd": "ko",
  "messageTxt": "사용자를 찾을 수 없습니다",
  "createdAt": "2026-02-23T10:00:00",
  "updatedAt": "2026-02-23T10:00:00"
}
```

### 4) 메시지 수정
- `PUT /api/mis/admin/messages/{namespace}/{messageCd}/{langCd}`

Request
```json
{
  "messageTxt": "사용자를 찾을 수 없습니다."
}
```

Response
```json
{
  "namespace": "userEntity",
  "messageCd": "not_found",
  "langCd": "ko",
  "messageTxt": "사용자를 찾을 수 없습니다.",
  "createdAt": "2026-02-23T10:00:00",
  "updatedAt": "2026-02-23T10:05:00"
}
```

### 5) 메시지 삭제
- `DELETE /api/mis/admin/messages/{namespace}/{messageCd}/{langCd}`

Response
- `204 No Content`

### 6) 메시지 일괄 등록/수정 (옵션)
- `POST /api/mis/admin/messages/bulk`

Request
```json
{
  "items": [
    {
      "namespace": "userEntity",
      "messageCd": "not_found",
      "langCd": "en",
      "messageTxt": "User not found"
    }
  ]
}
```

Response
```json
{
  "inserted": 1,
  "updated": 0,
  "skipped": 0
}
```

### 7) 누락 번역 조회 (옵션)
- `GET /api/mis/admin/messages/missing`
- Query params
  - `namespace` (optional)
  - `baseLang` (required, default `ko`)
  - `targetLang` (required)

Response
```json
{
  "items": [
    {
      "namespace": "userEntity",
      "messageCd": "not_found",
      "baseLang": "ko",
      "targetLang": "en"
    }
  ]
}
```

## 검증 규칙
- `namespace`, `messageCd`, `langCd`, `messageTxt`는 필수.
- `langCd`는 BCP47 형식(`ko`, `en`, `en-US`)을 허용.
- `messageCd`는 공백 금지, 권장 패턴: `snake_case` 또는 `camelCase`.
- 중복 키 `(namespace, message_cd, lang_cd)` 생성 시 `409 Conflict`.
 - `size`는 최대 200.

## 캐시 무효화
- 생성/수정/삭제 시 `(namespace, message_cd, lang_cd)` 캐시를 즉시 제거한다.
- 캐시 키: `namespace|message_cd|lang_cd`.

## 에러 응답
- 표준 에러 포맷을 유지한다.
```json
{
  "code": "CONFLICT",
  "messageKey": "message.duplicate",
  "message": "이미 존재하는 메시지 키입니다",
  "locale": "ko",
  "path": "/api/mis/admin/messages",
  "timestamp": "2026-02-23T10:12:34+09:00",
  "traceId": null
}
```

## 보안
- 관리자 권한(예: `ROLE_ADMIN` 또는 운영 정책에 맞는 권한)만 접근 가능.
- 감사 로그가 필요한 경우 `createdBy`, `updatedBy` 컬럼 확장 고려.
