# JPA 규칙 (세션 요약)

## 공통 엔티티 설계
- 모든 엔티티에 공통 필드가 존재하면 `@MappedSuperclass` 기반의 `BaseEntity`로 공통화한다.
- 공통 필드 예: `attribute1~10`, `createdBy`, `creationDate`, `lastUpdatedBy`, `lastUpdatedDate`.
- `@EntityListeners(AuditingEntityListener.class)`를 통해 Auditing 필드를 자동 관리한다.
- 상속 구조에서 빌더가 필요하면 Lombok `@SuperBuilder`를 사용한다.
- Lombok 상속 빌더를 사용할 경우 기본 생성자가 필요하므로 `@NoArgsConstructor`를 추가한다.

## 업데이트 방식
- 기본 업데이트는 기존 엔티티를 조회한 뒤 필드 set → `save`로 반영한다.
- builder는 새 엔티티 생성이므로 필드 누락 시 `null`이 저장된다. 전체 업데이트 스냅샷이 아닌 경우 사용하지 않는다.
- 전체 업데이트에서 프런트가 `null`을 보내면 그대로 `null`로 저장한다.
- JPA dirty checking은 실제로 변경된 필드만 업데이트한다. 값을 set하지 않으면 컬럼은 변경되지 않는다.

## Attributes 처리
- 원칙적으로 `attribute1~10` 컬럼은 엔티티에 매핑하지 않는다.
  - 엔티티에 매핑하지 않으면 JPA가 해당 컬럼을 조회/업데이트하지 않는다.
  - DTO에서도 기본적으로 다루지 않는다. 필요 시 별도 API/쿼리로 처리한다.

## 네이티브 업데이트 사용 기준
- 도메인 규칙과 감사 필드를 유지하려면 엔티티 기반 업데이트(`save`)를 기본으로 한다.
- 대량 성능 최적화가 명확히 필요한 경우에만 네이티브 UPDATE를 고려한다.
- 조회 후 네이티브 UPDATE를 섞는 구조는 이점이 적으므로 피한다.

## 조회 응답과 사용자 매핑
- `createdBy`/`lastUpdatedBy`가 `tb_cm_user.emp_no`인 경우, 목록 응답에서는 `IN` 조회로 한번에 이름을 매핑한다.
- 단건 응답은 `findByEmpNo`로 조회한다.
- 현재 범위에서 건수가 많지 않으면 2-query 방식(목록 + 사용자 IN 조회)을 사용한다.
