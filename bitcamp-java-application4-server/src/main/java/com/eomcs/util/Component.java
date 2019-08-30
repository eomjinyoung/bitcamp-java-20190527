package com.eomcs.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 빈 컨테이너가 관리할 대상 클래스를 표시할 때 사용할 주석
// => 이 주석이 붙은 클래스는 빈 컨테이너가 자동으로 객체를 생성해서 보관해 둔다.
//
// 애노테이션(annotation)
// => 컴파일러나 JVM에게 전달하는 특별한 주석
// => 일반 주석과 달리 속성=값 형태로 데이터를 조직화시킬 수 있다.
//

@Retention(RetentionPolicy.RUNTIME) // JVM에서 추출할 수 있는 주석으로 설정한다.
public @interface Component {
  // 클래스에 이 주석을 붙일 때 추가적으로 설정하는 데이터
  String value() default ""; // value 속성의 값을 지정하지 않으면 기본이 빈 문자열이다.
}









