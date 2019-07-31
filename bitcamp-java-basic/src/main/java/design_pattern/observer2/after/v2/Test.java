// v2(임꺽정, 3월4일에 추가함): 총 줄 수를 세기
package design_pattern.observer2.after.v2;

import java.io.FileReader;

public class Test {

  public static void main(String[] args) {
    try (FileReader in = new FileReader("build.gradle")) {
      TextAnalyzer analyzer = new TextAnalyzer(in);
      
      // 분석기로부터 보고를 받을 리스너(=observer)를 추가한다.
      CharacterCountListener l1 = new CharacterCountListener();
      analyzer.addCharacterListener(l1);
      
      LineCountListener l2 = new LineCountListener();
      analyzer.addCharacterListener(l2);
      
      // 분석기를 실행한다.
      // => 분석기는 문자를 한 개 읽을 때 마다 분석기에 등록된 모든 리스너에게 보고할 것이다.
      // => 각각의 리스너는 보고를 받으면 자신의 일을 한다.
      analyzer.execute();
      
      // 분석 작업이 끝났으면 각 리스너에게 결과를 출력하라고 명령한다.
      l1.displayResult();
      l2.displayResult();
      
    } catch (Exception e) {
      System.out.println("실행 중 오류 발생!");
    }
  }

}
