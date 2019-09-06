import java.util.ArrayList;

public class StudentSelector {

  public static void main(String[] args) throws Exception {
    
    String[] names = {
        "신우혁", "소한샘", "김지수", "이용재", "권다진", "이원주",
        "김광용", "조희정", "현수룡", "김유림", "심수현", "임성빈",
        "오상헌", "신지하", "이유정", "최태훈", "김유빈", "김영준"
    };
    
    ArrayList<String> list = new ArrayList<>();
    for (String name : names) {
      list.add(name);
    }
    
    int no = -1;
    for (int i = 0; i < 10; i++) {
      no = (int)(Math.random() * names.length);
      System.out.print(".");
      Thread.currentThread().sleep(200);
    }
    System.out.println();
    
    Thread.currentThread().sleep(5000);
    System.out.println(names[no]);

  }

}
