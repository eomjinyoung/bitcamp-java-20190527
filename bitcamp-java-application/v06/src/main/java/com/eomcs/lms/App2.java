package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class App2 {

  static Scanner keyScan;
  
  public static void main(String[] args) {
    java.io.InputStream keyboard = System.in;
    keyScan = new Scanner(keyboard);

    int[] no = new int[100];
    String[] name = new String[100];
    String[] email = new String[100];
    String[] password = new String[100];
    String[] photo = new String[100];
    String[] tel = new String[100];
    Date[] registeredDate = new Date[100];
    
    int i = 0;
    for ( ; i < no.length; i++) {
      no[i] = getIntValue("번호? ");
      name[i] = getStringValue("이름? ");
      email[i] = getStringValue("이메일? ");
      password[i] = getStringValue("암호? ");
      photo[i] = getStringValue("사진? ");
      tel[i] = getStringValue("전화? ");
      registeredDate[i] = new Date(System.currentTimeMillis()); 
        
      System.out.print("계속 입력하시겠습니까?(Y/n) ");
      String response = keyScan.nextLine();
      
      if (response.equals("n"))
        break;
    }
    
    System.out.println(); // 빈 줄 출력
    
    for (int i2 = 0; i2 <= i; i2++) {
      System.out.printf("%s, %s, %s, %s, %s\n", 
          no[i2], name[i2], email[i2], tel[i2], registeredDate[i2]);
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
