package com.eomcs.test.ex2;

public class Test01 {

  public static void main(String[] args) {
    Calculator c1 = new Calculator();
    Calculator c2 = new Calculator();

    c1.plus(100);
    c1.minus(30);
    
    c2.plus(200);
    c2.minus(30);
    
    System.out.println(c1.result);
    System.out.println(c2.result);

  }

}
