package com.eomcs.lms;

import java.sql.Date;

public class LessonTest2 {
  public static void main(String[] args) {
    
    // 레퍼런스 배열을 가리키는 레퍼런스 만들기
    Lesson[] arr; // arr [ 배열주소 ]
    
    // Lesson 레퍼런스 배열 만들기
    // => Lesson 인스턴스의 주소를 저장할 레퍼런스를 3개 만든다.
    // => Lesson 인스턴스를 3개 만드는 것이 아니다!!!!!!!!
    arr = new Lesson[3];  // [배열주소] ====> [0번 레퍼런스][1번 레퍼런스][2번 레퍼런스]
    
    // arr가 가리키는 배열에서 0번 레퍼런스 변수에 Lesson 인스턴스 주소를 저장하지 않고 사용하면?
    // => arr가 가리키는 배열에서 0번 변수에는 아무런 주소가 들어 있지 않다.
    // => 레퍼런스 배열의 각 변수에 인스턴스 주소를 넣지 않고 사용하면 NullPointerException 발생!
    //arr[0].no = 100;
    
    arr[0] = new Lesson();
    arr[1] = new Lesson();
    arr[2] = new Lesson();
    
    arr[0].no = 100;
    arr[0].title = "자바";
    
    arr[1].no = 200;
    arr[1].title = "웹";
    
    int[] arr2 = new int[3];
    arr2[0] = 100;
    arr2[1] = 200;
    arr2[2] = 300;
    
  }
}





