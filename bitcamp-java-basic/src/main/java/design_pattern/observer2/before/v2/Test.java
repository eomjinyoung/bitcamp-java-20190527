// v2(임꺽정, 3월27일에 추가함): 텍스트 분석기에 Reader 연결
package design_pattern.observer2.before.v2;

import java.io.FileReader;

public class Test {

  public static void main(String[] args) {
    try (FileReader in = new FileReader("build.gradle")) {
      TextAnalyzer analyzer = new TextAnalyzer(in);
      analyzer.execute();
    } catch (Exception e) {
      System.out.println("실행 중 오류 발생!");
    }
  }

}
