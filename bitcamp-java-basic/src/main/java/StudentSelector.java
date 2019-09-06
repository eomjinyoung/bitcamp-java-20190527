import java.util.ArrayList;
import java.util.Scanner;

public class StudentSelector {

  public static void main(String[] args) throws Exception {
    /*
    String[] names = {
        "소한샘", "이용재", "이원주",
        "김유림", "임성빈", "김유빈"
    };
    */
    
    String[] names = {
        "신우혁", "김지수", "권다진", 
        "김광용", "조희정", "현수룡", "심수현",
        "오상헌", "신지하", "이유정", "최태훈", "김영준"
    };
    
    
    ArrayList<String> list = new ArrayList<>();
    for (String name : names) {
      list.add(name);
    }
    
    Scanner keyboard = new Scanner(System.in);
    
    while (list.size() > 0) {
      int no = -1;
      for (int i = 0; i < 30; i++) {
        no = (int)(Math.random() * list.size());
        System.out.print(".");
        Thread.currentThread().sleep(200);
      }
      
      Thread.currentThread().sleep(1000);
      System.out.println(list.remove(no));
      keyboard.nextLine();
    }
    
    keyboard.close();
  }

}
