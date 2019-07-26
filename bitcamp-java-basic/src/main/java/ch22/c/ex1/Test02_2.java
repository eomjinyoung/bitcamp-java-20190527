// 버퍼 사용(for 텍스트 파일) - 사용 후
// 
package ch22.c.ex1;

import java.io.FileReader;

public class Test02_2 {
  public static void main(String[] args) {
    
    try {
      FileReader in = new FileReader("temp/jls12.txt");
      
      System.out.println("데이터 읽는 중...");
      
      long start = System.currentTimeMillis();
      
      char[] buf = new char[8192];
      int len = 0;
      int count = 0;
      while ((len = in.read(buf)) != -1) {
        count++;
      }
      
      long end = System.currentTimeMillis();
      System.out.println(end - start);
      System.out.println(count);
      
      in.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println("출력 완료!");
  }
}








