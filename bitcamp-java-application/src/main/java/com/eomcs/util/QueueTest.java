package com.eomcs.util;

public class QueueTest {
  public static void main(String[] args) {
    Queue<String> queue = new Queue<>();
    queue.offer("aaa");
    queue.offer("bbb"); 
    queue.offer("ccc");
    queue.offer("ddd");
     
    while (!queue.empty()) {
      System.out.println(queue.poll());
    }
  }
}
