package com.eomcs.lms;

public class Car {
  // static/class field
  // => 클래스가 로딩될 때 Method Area에 자동 생성된다.
  // => 클래스는 딱 한 번만 로딩되기 때문에(중복으로 로딩되지 않는다)
  //    스태틱 필드도 딱 한 번만 생성된다.
  static int count;
  
  // instance field
  // => new 명령을 실행할 때 Heap에 생성된다.
  // => new 명령을 실행하는 개수 만큼 인스턴스 필드가 생성된다.
  int no;
  String model;
}
