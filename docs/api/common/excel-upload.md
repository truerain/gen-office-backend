# 엑셀/CSV 업로드 임시 적재 설계

## 목적
엑셀 또는 CSV 파일을 업로드하여 내용을 분석한 뒤 임시 테이블에 적재하고, 업로드키로 조회할 수 있는 공통 업로드 흐름을 정의한다.

## 범위
- 업로드 파일 형식: XLSX, CSV (추후 확장 가능)
- 처리 방식: 비동기(권장) + 진행률 조회
- 데이터 보관: 임시 테이블 + 업로드 세션 메타 테이블
- 보관 기간: 기본 48시간(조정 가능)

## 설계 개요
### 핵심 흐름
1. 파일 업로드 요청
2. 업로드 세션 생성 및 업로드키 발급
3. 파일 파싱/검증
4. 임시 테이블 적재
5. 상태 업데이트 및 조회 제공

### 업로드키 정책
- 서버에서 생성: UUIDv4 기반 난수 문자열 사용
- 생성 예시 (Java): `UUID.randomUUID().toString()`
- DB에 unique index로 중복 방지
- 조회 시 `upload_key + user_id(또는 tenant_id)` 조건 필수
- 만료 기한 부여(기본 48시간)
- 로그/URL에 원문 키 노출 최소화

## 데이터 모델(권장)
### upload_session (업로드 세션)
- upload_key (PK)
- user_id
- status: UPLOADING | VALIDATING | READY | FAILED | EXPIRED
- original_filename
- content_type
- file_size
- row_count
- error_count
- error_summary
- created_at
- expires_at

### temp_{domain} (업로드 임시 테이블)
- upload_key (FK)
- row_no
- data_json 또는 도메인 컬럼
- validation_status: OK | ERROR
- error_code
- error_message
- created_at

## 리스크 및 처리 방안
### 1. 권한/보안
- 리스크: 업로드키만으로 조회 시 유출 위험
- 처리: 조회 API에 user_id/tenant_id 조건 필수, 키 난수화, 만료 정책 적용

### 2. 임시 데이터 누적
- 리스크: 스토리지/성능 저하
- 처리: 만료 배치 정리, 상태 EXPIRED 처리, 삭제 이력 기록

### 3. 파싱/검증 실패
- 리스크: 부분 실패, 데이터 불일치
- 처리:
  - 정책 선택: 전량 실패 또는 부분 성공
  - 에러 행을 임시 테이블에 기록, 오류 요약 반환
  - 유효성 규칙 명시화(필수 컬럼, 타입, 범위)

### 4. 대용량 성능
- 리스크: 타임아웃/메모리 폭주
- 처리: 스트리밍 파싱, 배치 insert, 비동기 처리, 진행률 조회 API

### 5. CSV/Excel 인젝션
- 리스크: 수식 실행 유도
- 처리: 수식 시작 문자는 이스케이프 또는 제거

### 6. 템플릿 변경
- 리스크: 컬럼 구조 변경으로 실패
- 처리: 템플릿 버전 관리, 컬럼 매핑 테이블 운영

## 구현 방안
### API 설계(예시)
1. 업로드 시작
- POST /api/common/excel-upload
- Request: multipart/form-data(file)
- Response: upload_key, status

2. 업로드 상태 조회
- GET /api/common/excel-upload/{uploadKey}
- Response: status, row_count, error_count, error_summary

3. 임시 데이터 조회
- GET /api/common/excel-upload/{uploadKey}/rows
- Query: page, size, validation_status

### 처리 흐름(권장)
1. 업로드 수신 후 upload_session 생성 (status=UPLOADING)
2. 파일 저장 또는 스트리밍 파싱
3. VALIDATING 상태 전환
4. 배치 적재 (temp_{domain})
5. 완료 시 READY, 실패 시 FAILED
6. 만료 배치로 EXPIRED 처리 및 데이터 삭제

### 구현 세부
- 파일 크기 제한 및 확장자 검증
- CSV는 인코딩(UTF-8/CP949) 탐지 또는 옵션 제공
- 엑셀은 Apache POI(Streaming) 사용
- 트랜잭션: 세션 메타는 별도 트랜잭션, 데이터 적재는 배치 단위 커밋
- 실패 시 에러 요약과 행 단위 에러 기록

## 운영 가이드
- 기본 만료: 48시간
- 배치 정리 주기: 1일 1회
- 대용량 기준: 1만 행 초과 시 비동기 강제

