# ProcureHub
스마트팩토리, ERP 시스템 구현 실습을 위한 자재 관리 및 조달(발주, 입•출고) 자동화 웹 애플리케이션 제작 프로젝트
<br>
<br>

## 👤 GoldenKids
GoldenKids [금쪽이파티]는 아래와 같은 멤버로 구성되었습니다.

<table>
  <tr>
    <td><img width="150" src="https://github.com/user-attachments/assets/dd897a2a-2991-481b-89e8-3988efb893bd"></td>
    <td><img width="150" src="https://github.com/user-attachments/assets/4dc24c5a-82be-4068-a8f2-bf8d2ffed585"></td>
    <td><img width="150" src="https://github.com/user-attachments/assets/87d7f229-6d73-4107-adec-b1edcd295280"></td>
    <td><img width="150" src="https://github.com/user-attachments/assets/48e197a4-3ed1-4444-b02d-50cf29af868e"></td>
  </tr>
  <tr>
    <th>정지원</th>
    <th>최병준</th>
    <th>장연희</th>
    <th>최지우</th>
  </tr>
<!--   <tr>
    <td>팀장</td>
    <td>팀원</td>
    <td>팀원</td>
    <td>팀원</td>
  </tr> -->
</table>
<br>

## 📋 목차

1. [소개](#소개)
2. [기술 스택](#기술-스택)
3. [설계](#설계)
4. [구현](#구현)
<br>

## 소개
### 프로젝트 목표

이 프로젝트는 **엠아이티능력개발원**[[#]](https://www.mit.or.kr "엠아이티능력개발원 홈페이지")에서 진행한 **KDT 과정**의 일환으로, 주요 목표는 다음과 같습니다.

- **웹 어플리케이션 개발 지식 습득**: 웹 어플리케이션 개발과 관련된 이론과 실무 지식을 습득하고 이를 실천하여 숙달화하는 것을 목표로 하였습니다.
- **팀 단위 활동 경험**: 팀원들과 협력하여 프로젝트를 진행하면서 조직 내 활동 경험을 쌓고, 효과적인 소통 및 협업 능력을 향상시키는 것을 목표로 하였습니다.
- **개발 과정의 체계화 및 문서화**: 개발 과정을 체계적으로 관리하고, 문서화를 통해 프로젝트의 흐름을 명확히 하고 표준화하는 것을 목표로 하였습니다.

이 프로젝트는 위의 목표를 달성하기 위해 진행되었으며, 실질적인 개발 경험과 팀워크, 그리고 문서화의 중요성을 강조하고자 하였습니다.
<br><br>

## 기술 스택

이 프로젝트는 여러 웹 기술을 사용하여 개발되었습니다. 주요 기술 스택은 다음과 같습니다.

### 프로그래밍 언어
- **Java**: openJDK 17
- **JavaScript**: Vanilla JS, jQuery 3.7.1

### 프레임워크 및 라이브러리
- **Spring Boot**: 3.3.1 (Spring Core 6.1.10)
- **JPA Hibernate**: ORM 솔루션
- **Spring Security**: 사용자 로그인 처리
- **Thymeleaf**: 3.1.2, 서버 사이드 템플릿 엔진
- **Bootstrap**: 5.2.3, CSS 프레임워크, 반응형 디자인 및 UI 구성
- **Lombok**: 1.18.32, 코드 간결화
- **QueryDSL**: 5.1.0, 복잡한 쿼리 생성
- **HikariCP**: 5.1.0, 고성능 JDBC 커넥션 풀

### 데이터베이스
- **MariaDB**: 10.6.18
- **Redis**: 6.0.16 (통합 세션 스토리지)

### 빌드 도구
- **Gradle**: 빌드 및 의존성 관리

### 서버 환경
- **Ubuntu Server**: 22.04 LTS
- **Nginx**: 웹 서버 및 리버스 프록시
- **Tomcat**: 서블릿 컨테이너

### 도구
- **Git**: 버전 관리
- **GitHub**: 원격 저장소 호스팅
- **IntelliJ IDEA**: 통합 개발 환경(IDE)
- **Trello**: 프로젝트 관리 [[#]](https://trello.com/b/MTJkvgDV)
- **Notion**: 문서화 및 협업
- **Jenkins**: 지속적 통합 및 배포 자동화
- **dbdiagam.io**: ERD 제작 도구
<br><br>

## 설계

이 프로젝트의 설계는 다음과 같은 주요 구성 요소로 이루어져 있습니다.
<br>

### 아키텍쳐

이 웹 애플리케이션은 **MVC (Model-View-Controller)** 패턴을 사용하여 개발되었으며, 구조도는 아래와 같습니다.
<details>
  <summary>클릭하여 펼치기</summary>
    <img width="500" src="https://github.com/user-attachments/assets/e783c0ee-f805-4e1e-9614-1f75bbc25bc2"/>
</details>
<br>

이 웹 애플리케이션 내 파일 입•출력을 처리하는 웹 애플리케이션(ProcureHubFiles[[#]](https://github.com/Razett/ProcureHubFiles "ProcureHubFiles GitHub")) 또한 **MVC** 패턴을 사용하여 개발되었으며, 구조도는 아래와 같습니다.
<details>
  <summary>클릭하여 펼치기</summary>
    <img width="500" src="https://github.com/user-attachments/assets/23735213-615b-42c4-b3ac-501a4c573a51"/>
    <img width="500" src="https://github.com/user-attachments/assets/8b0637fa-e6bb-45e8-823d-b8a282d3cd53"/>
</details>
<br>

### 데이터베이스 모델링

데이터 베이스는 다음과 같은 테이블로 구성됩니다.
<blockquote class="trello-card"><a href="https:&#x2F;&#x2F;trello.com&#x2F;c&#x2F;naliNuIe">Trello - DB 테이블 정의서</a></blockquote>
<br>

ERD(엔티티-관계 다이어그램)는 다음과 같습니다.
<blockquote class="trello-card"><a href="https:&#x2F;&#x2F;trello.com&#x2F;c&#x2F;oLb12DlK">Trello - DB ER다이어그램</a></blockquote>
<br>

### 화면 별 기능 정의 및 정책
화면 별 기능 정의 및 정책서는 다음과 같습니다.
<blockquote class="trello-card"><a href="https:&#x2F;&#x2F;trello.com&#x2F;c&#x2F;KWKy51Lc">Trello - 화면 기능 정의 및 정책서</a></blockquote>
<br>

### UI
유저 인터페이스는 사용자 편의를 고려하여 설계되었습니다. 주요 디자인 원칙은 다음과 같습니다.

- **효율적인 데이터 입력**: 사용자가 여러 값을 반복적으로 입력할 필요 없이, 최소한의 화면 이동으로 여러 작업을 한 화면에서 수행할 수 있도록 구성했습니다. (모달)
- **직관적인 데이터 시각화**: 표를 사용하여 다양한 데이터를 한눈에 확인할 수 있게 하여, 정보 접근성을 높였습니다. (목록)
- **진행 상태 확인**: 영역을 분할하여 각 프로세스의 진행 상황을 명확히 시각화하여, 사용자가 현재 상태를 쉽게 파악할 수 있도록 디자인했습니다. (발주, 입고)
- **색상 기반 상태 표시**: 색상으로 상태를 구분하여 빠른 인식과 시각적 피드백을 제공합니다. (견적, 조달 계획)
<br>

UI 설계는 와이어프레임 도구인 Figma[[#]](https://url.kr/fwuxm5)를 통해 진행하였으며, 결과는 다음과 같습니다.
<blockquote class="trello-card"><a href="https:&#x2F;&#x2F;trello.com&#x2F;c&#x2F;QbIRB6bZ">Trello - 디자인 시안 확정</a></blockquote>
<details>
  <summary>클릭하여 펼치기</summary>
  <table>
    <tr>
      <td rowspan="2"><img width="600" src="https://github.com/user-attachments/assets/8ce2c8c0-ed9b-4fec-87b4-899c755b28e7"/></td>
      <td><img width="500" src="https://github.com/user-attachments/assets/1c313f78-e504-413c-895e-a77e41b0d032"/></td>
    </tr>
    <tr>
      <td><img width="500" src="https://github.com/user-attachments/assets/9c1d2a0f-9ab9-406b-a7a3-b92b5fdb041a"/></td>
    </tr>
  </table>
</details>
<br>

### 테스트
소프트웨어의 품질을 보장하고, 모든 기능이 예상대로 작동하는지 확인하기 위해 계획한 **테스트 게획서**는 다음과 같습니다.
<blockquote class="trello-card"><a href="https:&#x2F;&#x2F;trello.com&#x2F;c&#x2F;zEPiRwJw">Trello - 테스트 계획서</a></blockquote>
<br>

### URL 설계
웹 애플리케이션의 **URL 설계서**는 다음과 같습니다.
<blockquote class="trello-card"><a href="https:&#x2F;&#x2F;trello.com&#x2F;c&#x2F;yY1hNkV5">URL 설계</a></blockquote>
<br>

### 서버 및 배포
서버는 Nginx가 사용자의 요청을 라운드 로빈 방식으로 WAS 1과 WAS 2에 분배하며, 각 WAS는 MariaDB와 파일 서버와 연결되어 데이터와 파일 자원을 접근하여 요청을 처리합니다. 모든 서버는 Amazon EC2 인스턴스에서 운영됩니다.
서버 구성도는 아래와 같습니다.
<details>
  <summary>클릭하여 펼치기</summary>
  <table>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/e396f111-429c-477c-8c65-ee74985eba3e"></td>
      <td><img src="https://github.com/user-attachments/assets/4f5a2bd4-2f7d-48d9-ab41-650f6f02c754"></td>
    </tr>
  </table>
</details>
<br>

배포는 GitHub의 WebHook 및 Jenkins Pipeline을 통해 두 개의 WAS에 롤링 업데이트 방식으로 진행됩니다. 이는 서비스 중단 없이 배포를 진행하여 사용자에게 연속적인 서비스를 제공합니다.
배포 구조도는 다음과 같습니다.
<details>
  <summary>클릭하여 펼치기</summary>
  <img width="1000" src="https://github.com/user-attachments/assets/886069a0-1f83-4436-ab4d-04a362a671e7">
</details>
<br>

## 구현
