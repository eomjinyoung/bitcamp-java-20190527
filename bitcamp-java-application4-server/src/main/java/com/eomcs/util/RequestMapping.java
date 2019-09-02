package com.eomcs.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)// 클래스를 로딩할 때 이 애노테이션의 정보로 로딩되어야 한다.
@Target(ElementType.METHOD)// 메서드에 붙이는 애노테이션으로 설정한다.
public @interface RequestMapping {
  String value(); // 명령어를 설정하는 프로퍼티
}
