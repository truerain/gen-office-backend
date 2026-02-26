# Menu API (tb_cm_menu)

## 목적
- 관리자 화면에서 메뉴 리소스(`tb_cm_menu`)를 등록/수정/삭제/조회한다.
- 메뉴/권한 매핑(`tb_cm_role_menu`)과 함께 권한 기반 메뉴 노출에 사용된다.

## 범위
- 관리 대상: `tb_cm_menu`
- 메뉴 트리는 `parent_menu_id` 기반으로 구성한다.
- 하위 메뉴 조회는 `/api/menus/submenu/{id}`로 제공한다.

## 데이터 모델
- 테이블: `tb_cm_menu`
- 기본키: `menu_id`
- 주요 필드:
  - `menu_id`: 메뉴 ID
  - `menu_name`: 메뉴명
  - `menu_name_eng`: 메뉴명(영문)
  - `menu_desc`: 메뉴 설명
  - `menu_desc_eng`: 메뉴 설명(영문)
  - `menu_level`: 메뉴 레벨
  - `exec_component`: 실행 컴포넌트
  - `menu_icon`: 메뉴 아이콘
  - `parent_menu_id`: 상위 메뉴 ID
  - `display_yn`: 표시 여부 (`Y/N`)
  - `use_yn`: 사용 여부 (`Y/N`)
  - `sort_order`: 정렬 순서

## Endpoints
### 1) 메뉴 목록 조회
- `GET /api/menus`

Response
```json
[
  {
    "menuId": 1001,
    "menuName": "인사",
    "menuNameEng": "HR",
    "menuDesc": "인사 관리",
    "menuDescEng": "HR management",
    "menuLevel": 1,
    "execComponent": "HrMain",
    "menuIcon": "users",
    "parentMenuId": null,
    "displayYn": "Y",
    "useYn": "Y",
    "sortOrder": 1,
    "lastUpdatedBy": "EMP001",
    "lastUpdatedByName": "홍길동",
    "lastUpdatedAt": "2026-02-12T09:00:00"
  }
]
```

### 2) 메뉴 단건 조회
- `GET /api/menus/{id}`

Response
```json
{
  "menuId": 1001,
  "menuName": "인사",
  "menuNameEng": "HR",
  "menuDesc": "인사 관리",
  "menuDescEng": "HR management",
  "menuLevel": 1,
  "execComponent": "HrMain",
  "menuIcon": "users",
  "parentMenuId": null,
  "displayYn": "Y",
  "useYn": "Y",
  "sortOrder": 1,
  "lastUpdatedBy": "EMP001",
  "lastUpdatedByName": "홍길동",
  "lastUpdatedAt": "2026-02-12T09:00:00"
}
```

### 3) 하위 메뉴 목록
- `GET /api/menus/submenu/{id}`

Response
```json
[
  {
    "menuId": 1100,
    "menuName": "인사 기준정보",
    "menuNameEng": "HR Master",
    "menuDesc": "인사 기준정보",
    "menuDescEng": "HR Master",
    "menuLevel": 2,
    "execComponent": "HrMaster",
    "menuIcon": "list",
    "parentMenuId": 1001,
    "displayYn": "Y",
    "useYn": "Y",
    "sortOrder": 1,
    "lastUpdatedBy": "EMP001",
    "lastUpdatedByName": "홍길동",
    "lastUpdatedAt": "2026-02-12T09:00:00"
  }
]
```

### 4) 메뉴 생성
- `POST /api/menus`

Request
```json
{
  "menuId": 1001,
  "menuName": "인사",
  "menuNameEng": "HR",
  "menuDesc": "인사 관리",
  "menuDescEng": "HR management",
  "menuLevel": 1,
  "execComponent": "HrMain",
  "menuIcon": "users",
  "parentMenuId": null,
  "displayYn": "Y",
  "useYn": "Y",
  "sortOrder": 1
}
```

Response
```json
{
  "menuId": 1001,
  "menuName": "인사",
  "menuNameEng": "HR",
  "menuDesc": "인사 관리",
  "menuDescEng": "HR management",
  "menuLevel": 1,
  "execComponent": "HrMain",
  "menuIcon": "users",
  "parentMenuId": null,
  "displayYn": "Y",
  "useYn": "Y",
  "sortOrder": 1,
  "lastUpdatedBy": "EMP001",
  "lastUpdatedByName": "홍길동",
  "lastUpdatedAt": "2026-02-12T09:00:00"
}
```

### 5) 메뉴 수정
- `PUT /api/menus/{id}`

Request
```json
{
  "menuId": 1001,
  "menuName": "인사",
  "menuNameEng": "HR",
  "menuDesc": "인사 관리",
  "menuDescEng": "HR management",
  "menuLevel": 1,
  "execComponent": "HrMain",
  "menuIcon": "users",
  "parentMenuId": null,
  "displayYn": "Y",
  "useYn": "Y",
  "sortOrder": 1
}
```

Response
```json
{
  "menuId": 1001,
  "menuName": "인사",
  "menuNameEng": "HR",
  "menuDesc": "인사 관리",
  "menuDescEng": "HR management",
  "menuLevel": 1,
  "execComponent": "HrMain",
  "menuIcon": "users",
  "parentMenuId": null,
  "displayYn": "Y",
  "useYn": "Y",
  "sortOrder": 1,
  "lastUpdatedBy": "EMP001",
  "lastUpdatedByName": "홍길동",
  "lastUpdatedAt": "2026-02-12T09:00:00"
}
```

### 6) 메뉴 삭제
- `DELETE /api/menus/{id}`

Response
- `204 No Content`

## 검증 규칙
- `menuId`, `menuName`, `menuNameEng`, `menuLevel`은 필수.
- `displayYn`, `useYn`은 `Y/N` 값만 허용.

## 에러 응답
- 표준 에러 포맷을 유지한다.
```json
{
  "code": "NOT_FOUND",
  "messageKey": "menu.not_found",
  "message": "메뉴를 찾을 수 없습니다",
  "locale": "ko",
  "path": "/api/menus/1001",
  "timestamp": "2026-02-12T10:12:34+09:00",
  "traceId": null
}
```
