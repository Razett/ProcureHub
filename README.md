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
   4-1. [사용자 인증(로그인) 및 세션 관리](#사용자-인증(로그인)-및-세션-관리)
   4-2. 
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
ProcureHub는 다음과 같은 주요 기능들로 구성되었습니다.

### 사용자 인증(로그인) 및 세션 관리

- **사용자 인증**: Spring Security를 통해 사용자 인증을 처리합니다.
- **암호 보호**: 사용자의 암호는 Bcrypt를 사용하여 단방향 암호화됩니다.
- **접근 제어**: 로그인이 이루어지기 전에는 모든 URL 접근이 차단됩니다.
- **세션 관리**: 로그인 성공 후, 사용자 정보는 Redis를 이용한 통합 세션 스토리지에 저장됩니다.
<details>
  <summary>클릭하여 펼치기</summary>
    <img width="600" src="https://github.com/user-attachments/assets/e42f9664-c13b-4ad9-8d49-ebbed2f5e940"/>
</details>
<br>

### 대시보드

대시보드는 사용자에게 다음과 같은 정보를 제공합니다:

- **사용자 정보**: 화면 상단에 로그인한 사용자의 정보가 표시됩니다.
- **업무 현황**:
  - **조달 계획, 발주, 입고**: 긴급, 경고, 자동 수정됨, 전체 진행중인 건의 수가 표시됩니다.
  - **출고**: 요청 건수와 전체 건수가 표시됩니다.
- **생산 계획**: 캘린더를 통해 생산 계획을 한눈에 확인하고, 즉시 추가하거나 변경할 수 있습니다.
- **생산 제품 현황**: 월별 생산 제품 현황(수량)을 나타내는 그래프가 있습니다.
- **업무 현황 표**: 월별 로그인한 사원의 업무 처리 현황이 표로 나타납니다.
- **발주, 입고, 출고 통계**: 6개월간의 월별 발주, 입고, 출고 건수가 그래프로 표시됩니다.

<details>
  <summary>클릭하여 펼치기</summary>
    <table>
      <tr>
        <td><img src="https://github.com/user-attachments/assets/0b15d22e-7c1f-4cbd-9288-34216d9ae995"/></td>
        <td><img src="https://github.com/user-attachments/assets/26a328aa-352a-4615-ad51-ac9b840f0c8e"/></td>
      </tr>
      <tr>
        <th>대시보드</th>
        <th>생산 계획 추가 및 수정</th>
      </tr>
    </table>
</details>
<br>

### 계약 관리

계약 관리 기능은 **업체**와 **견적** 메뉴로 구성되어 있으며, 각 메뉴의 세부 사항은 다음과 같습니다:

#### 업체
- **업체 목록**:
  - 업체 명, 주소, 내선 번호를 확인할 수 있습니다.
  - 등록된 업체를 클릭하면 상세정보 페이지로 이동하여, 해당 업체의 정보와 주고받은 견적 목록을 확인할 수 있습니다.
- **업체 등록**:
  - 사업자 등록번호, 업체 명, 내선번호, 주소를 입력받습니다.
  - 주소는 **카카오 우편번호 서비스**를 통해 손쉽게 입력할 수 있습니다.
  - 담당자 이름, 연락처, 거래 은행, 계좌번호도 입력받아 등록할 수 있습니다.
  
<details>
  <summary>클릭하여 펼치기</summary>
  <table>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/321cba38-6947-4384-a30f-1c4f430ff937"/></td>
      <td><img src="https://github.com/user-attachments/assets/87c1a114-8361-43cd-b5be-a0605719fba8"/></td>
    </tr>
    <tr>
      <th>업체 목록 및 상세정보</th>
      <th>업체 등록</th>
    </tr>
      <td><img src="https://github.com/user-attachments/assets/c939acad-c09c-4fdd-9e4c-a1102d1aace6"/></td>
      <td><img src="https://github.com/user-attachments/assets/a20c3d6d-268a-41c7-9c91-c79c0239460d"/></td>
    </tr>
    <tr>
      <th>업체 정보 수정</th>
      <th>업체 상세정보 내 견적 추가 시</th>
    </tr>
  </table>
</details>

#### 견적
- **견적 목록**:
  - 전체 주고받은 견적 목록을 표 형태로 표시하며, 상태 부분에는 색상을 통해 견적의 상태를 나타냅니다.
  - 목록 검색 기능이 제공되어 원하는 견적을 쉽게 찾을 수 있습니다. (견적 코드, 견적 제목, 사업자등록번호, 회사명, 자재코드, 자재명으로 검색)
  - 견적 목록 행을 클릭하면 견적의 상세정보 페이지로 이동하며, 상세정보를 확인할 수 있습니다.
    - **견적 상세정보**:
      - 견적의 상세 정보를 확인할 수 있습니다.
      - 우측 상단의 출력 버튼을 통해 견적서를 출력할 수 있으며, 상대 업체로부터 받은 견적서 파일을 첨부할 수 있습니다.
      - 견적 수정이 가능하며, 계약 추가 버튼을 눌러 계약을 체결할 수 있습니다.
      - 계약 체결 시, 계약 날짜를 확인할 수 있습니다.
- **견적 등록**:
  - 업체의 사업자 등록번호 또는 업체명을 입력하면 자동완성 목록이 표시되며, 이를 선택하여 업체 정보를 입력합니다.
  - 한 견적에 여러 자재를 추가할 수 있으며, 자재 코드 또는 자재명을 입력하면 자동완성 목록이 나타나고, 이를 선택하여 자재를 추가합니다.
  - 견적서 파일을 첨부할 수 있습니다.

<details>
  <summary>클릭하여 펼치기</summary>
  <table>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/a8bdae75-2726-4cdd-a808-8e7a9621a5fb"/></td>
      <td><img src="https://github.com/user-attachments/assets/bb1dc347-cd9c-407b-92f3-1e15f82d1171"/></td>
    </tr>
    <tr>
      <th>견적 목록 (검색) 및 상세정보</th>
      <th>견적서 출력 및 파일 업로드</th>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/2067a555-290d-410b-a4e6-6561cd11240a"/></td>
      <td><img src="https://github.com/user-attachments/assets/8cd136c3-d914-445e-a611-def05ef12f3b"/></td>
    </tr>
    <tr>
      <th>견적 등록</th>
      <th>견적 수정</th>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/cf30948e-f6d7-467a-8ba6-e197995bd070"/></td>
      <td><img src="https://github.com/user-attachments/assets/eff30110-4a89-4ee6-b43c-0496c17c7967"/></td>
    </tr>
    <tr>
      <th>계약 등록 실패 시</th>
      <th>계약 등록 성공 시</th>
    </tr>
  </table>
</details>
<br>

### 자재 관리

자재 관리 기능은 **자재 목록**, **자재 등록**, **그룹 목록**, **창고 목록**으로 구성되어 있으며, 각 메뉴의 세부 사항은 다음과 같습니다:

#### 자재 목록
- 자재를 검색할 수 있으며, 자재의 상세 정보를 표 형태로 확인할 수 있습니다.
- 표의 행을 클릭하면 자재의 상세정보 페이지로 이동합니다. 여기서 자재의 도면을 추가하거나 기존 도면을 확인할 수 있습니다.
  - **상세정보**:
    - 자재의 상세정보와 도면을 확인할 수 있습니다.
    - 자재가 사용되는 제품의 목록과 6개월간 입고 및 출고 내역을 그래프로 시각화하여 수량 변화를 분석할 수 있습니다.

#### 자재 등록
- 자재의 상세 정보를 입력하여 자재를 등록합니다.
- 자재 등록 시, 자재가 속할 그룹과 저장할 창고를 선택해야 합니다.
- 자재 등록 시 도면 파일을 첨부하여 자재의 시각적 정보를 추가할 수 있습니다.

#### 그룹 목록
- 자재를 대중소 그룹으로 분류합니다.
- 그룹 이름 선택 시, 해당 그룹에 속한 자재의 목록을 표시합니다.
- 그룹을 추가하거나 그룹 이름을 변경할 수 있습니다.

#### 창고 목록
- 창고 목록을 관리합니다.
- 창고를 추가하거나 이름을 변경할 수 있습니다.
- 창고 삭제 시, 해당 창고에 포함된 자재는 자동으로 ‘기타 창고’로 이동됩니다.

<details>
  <summary>클릭하여 펼치기</summary>
  <table>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/7328a74d-c7a0-44f1-a771-ec3d1d5b8dfc"/></td>
      <td><img src="https://github.com/user-attachments/assets/19205e74-cc74-4bd8-8ee4-f0b6810c3050"/></td>
    </tr>
    <tr>
      <th>자재 목록 및 검색</th>
      <th>상세정보 및 도면</th>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/fb42a90d-a6dc-4105-86c8-a240908b4cd2"/></td>
      <td><img src="https://github.com/user-attachments/assets/59ced78f-382f-4d12-afc2-9af0a0b2f20f"/></td>
    </tr>
    <tr>
      <th>자재 등록</th>
      <th>자재 수정</th>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/b8219a1b-dd56-445b-905d-4736a1df6099"/></td>
      <td><img src="https://github.com/user-attachments/assets/10acb759-65be-4ea8-b158-0400534ee5d9"/></td>
    </tr>
    <tr>
      <th>그룹 목록</th>
      <th>창고 목록</th>
    </tr>
  </table>
</details>
<br>

### 조달 관리

조달 관리 기능은 **조달 계획 목록**으로 구성되어 있으며, 메뉴의 세부 사항은 다음과 같습니다.

#### 자동 생성 및 관리
- **자동 생성**: 생산 계획이 추가되면, 필요한 자재의 조달 계획이 자동으로 생성됩니다. 이 과정은 DB 트리거 기능을 통해 이루어지며, 조달 계획은 납기일을 기준으로 자동 정렬됩니다.
- **납기일 설정**: 조달 납기일은 생산 시작일 2일 전으로 자동 설정되어, 자재가 차질 없이 준비될 수 있도록 합니다.
- **생산 계획 수정**: 생산 계획이 수정되면, DB 트리거를 통해 조달 계획의 자재 필요 수량 및 가용 재고가 자동으로 업데이트됩니다.

#### 상태 표시 및 경고
- **리드타임 및 버퍼**:
  - 리드타임은 해당 자재의 공급 계약 중 가장 리드타임이 긴 값이 적용되어 표시됩니다.
  - 납기일 - (리드타임 + 버퍼 2일)이 5일 이하일 경우, 해당 항목은 노란색으로 표시되어 주의가 필요한 상태를 나타냅니다.
  - 납기일 - (리드타임 + 버퍼 2일)이 3일 이하일 경우, 빨간색으로 표시되어 매우 긴급한 상황임을 경고합니다.
  - 리드타임이 존재하지 않는 경우, 납기일 - (7일 + 버퍼 2일)로 계산합니다.
- **스케줄러 기능**: 매일 00시에 스프링 프레임워크의 스케줄러 기능을 통해 조달 계획을 다시 불러오고, 리드타임을 자동으로 계산하여 상태를 업데이트합니다.

#### 자재 및 발주 관리
- **자재 리스트**: 각 제품에 필요한 자재는 리스트로 표시되며, 자재 코드, 명칭, 규격, 현재 수량, 납품 소요 기간 등의 정보가 포함됩니다. 또한 리스트는 납기일이 가장 빠른 순으로 우선 정렬됩니다.
- **가용 재고 계산**: 동일한 제품에 대해 여러 조달 계획이 생성될 수 있으며, 가용 재고를 계산하여 현재 조달 계획에 필요한 자재 수량을 계산합니다.
- **발주 자동 생성**: 가용 재고를 바탕으로 발주가 필요한 경우, 미달 수량만큼 발주 항목이 자동으로 생성되고 발주 수량도 자동으로 수정됩니다.
- **계약 없는 자재**: 리드타임이 없는 자재는 유효한 계약이 없음을 의미하며, '계약 추가' 버튼을 통해 견적 목록으로 이동하여 필요한 경우 계약을 체결할 수 있습니다.

#### 조달 상태 관리
- **발주 추가**: 재고가 충분하지 않을 경우 발주가 자동으로 추가됩니다. 상태는 '발주 추가됨'으로 표시됩니다.
- **발주 실행**: 발주 실행 후 상태는 '발주됨'으로 업데이트되어 실제 발주가 진행 중임을 나타냅니다.
- **입고 처리**: 입고 항목이 도착 처리되면 상태는 '입고 중'으로 변경되며, 자재가 창고로 입고되는 과정입니다.
- **출고 완료**: 출고가 완료되면 상태는 '완료'로 변경되며, 조달 프로세스가 최종적으로 완료되었음을 의미합니다.

<details>
  <summary>클릭하여 펼치기</summary>
  <table>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/fa75cf9a-0a79-480e-a3ef-dd9decc89799"/></td>
      <td><img src="https://github.com/user-attachments/assets/bc79015a-7186-480e-80cf-2d81f91d13bc"/></td>
    </tr>
    <tr>
      <th>조달 계획 목록 및 가용 재고 계산</th>
      <th>조달 계획 내 계약 추가</th>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/dee84caf-638c-4768-b889-cde6740fdd65"/></td>
      <td><img src="https://github.com/user-attachments/assets/c1d70eb9-6796-45f3-ac41-dbb5483f0074"/></td>
    </tr>
    <tr>
      <th>리드타임 계산</th>
      <th>조달 계획의 상태</th>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/9a34b1c3-9b28-47ad-ad3a-dfc623d01073"/></td>
      <td></td>
    </tr>
    <tr>
      <th>조달 계획 목록 내 발주 수동 추가</th>
      <th></th>
    </tr>
  </table>
</details>
