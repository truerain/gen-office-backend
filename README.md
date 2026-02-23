# gen-office-backend
백오피스 프레임워크(Spring Boot + Postgresql)

### 기술 스택
- JPA : ORM  
- QueryDSL : ORM에서 Query 수행 가능 하도록    
- SpringDoc : 소스 코드를 분석하여 OpenAPI Spec(JSON/YAML)을 생성하는 '두뇌' 역할을 합니다.  
- Scalar : pringdoc이 만든 데이터를 가져와서 사용자에게 보여주는 'UI 전용' 라이브러리입니다. 기존의 투박한 Swagger UI 대신 Scalar를 씌우면 훨씬 예쁘고 쓰기 편한 문서가 됩니다.
- JavaMainSender :
```
  dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-mail'
  }
```  
- 메일 Context는 Thymeleaf 템플릿으로
- 메일 Context 편집은 Rich Text Editor(예: TipTap, Quill, CKEditor)


### 프로젝트 디렉토리 구조 견본
global                     # 공통 설정 및 유틸리티  
│   ├── config             # Springdoc, Scalar, QueryDSL 설정  
│   ├── error              # ExceptionHandler, Problem Details 정의  
│   └── common             # 공통 응답(Response) 및 BaseEntity  
│  
├── mis                   # 핵심 비즈니스 로직 (도메인별 분리)  
│   ├── userEntity              # [도메인 1] 결산 관리  
│   │   ├── api            # Controller (Springdoc 어노테이션 포함)  
│   │   ├── application    # Service (비즈니스 로직)  
│   │   ├── dao            # Repository & QueryDSL Custom Repository  
│   │   ├── domain         # Entity & Embedded Types (Money, Tax 등)  
│   │   └── dto            # Request/Response DTO (Scalar 문서화용)  
│   │  
│   ├── journal            # [도메인 2] 전표 관리  
│   │   ├── api  
│   │   ├── application  
│   │   ├── dao  
│   │   ├── domain  
│   │   └── dto  
│   │  
│   └── budget             # [도메인 3] 예산 관리...  
│  
└── infrastructure         # 외부 시스템 연동 (메일, 파일 업로드 등)  

#### Domain 내부 기준 
mis/admin/userEntity/  
   UserController  
   UserService  
   User  
   UserRequest  
   dao/  
      UserRepository
      UserQueryRepository   // QueryDSL

• QueryDSL은 책임이 명확해서 dao 분리만으로도 구조적 이점 큼  
• 나머지는 필요해질 때만 점진적으로 분리 가능  

#### 확장 시점 기준   
• DTO가 늘어나면 dto/  
• API가 복잡해지면 api/  
• 서비스 로직이 커지면 application/ 또는 service/로 분리  
    즉, 도메인 폴더 유지 + QueryDSL 전용 dao 분리로 시작하고, 복잡도 증가에 맞춰 계층을 늘리는 게 최적입니다.


