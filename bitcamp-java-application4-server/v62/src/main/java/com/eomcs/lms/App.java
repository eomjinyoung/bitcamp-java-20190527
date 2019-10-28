// v62 : 뷰 컴포넌트에 Tiles 도입하기
package com.eomcs.lms;

// 작업1: Tiles 라이브러리를 프로젝트에 추가
// => build.gradle 변경
//    - tiles-jsp 라이브러리 추가(mvnrepository.com)
// => 'gradle eclipse' 실행
// => 이클립스에서 프로젝트 갱신
//
// 작업2: Spring WebMVC 의 Java Config 변경 
// => com.eomcs.lms.config.WebConfig 변경
//    - ViewResolver 에 viewClass 설정
//    - TilesConfigurer 객체 추가
//
// 작업3: Tiles 템플릿 엔진이 사용할 설정 파일 준비
// => /webapp/WEB-INF/defs/tiles.xml 생성
//    - 템플릿 레이아웃에 삽입될 타일을 정의한다.
//
// 작업4: 템플릿 JSP 파일 준비
// => /webapp/WEB-INF/tiles/template.jsp 생성
//    - 템플릿 페이지의 레이아웃을 설계한다.
// => /webapp/WEB-INF/tiles/header.jsp 생성
//    - 템플릿 페이지에 삽입될 header.jsp를 생성한다.
// => /webapp/WEB-INF/tiles/footer.jsp 생성
//    - 템플릿 페이지에 삽입될 footer.jsp를 생성한다.
//
// 작업5: 템플릿에 삽입될 본문 JSP를 생성
// => /webapp/WEB-INF/views/*/*.jsp 생성
//    - 템플릿 페이지에 삽입될 JSP를 생성한다. 
//    - 기존 JSP 파일을 복사하여 편집한다.
//
//
// dummy 클래스!
// => 기존 버전에서 계속 존재했던 클래스라서 그대로 둠.
// => 단지 버전의 목표에 대한 설명을 기록하기 위해 존재함.
// => 프로젝트에서 사용되지 않음!
//
public class App {
}










