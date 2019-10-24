// v61 : Back-end 와 Front-end 분리하기
package com.eomcs.lms;

// 작업1: 게시물 관리
// => /webapp/html/board-1/list.html 생성
// => /webapp/html/board-1/form.html 생성
//
// 작업2: npm(node package maneger)으로 외부 CSS, JS 라이브러리 파일 관리하기 
// => 콘솔에서 /webapp 폴더로 이동
// => 'npm init' 실행
//    - package.json 파일 생성
//    - 자바스크립트 라이브러리에 대한 설정 파일
// => 'npm install bootstrap' 실행
//    - npm을 통해 외부 라이브러리를 다운로드 한다.
//    - npm을 실행한 폴더에 node_modules 디렉토리를 자동 생성하고 
//      이 디렉토리에 라이브러리를 자동으로 다운로드 한다.
//    - package.json에 라이브러리 정보가 등록된다.
//      만약 등록되지 않았다면 다음과 같이 --save 옵션을 붙여 실행하라.
//      'npm install bootstrap --save'
//      또는 
//      'npm install --save bootstrap'
//    - package.json 파일에 등록된 라이브러리를 모두 자동으로 다운로드 하고 싶다면,
//      다음과 같이 패키지 이름을 지정하지 않고 실행하라.
//      'npm install' 
// => /webapp/html/board-2/list.html 변경
//    - css, javascript 파일 경로를 npm 폴더 경로로 변경한다.
// => 'npm install sweetalert' 실행
//    - 자바스클립트에서 제공하는 alert() 대신 사용할 라이브러리를 추가한다.
// => /webapp/html/board-2/form.html 변경
//    - css, javascript 파일 경로를 npm 폴더 경로로 변경한다.
//    - alert() 대신 swal() 함수를 사용한다.
//    
// 작업3: HTML과 자바스크립트 코드 분리하기
// => /webapp/html/board-3/list.js 생성
//    - list.html 에서 javascript 코드를 분리한다.
// => /webapp/html/board-3/list.html 변경
//    - list.js를 가져온다.
// => form.html 에서 javascript 코드를 분리하여 form.js를 만든다.
//
// 작업4: 미니 jQuery 만들기
// => /webapp/html/js/bitcamp.js 생성
//    - DOM API와 AJAX를 보다 쉽게 다루는 함수를 만든다.
// => /webapp/html/board-4/list.html, form.html 변경
// 
// 작업5: 미니 jQuery 자바스크립트 파일 압축하기
// => /webapp/html/js/bitcamp.min.js 생성
//    - 자바스클립트 코드의 크기를 줄이기 위해 압축한다.
// => /webapp/html/../list.html, form.html 변경
//    - bitcamp.min.js 를 사용하도록 변경한다.
//
// 작업6: 진짜 jQuery 사용하기
// 
//
// 추가 작업:
// => com.eomcs.lms.config.AppWebApplicationInitializer 변경
//    - CharacterEncodingFilter 등록
// => web.xml 변경
//    - 필터 설정 제거
//
// dummy 클래스!
// => 기존 버전에서 계속 존재했던 클래스라서 그대로 둠.
// => 단지 버전의 목표에 대한 설명을 기록하기 위해 존재함.
// => 프로젝트에서 사용되지 않음!
//
public class App {
}










