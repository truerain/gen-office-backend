# Role API (tb_cm_role)

## 목적
- 관리자 화면에서 역할(권한 그룹) 리소스(`tb_cm_role`)를 등록/수정/삭제/조회한다.
- 사용자-역할(`tb_cm_user_role`) 및 역할-메뉴(`tb_cm_role_menu`) 매핑에 사용된다.

## 범위
- 관리 대상: `tb_cm_role`
- 역할은 `role_cd`를 고유 값으로 사용한다.

## 데이터 모델
- 테이블: `tb_cm_role`
- 기본키: `role_id`
- 주요 필드:
  - `role_id`: 역할 ID
  - `role_cd`: 역할 코드 (고유)
  - `role_name`: 역할명
  - `role_name_eng`: 역할명(영문)
  - `role_desc`: 역할 설명
  - `sort_order`: 정렬 순서
  - `use_yn`: 사용 여부 (`Y/N`)

## Endpoints
### 1) 역할 목록 조회
- `GET /api/roles`

Response
```json
[
  {
    "roleId": 1,
    "roleCd": "ADMIN",
    "roleName": "관리자",
    "roleNameEng": "Admin",
    "roleDesc": "관리자 역할",
    "sortOrder": 1,
    "useYn": "Y",
    "lastUpdatedBy": "EMP001",
    "lastUpdatedByName": "홍길동",
    "lastUpdatedAt": "2026-02-12T09:00:00"
  }
]
```

### 2) 역할 단건 조회
- `GET /api/roles/{id}`

Response
```json
{
  "roleId": 1,
  "roleCd": "ADMIN",
  "roleName": "관리자",
  "roleNameEng": "Admin",
  "roleDesc": "관리자 역할",
  "sortOrder": 1,
  "useYn": "Y",
  "lastUpdatedBy": "EMP001",
  "lastUpdatedByName": "홍길동",
  "lastUpdatedAt": "2026-02-12T09:00:00"
}
```

### 3) 역할 생성
- `POST /api/roles`

Request
```json
{
  "roleCd": "ADMIN",
  "roleName": "관리자",
  "roleNameEng": "Admin",
  "roleDesc": "관리자 역할",
  "sortOrder": 1,
  "useYn": "Y"
}
```

Response
```json
{
  "roleId": 1,
  "roleCd": "ADMIN",
  "roleName": "관리자",
  "roleNameEng": "Admin",
  "roleDesc": "관리자 역할",
  "sortOrder": 1,
  "useYn": "Y",
  "lastUpdatedBy": "EMP001",
  "lastUpdatedByName": "홍길동",
  "lastUpdatedAt": "2026-02-12T09:00:00"
}
```

### 4) 역할 수정
- `PUT /api/roles/{id}`

Request
```json
{
  "roleCd": "ADMIN",
  "roleName": "관리자",
  "roleNameEng": "Admin",
  "roleDesc": "관리자 역할",
  "sortOrder": 1,
  "useYn": "Y"
}
```

Response
```json
{
  "roleId": 1,
  "roleCd": "ADMIN",
  "roleName": "관리자",
  "roleNameEng": "Admin",
  "roleDesc": "관리자 역할",
  "sortOrder": 1,
  "useYn": "Y",
  "lastUpdatedBy": "EMP001",
  "lastUpdatedByName": "홍길동",
  "lastUpdatedAt": "2026-02-12T09:00:00"
}
```

### 5) 역할 삭제
- `DELETE /api/roles/{id}`

Response
- `204 No Content`

## 검증 규칙
- `roleCd`, `roleName`, `roleNameEng`는 필수.
- `useYn`은 `Y/N` 값만 허용.

## 에러 응답
- 표준 에러 포맷을 유지한다.
```json
{
  "code": "NOT_FOUND",
  "messageKey": "role.not_found",
  "message": "역할을 찾을 수 없습니다",
  "locale": "ko",
  "path": "/api/roles/1",
  "timestamp": "2026-02-12T10:12:34+09:00",
  "traceId": null
}
```
