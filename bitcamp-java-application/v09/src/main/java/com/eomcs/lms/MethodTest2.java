package com.eomcs.lms;

public class MethodTest2 {
  void m1() {
    // 아규먼트(argument)?
    // => 메서드를 호출할 때 파라미터에 넘겨주는 값
    m2("ok", 20);
  }
  
  // 파라미터(parameter)?
  // => 메서드를 호출할 때 넘겨주는 값을 받는 로컬 변수
  void m2(String s, int a) {
    int b;
  }
  
  // 리턴 타입?
  // => 메서드가 리턴하는 값의 종류
  int m3() {
    return 10;
  }
  
  String m4() {
    return "okok";
  }
  
  int plus(int a, int b) {
    return a + b;
  }
}










