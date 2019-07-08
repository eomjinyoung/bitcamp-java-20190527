package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class Input {
  
  static Scanner keyScan;
  
  static int getIntValue(String message) {
    while (true) {
      try {
        System.out.print(message);
        return Integer.parseInt(keyScan.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("숫자를 입력하세요.");
      }
    }
  }
  
  static Date getDateValue(String message) {
    while (true) {
      try {
        System.out.print(message);
        return Date.valueOf(keyScan.nextLine());
      } catch (IllegalArgumentException e) {
        System.out.println("2019-07-05 형식으로 입력하세요.");
      }
    }
  }
  
  static String getStringValue(String message) {
    System.out.print(message);
    return keyScan.nextLine();
  }
}
