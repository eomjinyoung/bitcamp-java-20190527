package com.eomcs.util;

public class ArrayListTest {
  public static void main(String[] args) {
    //test1();
    //test2();
    //test3();
    test4();
  }
  
  static void test1() {
    ArrayList<String> list = new ArrayList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    
    Object[] arr1 = list.toArray();
    for (Object obj : arr1) {
      System.out.println((String) obj);
    }
  }
  
  static void test2() {
    ArrayList<String> list = new ArrayList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    
    String[] arr1 = new String[list.size()];
    String[] arr2 = list.toArray(arr1);
    System.out.println(arr1 == arr2);
    
    for (String str : arr1) {
      System.out.println(str);
    }
  }
  
  static void test3() {
    ArrayList<String> list = new ArrayList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    
    String[] arr1 = new String[2];
    String[] arr2 = list.toArray(arr1);
    System.out.println(arr1 == arr2);
    
    for (String str : arr2) {
      System.out.println(str);
    }
  }
  
  static void test4() {
    ArrayList<String> list = new ArrayList<>();
    list.add("aaa");
    list.add("ccc");
    list.add("ddd");
    
    String[] arr1 = new String[] {"x","x","x","x","x","x","x","x","x","x"};
    String[] arr2 = list.toArray(arr1);
    System.out.println(arr1 == arr2);
    
    for (String str : arr1) {
      if (str == null)
        break;
      System.out.println(str);
    }
  }
}





