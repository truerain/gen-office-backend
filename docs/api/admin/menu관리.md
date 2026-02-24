# Menu API (tb_cm_menu)

## Endpoints
- `GET /api/menus`: 메뉴 목록
- `GET /api/menus/{id}`: 메뉴 단건 조회
- `GET /api/menus/submenu/{id}`: 하위 메뉴 목록
- `POST /api/menus`: 메뉴 생성
- `PUT /api/menus/{id}`: 메뉴 수정
- `DELETE /api/menus/{id}`: 메뉴 삭제

## Request (MenuRequest)
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
  "attribute1": null,
  "attribute2": null,
  "attribute3": null,
  "attribute4": null,
  "attribute5": null,
  "attribute6": null,
  "attribute7": null,
  "attribute8": null,
  "attribute9": null,
  "attribute10": null,
  "createdBy": "admin",
  "lastUpdatedBy": "admin"
}
```

## Response (Menu)
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
  "attribute1": null,
  "attribute2": null,
  "attribute3": null,
  "attribute4": null,
  "attribute5": null,
  "attribute6": null,
  "attribute7": null,
  "attribute8": null,
  "attribute9": null,
  "attribute10": null,
  "creationDate": "2026-02-12T09:00:00",
  "createdBy": "admin",
  "lastUpdatedDate": "2026-02-12T09:00:00",
  "lastUpdatedBy": "admin"
}
```