package com.eomcs.lms;

public class StaticTest {

  public static void main(String[] args) {

    // 스태틱 필드 사용하기
    // => 클래스 이름으로 사용하면 된다.
    System.out.println(Car.count);

    // 인스턴스 필드 사용하기
    Car c1;
    c1 = new Car();
    c1.no = 1;
    c1.model = "티코";
    c1.count++; // 스태틱 필드는 보통 클래스 이름으로 사용한다.
                // 그러나 클래스 이름 대신 레퍼런스 통해 스태틱 필드를 사용할 수 있다. 비추!
                // 개발자가 인스턴스 필드라고 착각할 수 있다. 따라서 이런 식으로 사용하지 말라!
    Car c2 = new Car();
    c2.no = 2;
    c2.model = "소나타";
    c2.count++; 
    
    System.out.printf("%d, %s\n", c1.no, c1.model);
    System.out.printf("%d, %s\n", c2.no, c2.model);
    System.out.println(Car.count);
    
  }

}
