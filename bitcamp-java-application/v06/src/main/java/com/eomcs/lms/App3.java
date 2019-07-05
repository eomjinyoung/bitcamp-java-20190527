package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class App3 {

  static Scanner keyScan;
  
  public static void main(String[] args) {
    java.io.InputStream keyboard = System.in;
    keyScan = new Scanner(keyboard);

    int[] no = new int[100];
    String[] contents = new String[100];
    Date[] createdDate = new Date[100];
    int[] viewCount = new int[100];
    
    int i = 0;
    for ( ; i < no.length; i++) {
      no[i] = getIntValue("번호? ");
      contents[i] = getStringValue("내용? ");
      createdDate[i] = new Date(System.currentTimeMillis()); 
      
      System.out.print("계속 입력하시겠습니까?(Y/n) ");
      String response = keyScan.nextLine();
      
      if (response.equals("n"))
        break;
    }
    
    System.out.println(); // 빈 줄 출력
    
    for (int i2 = 0; i2 <= i; i2++) {
      System.out.printf("%s, %s, %s, %s\n", 
          no[i2], contents[i2], createdDate[i2], viewCount[i2]);
    }
  }
  
  private static int getIntValue(String message) {
    while (true) {
      try {
        System.out.print(message);
        return Integer.parseInt(keyScan.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("숫자를 입력하세요.");
      }
    }
  }
  
  private static String getStringValue(String message) {
    System.out.print(message);
    return keyScan.nextLine();
  }
}
