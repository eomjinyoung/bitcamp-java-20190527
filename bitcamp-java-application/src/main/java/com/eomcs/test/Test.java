package com.eomcs.test;

public class Test {

  public static void main(String[] args) throws Exception {
    String str = "aaa,bbb,ccc".replaceAll(",", "&#44;");
    System.out.println(str);
    
    System.out.println(str.replace("&#44;", ","));
  }

}
