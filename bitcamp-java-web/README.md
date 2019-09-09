# Web Application 프로젝트

## 준비

프로젝트 폴더 만들기
```
> mkdir bitcamp-java-web
> cd bitcamp-java-web
> gradle init 
... 지시에 따라 폴더를 자바 프로젝트 폴더로 만든다.
```

웹 프로젝트로 전환하기
```
- build.gradle 파일 편집
  - 'war', 'eclipse-wtp' 플러그인 추가
  - 소스파일 인코딩과 버전 지정
  - servlet-api 라이브러리 추가
  
> gradle eclipse     <--- 실행 
```

이클립스 IDE로 임포트
```
- 웹 자원을 둘 폴더 생성
  - src/main/webapp 폴더 생성 
  - src/main/webapp/index.html 테스트 페이지 생성
  
- 웹 자원 폴더를 추가했으면 이클립스 설정 파일을 갱신해야 한다.
  - > gradle cleanEclipse
  - > gradle eclipse
  - 프로젝트 갱신
```

이클립스에 톰캣 서버 등록
```
1) window / Preferences 메뉴 클릭
2) Server 노드 클릭
3) Runtime Environments 노드 클릭
4) [Add] 버튼을 클릭하여 로컬에 설치한 톰캣 서버의 폴더를 등록한다.
```

프로젝트를 테스트할 때 사용할 톰캣 서버 실행 환경 추가
```
1) 이클립스 perspective가 'Java EE'로 되어 있는지 확인
  - window / perspective / Open Perspective 메뉴 클릭
  - 'Java EE' 노드 선택
2) 'Servers' 뷰 클릭
  - 컨텍스트 메뉴: New / Server 클릭
  - 톰캣 9 서버 추가
    - 원래 톰캣을 설치한 폴더에서 /conf 밑에 있던 설정 파일을 이클립스의 작업 폴더로 복사해 온다.
```

## 웹 프로젝트를 톰캣 테스트 서버에 배치하기

```
1) 'Servers' 뷰 클릭 
2) 배치할 테스트 서버를 선택
3) 컨텍스트 메뉴 출력
4) 'Add and remove...' 메뉴 클릭
5) 테스트 서버에 배치할 웹 프로젝트를 추가
```

## 웹 프로젝트 실행 및 테스트

```
1) 'Servers' 뷰 클릭
2) 테스트 서버를 선택
3) 컨텍스트 메뉴에서 'start/stop' 메뉴 클릭
4) 웹 브라우저에서 서버에 요청 
```








