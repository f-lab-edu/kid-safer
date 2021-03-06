# 🧒내 아이의 안전한 등하원을 위해, kid-safer

* 부모와 아이의 등원 또는 하원을 돕는 등하원 도우미를 연결해주는 서비스를 제공합니다.
* 부모는 등하원 도우미를 신청하는 게시글을 작성할 수 있으며, 도우미는 게시된 글을 토대로 지원할 수 있습니다.

## 📌프로젝트 목표
* 객체지향 기본 원리를 적용하여 Spring IoC/DI, AOP, PSA 활용과 의미 있는 코드를 작성하는 것이 목표입니다.
* 라이브러리 및 애노테이션 적용시 의미를 이해하고 사용 목적을 고려하여 작성합니다.
* 단순히 기능 구현을 하는 것이 아니라 대용량 트래픽 처리까지 고려한 기능을 구현하는 것이 목표입니다.
* Jenkins를 이용한 CI/CD 환경 구축합니다.

## 📌사용 기술

* Spring boot
* Gradle
* Java8

(TBD)


## 📌Git Flow

### 브랜치 전략

Git Flow전략을 사용하여 브랜치를 관리합니다.  
기능 개발은 feature 브랜치에서 진행하며, Pull Request에 리뷰를 진행한 후 merge를 진행합니다.   

현재 개발 진척사항을 확인하고 싶다면 PR를 확인해주세요.

kid-safer PR 내역 : https://github.com/f-lab-edu/kid-safer/pulls

<p align="center">
 <img src="https://woowabros.github.io/img/2017-10-30/git-flow_overall_graph.png" width="400">
</p>

* `main` : 배포시 사용하는 브랜치
* `develop` : 다음 출시 버전을 개발하는 브랜치
	* 다음 릴리즈를 위해 언제든 배포될 수 있는 상태
* `feature` : 기능을 개발하는 브랜치
	* 기능을 완성할 때 까지 유지하며, 완성시 `develop`브랜치로 merge
	* `feature`는 이슈번호를 기준으로 생성
* `release` : 릴리즈를 준비하는 브랜치(QA)
* `hotfix` : 배포 버전에서 생긴 문제로 긴급한 트러블 슈팅이 필요할 때 개발이 진행되는 브랜치

#### 참고 문헌 
 * 우린 Git-flow를 사용하고 있어요 : https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html

### Commit 메세지 규칙

* <타입>: <제목>의 형식으로 입력하며, 제목은 최대 50글자까지만 입력한다.
* 제목 첫 글자를 대문자로 입력한다.
* 제목은 명령문으로 작성한다.
* 제목 끝에는 마침표(.)를 입력하지 않는다.
* 제목과 본문을 한 줄 띄워 분리한다.
* 본문은 "어떻게"보다 "무엇을", "왜"를 설명한다.
* 본문에 여러 줄의 메시지를 작성할 땐 "-"로 구분한다.

#### 타입
* feat : 기능(새로운 기능)
* fix : 버그 ( 버그 수정)
* refactor : 리팩토링
* style : 스타일(코드 형식, 세미콜론 추가 등 비즈니스 로직에 영향이 없는 부분)
* docs : 문서 (문서 추가, 수정, 삭제)
* test : 테스트 ( 테스트 코드 추가, 수정, 삭제 등 비즈니스 로직에 영향이 없는 부분)
* chore : 기타 변경 사항(빌드 스크립트 수정 등)

#### 참고 문헌 
 * 좋은 커밋 메시지를 작성하기 위한 커밋 템플릿 만들어보기 : https://junwoo45.github.io/2020-02-06-commit_template/
