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
  <tr>
    <td>팀장</td>
    <td>팀원</td>
    <td>팀원</td>
    <td>팀원</td>
  </tr>
</table>
<br>

## 📋 목차

1. [소개](#소개)
2. [기술 스택](#기술-스택)
3. [설계](#설계)
4. [구현](#구현)
5. [라이센스](#라이센스)
<br>

## 소개
### 프로젝트 목표

이 프로젝트는 **엠아이티능력개발원**에서 진행한 **KDT 과정**의 일환으로, 주요 목표는 다음과 같습니다.

- **웹 어플리케이션 개발 지식 습득**: 웹 어플리케이션 개발과 관련된 이론과 실무 지식을 습득하고 이를 실천하여 숙달화하는 것을 목표로 하였습니다.
- **팀 단위 활동 경험**: 팀원들과 협력하여 프로젝트를 진행하면서 조직 내 활동 경험을 쌓고, 효과적인 소통 및 협업 능력을 향상시키는 것을 목표로 하였습니다.
- **개발 과정의 체계화 및 문서화**: 개발 과정을 체계적으로 관리하고, 문서화를 통해 프로젝트의 흐름을 명확히 하고 표준화하는 것을 목표로 하였습니다.

이 프로젝트는 위의 목표를 달성하기 위해 진행되었으며, 실질적인 개발 경험과 팀워크, 그리고 문서화의 중요성을 강조하고자 하였습니다.
<br>

## 기술 스택

이 프로젝트는 여러 웹 기술을 사용하여 개발되었습니다. 주요 기술 스택은 다음과 같습니다.

### 프로그래밍 언어
- **Java**: openJDK 17
- **JavaScript**: Vanilla JS, jQuery 3.7.1

### 프레임워크 및 라이브러리
- **Spring Boot**: 3.3.1
- **JPA Hibernate**: ORM 솔루션
- **Spring Security**: 사용자 로그인 처리
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **Lombok**: 코드 간결화
- **QueryDSL**: 타입 안전한 쿼리 생성

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
- **Trello**: 프로젝트 관리
- **Notion**: 문서화 및 협업
