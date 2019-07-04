// 애플리케이션 메인 클래스
// => 애플리케이션을 실행할 때 이 클래스를 실행한다.
package com.eomcs.lms;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    java.io.InputStream keyboard = System.in;
    Scanner keyScan = new Scanner(keyboard);
    
    System.out.print("번호? ");
    String no = keyScan.nextLine();
    
    System.out.print("수업명? ");
    String lectureName = keyScan.nextLine();
    
    System.out.print("설명? ");
    String description = keyScan.nextLine();
    
    System.out.print("시작일? ");
    String startDate = keyScan.nextLine();
    
    System.out.print("종료일? ");
    String endDate = keyScan.nextLine();
    
    System.out.print("총수업시간? ");
    String totalHours = keyScan.nextLine();
    
    System.out.print("일수업시간? ");
    String dayHours = keyScan.nextLine();
    
    System.out.println();
    
    System.out.println("번호: " + no);
    System.out.println("수업명: " + lectureName);
    System.out.println("설명: " + description);
    System.out.println("시작일: " + startDate);
    System.out.println("종료일: " + endDate);
    System.out.println("총수업시간: " + totalHours);
    System.out.println("일수업시간: " + dayHours);
  }
}
