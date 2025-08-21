# 🌿 한적서울 (HanjeokSeoul)

> "서울 도심 속, 한적한 쉼표를 찾아드립니다."

실시간 인구 밀집도 데이터를 기반으로, 사용자 위치 주변의 덜 붐비는 장소(식당, 카페, 공원)를 추천하는 모바일 서비스입니다.  
공공데이터와 사용자 리뷰를 결합한 커뮤니티 기반 추천 시스템으로, 조용한 장소를 찾고 싶은 사용자들의 감성적 니즈를 기술적으로 해결했습니다.

---

## 🔍 프로젝트 개요

- **개발 기간**: 2025.03 ~ 2025.05  
- **참여 인원**: 4명 (백엔드 2, 프론트 1, 데이터 1)  
- **담당 역할**: 기획, 백엔드 전반 설계 및 핵심 기능 구현 주도
- **성과**: 서울시 공공데이터 활용 경진대회 본선 진출 / Google Play 앱 출시 테스트중

---

## 🌟 주요 기능

- **위치 기반 장소 추천**:  
  사용자 좌표와 116개 기준 지역 간의 거리를 계산하여, 가장 가까운 지역 내 혼잡도가 낮은 장소를 추천합니다.

- **커뮤니티 제보 및 후기 흐름**:  
  사용자가 장소를 제보하고, 관리자의 승인을 거쳐 리뷰와 사진을 등록하는 커뮤니티 구조를 제공합니다.

- **이미지 업로드 처리**:  
  리뷰 이미지 파일을 AWS S3에 저장하고, 접근 가능한 URL을 DB에 연동합니다ㅁ.

- **JWT 인증 및 마이페이지**:  
  회원가입, 로그인, 내 리뷰 확인 등 사용자 인증 및 개인정보 기반 API를 구현했습니다.

- **CI/CD 및 HTTPS 대응**:  
  GitHub Actions, Docker, EC2 기반 자동 배포 파이프라인을 구축하고, HTTPS 대응을 위해 Render로 전환 배포를 수행했습니다.

---

## 🛠️ 기술 스택

| 구분 | 스택 |
|------|------|
| **🧠 Backend** | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-007396?style=for-the-badge&logo=hibernate&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white) ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black) |
| **⚙️ Infra / DevOps** | ![AWS EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![Amazon S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=amazonaws&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=GitHubActions&logoColor=white) ![Render](https://img.shields.io/badge/Render-46E3B7?style=for-the-badge&logo=render&logoColor=black) |
| **📱 Frontend (협업)** | ![React Native](https://img.shields.io/badge/React%20Native-61DAFB?style=for-the-badge&logo=react&logoColor=black) ![Expo](https://img.shields.io/badge/Expo-000020?style=for-the-badge&logo=expo&logoColor=white) |
| **📊 Data** | ![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white) ![Pandas](https://img.shields.io/badge/Pandas-150458?style=for-the-badge&logo=pandas&logoColor=white) |

---

## 🧩 아키텍처 구성

```plaintext
[React Native App]
        ↓
API 요청 (JWT 인증 포함)
        ↓
Spring Boot Backend
  ├── 위치 기반 추천 로직
  ├── 장소/제보/리뷰 CRUD API
  └── 이미지 업로드 → AWS S3 연동
        ↓
MySQL (RDS) / Swagger 문서화
        ↓
Python 전처리 (CSV 삽입, TM → WGS84 변환)
        ↓
서울시 실시간 인구 API + 사용자 리뷰 데이터 기반 추천
```
---
## 📸 주요 화면

📎 [화면 미리보기](./docs/screenshots.md)

---

## 🙋‍♀️ 주요 기여

- **DB 및 시스템 구조 설계** (`Place`, `Area`, `Review`, `Suggestion`)  
- **위치 기반 추천 알고리즘 구현** (좌표 거리 계산 + 인구 데이터 활용)  
- **제보/리뷰 & 이미지 업로드 전체 흐름 구축** (AWS S3 연동)  
- **CI/CD 파이프라인 구축** (GitHub Actions + Docker + EC2) → Render 전환으로 HTTPS 대응  
- **Swagger 기반 API 문서화** 및 프론트엔드 협업 최적화  

📎 [기여 상세 보기](./docs/contribution.md)

---

## 📫 연락처

**문예진 | Backend Developer**  
사용자 중심의 확장 가능한 시스템을 고민하고, 문제를 끝까지 책임지는 개발자입니다.

- ✉️ Email: i0209i80@gmail.com  
- 📱 Google Play: 앱 심사 중
