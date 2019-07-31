package design_pattern.observer2.after.v3;

import java.io.Reader;
import java.util.ArrayList;

public class TextAnalyzer {
  Reader in;
  
  // 보고를 받을 객체의 주소를 저장
  ArrayList<CharacterListener> listeners = new ArrayList<>();
  
  public TextAnalyzer(Reader in) {
    this.in = in;
  }
  
  // 보고를 받을 객체(listener)를 추가하는 메서드
  public void addCharacterListener(CharacterListener listener) {
    listeners.add(listener);
  }
  
  public void execute() {
    try { 
      int ch;
      while (true) {
        // -1도 보고를 해야 하기 때문에 다음과 같이 변경한다.
        ch = in.read();
        
        // 문자를 읽을 때 마다, 이 객체에 등록된 모든 관찰자에게 보고한다.
        for (CharacterListener listener : listeners) {
          listener.readed(ch); // 보고를 한다는 것은 메서드를 호출하는 것이다.
        }
        
        if (ch == -1)
          break;
      }
      
    } catch (Exception e) {
      System.out.println("분석 중 오류 발생!");
      
    } finally {
      //주의!
      //=> 자원 해제는 그 자원을 관리하는 객체가 책임져야 한다.
      //=> TextAnalyzer는 단지 Reader 자원을 생성자에서 받아서 
      //   execute()에서 사용할 뿐이다.
      //=> 따라서 다음과 같이 사용이 끝났다고 해서
      //   여기서 자원을 해제해서는 안된다.
      //=> 이 객체에 자원을 넘겨준 놈이 자원 해제의 책임을 져야 한다.
      //try {in.close();} catch (Exception e) {}
    }
    
  }
  
}
