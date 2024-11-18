7조 손흥민
=============

## 팀 역할 분담
<table>
  <thead>
    <tr>
      <th align="center">팀원</th>
      <th align="center">포지션</th>
      <th align="center">담당</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center"><b>이준석</b></td>
      <td align="center">Leader</td>
      <td align="left">
        - 주문, 결제, 리뷰 도메인 개발
      </td>
    </tr>
    <tr>
      <td align="center"><b>황시연</b></td>
      <td align="center">Member</td>
      <td align="left">
        - 회원가입 도메인 개발
      </td>
    </tr>
        <tr>
      <td align="center"><b>추지혜</b></td>
      <td align="center">Member</td>
      <td align="left">
        - 음식점, ai 도메인 개발
      </td>
    </tr>
  </tbody>
</table>


</br>

## 서비스 구성

- 사용자
  - 인증: 회원 가입 -> 로그인 -> 토큰 발급
  - 인가: 고객, 사장, 관리자, 마스터
- 음식점
  - 음식점 생성
    - 관리자 이상
  - 음식 카테고리
    - 추가, 조회, 수정, 삭제
  - 음식 메뉴
    - 추가, 조회, 수정, 삭제
- 주문
  - 생성, 조회, 수정, 삭제
- 결제
  - 주문에 대한 결제내역만 보유
- 리뷰
  - 작성
- AI
  - 질문과 답 저장

- 각 도메인별 권한 체크
  - AI : 생성(전부), 수정, 삭제 : OWNER
  - Category : 생성, 수정, 삭제 : MANAGER - MASTER / 조회 : OWNER - MASTER
  - Menu : 생성, 수정, 삭제 : OWNER - MASTER / 조회 : all
  - Store : 생성, 수정, 삭제 : MANAGER - MASTER / 조회 : all
  - Review : 생성 : CUSTOMER / 수정 : CUSTOMER , MANAGER , MASTER / 조회 : all
  - Region : 생성, 수정, 삭제 : MANAGER - MASTER / 조회 : OWNER - MASTER

</br>

## 실행방법 
- 배포 화면 URL: http://18.234.161.55:8080/


</br>

## 프로젝트 목적, 상세
- 프로젝트 개요: 기존의 배달앱을 벤치마킹한 주문 관리 플랫폼입니다. 이 프로젝트는 신규로 백엔드 개발을 먼저 하는 상황으로 진행 됩니다.
- 개발기간 : 11월 6일(수) ~ 11월 18(월)

</br>

## ERD
![ERD 이미지](https://raw.githubusercontent.com/hanghae-project-1/backend/dev/image/erd.jpeg)


</br>

## 기술 스택
- 백엔드
  - 스프링 부트, JWT , JPA
- 배포
  - AWS, 도커 컴포즈

- 아키텍처
  - 도메인 아키텍처
<details><summary> 패키지 구조</summary>

  ```java
  ├── DemoApplication.java
├── common
│   ├── config
│   │   ├── JpaConfig.java
│   │   ├── QueryDslConfig.java
│   │   ├── RestTemplateConfig.java
│   │   ├── SecurityAuditorAware.java
│   │   ├── SecurityConfig.java
│   │   └── jwt
│   │       ├── JWTFilter.java
│   │       ├── JWTUtil.java
│   │       ├── JwtAccessDeniedHandler.java
│   │       ├── JwtAuthenticationEntryPoint.java
│   │       └── LoginFilter.java
│   ├── entity
│   │   └── BaseEntity.java
│   ├── exception
│   │   ├── CommonExceptionHandler.java
│   │   └── Error.java
│   ├── model
│   │   └── response
│   │       └── Response.java
│   └── util
│       └── PagingUtils.java
└── domain
    ├── ai
    │   ├── controller
    │   │   ├── AiController.java
    │   │   └── docs
    │   │       └── AiControllerDocs.java
    │   ├── dto
    │   │   ├── request
    │   │   │   └── AiRequestDto.java
    │   │   └── response
    │   │       └── AiResponseDto.java
    │   ├── entity
    │   │   └── Ai.java
    │   ├── exception
    │   │   ├── AiException.java
    │   │   └── NotFoundAiException.java
    │   ├── mapper
    │   │   └── AiMapper.java
    │   ├── repository
    │   │   └── AiRepository.java
    │   └── service
    │       └── AiService.java
    ├── category
    │   ├── controller
    │   │   ├── CategoryMenuController.java
    │   │   └── docs
    │   │       └── CategoryMenuControllerDocs.java
    │   ├── dto
    │   │   ├── request
    │   │   │   └── CategoryMenuRequestDto.java
    │   │   └── response
    │   │       └── CategoryMenuResponseDto.java
    │   ├── entity
    │   │   └── CategoryMenu.java
    │   ├── exception
    │   │   ├── CategoryMenuException.java
    │   │   ├── DuplicateCategoryMenuException.java
    │   │   └── NotFoundCategoryMenuException.java
    │   ├── mapper
    │   │   └── CategoryMenuMapper.java
    │   ├── repository
    │   │   └── CategoryMenuRepository.java
    │   └── service
    │       └── CategoryMenuService.java
    ├── entity
    │   └── common
    │       ├── CommonConstant.java
    │       └── Status.java
    ├── menu
    │   ├── controller
    │   │   ├── MenuController.java
    │   │   └── docs
    │   │       └── MenuControllerDocs.java
    │   ├── dto
    │   │   ├── request
    │   │   │   └── MenuRequestDto.java
    │   │   └── response
    │   │       └── MenuResponseDto.java
    │   ├── entity
    │   │   └── Menu.java
    │   ├── exception
    │   │   ├── DuplicateMenuException.java
    │   │   ├── MenuException.java
    │   │   ├── NotFoundMenuAndStoreException.java
    │   │   └── NotFoundMenuException.java
    │   ├── mapper
    │   │   └── MenuMapper.java
    │   ├── repository
    │   │   └── MenuRepository.java
    │   └── service
    │       └── MenuService.java
    ├── order
    │   ├── controller
    │   │   ├── OrderController.java
    │   │   └── docs
    │   │       └── OrderControllerDocs.java
    │   ├── entity
    │   │   ├── Order.java
    │   │   └── OrderDetail.java
    │   ├── exception
    │   │   ├── IncorrectTotalPriceException.java
    │   │   ├── IsNotYourOrderException.java
    │   │   ├── NotFoundOrderException.java
    │   │   ├── OrderException.java
    │   │   └── ReturnPeriodPassedException.java
    │   ├── mapper
    │   │   └── OrderMapper.java
    │   ├── model
    │   │   ├── request
    │   │   │   ├── OrderDetailRequestDTO.java
    │   │   │   └── OrderRequestDTO.java
    │   │   └── response
    │   │       ├── BaseOrderDTO.java
    │   │       ├── OrderDetailResponseDTO.java
    │   │       ├── OrderResponseDTO.java
    │   │       └── StoreOrderResponseDTO.java
    │   ├── repository
    │   │   ├── OrderRepository.java
    │   │   └── custom
    │   │       ├── OrderRepositoryCustom.java
    │   │       └── impl
    │   └── service
    │       └── OrderService.java
    ├── region
    │   ├── controller
    │   │   ├── RegionController.java
    │   │   └── docs
    │   │       └── RegionControllerDocs.java
    │   ├── dto
    │   │   ├── request
    │   │   │   └── RegionRequestDto.java
    │   │   └── response
    │   │       └── RegionResponseDto.java
    │   ├── entity
    │   │   └── Region.java
    │   ├── exception
    │   │   ├── NotFoundRegionException.java
    │   │   └── RegionException.java
    │   ├── mapper
    │   │   └── RegionMapper.java
    │   ├── repository
    │   │   └── RegionRepository.java
    │   └── service
    │       └── RegionService.java
    ├── review
    │   ├── controller
    │   │   ├── ReviewController.java
    │   │   └── docs
    │   │       └── ReviewControllerDocs.java
    │   ├── entity
    │   │   └── Review.java
    │   ├── exception
    │   │   ├── IsNotYourReviewException.java
    │   │   ├── NotFoundReviewException.java
    │   │   ├── PurchaseIsNotConfirmedException.java
    │   │   └── ReviewException.java
    │   ├── mapper
    │   │   └── ReviewMapper.java
    │   ├── model
    │   │   ├── request
    │   │   │   ├── BaseReviewRequestDTO.java
    │   │   │   └── ReviewRequestDTO.java
    │   │   └── response
    │   │       ├── ReviewListResponseDTO.java
    │   │       └── ReviewResponseDTO.java
    │   ├── repository
    │   │   └── ReviewRepository.java
    │   └── service
    │       └── ReviewService.java
    ├── store
    │   ├── controller
    │   │   ├── StoreController.java
    │   │   └── docs
    │   │       └── StoreControllerDocs.java
    │   ├── dto
    │   │   ├── request
    │   │   │   └── StoreRequestDto.java
    │   │   └── response
    │   │       ├── StoreDetailResponseDto.java
    │   │       ├── StoreListResponseDto.java
    │   │       └── StoreResponseDto.java
    │   ├── entity
    │   │   └── Store.java
    │   ├── exception
    │   │   ├── DuplicateStoreNameException.java
    │   │   ├── IsNotYourStoreException.java
    │   │   ├── NotFoundStoreException.java
    │   │   └── StoreException.java
    │   ├── mapper
    │   │   └── StoreMapper.java
    │   ├── repository
    │   │   ├── StoreRepository.java
    │   │   └── custom
    │   │       ├── StoreCustomRepository.java
    │   │       └── impl
    │   └── service
    │       └── StoreService.java
    └── user
        └── common
            ├── controller
            │   ├── JoinController.java
            │   ├── LoginController.java
            │   ├── UserController.java
            │   └── docs
            ├── dto
            │   ├── CustomUserDetails.java
            │   ├── JoinRequestDto.java
            │   └── UserInfoRequestDto.java
            ├── entity
            │   ├── Role.java
            │   └── User.java
            ├── exception
            │   ├── DuplicateUsernameExistsException.java
            │   ├── NotPoundUriException.java
            │   ├── NotPoundUserException.java
            │   ├── OwnerMismatchException.java
            │   ├── UserException.java
            │   └── UserWithdrawnException.java
            ├── mapper
            │   └── UserMapper.java
            ├── repository
            │   └── UserRepository.java
            └── service
                ├── CustomUserDetailService.java
                ├── JoinService.java
                ├── PasswordChangeService.java
                ├── UserService.java
                ├── UserValidService.java
                └── UserWithdrawnService.java

  ```
</details>

## API 문서 
- URL :18.234.161.55:8080/swagger-ui/index.html
