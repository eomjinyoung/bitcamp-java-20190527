package design_pattern.observer2.after.v4;

// 텍스트 분석기에서 보고를 받을 클래스를 정의한다.
// 텍스트 분석기는 보고할 때 CharacterListener에 선언된 대로 메서드를 호출하기 때문에 
// CharacterListener 인터페이스 규칙에 따라 클래스를 작성해야 한다.
//
public class MainClassNameListener implements CharacterListener {

  StringBuffer line = new StringBuffer();
  String mainClassName = null;
  
  @Override
  public void readed(int ch) {
    // mainClassName의 값 알아내기
    if (ch == '\n' || ch == -1) {
      if (line.indexOf("mainClassName") != -1) {
        int i = line.indexOf("\"");
        if (i != -1) {
          mainClassName = line.substring(i + 1, line.indexOf("\"", i + 1));
        }
        i = line.indexOf("'");
        if (i != -1) {
          mainClassName = line.substring(i + 1, line.indexOf("'", i + 1));
        }
      }
      line.setLength(0);
    } else {
      line.append((char)ch);
    }
    
  }

  @Override
  public void displayResult() {
    System.out.printf("mainClassName: %s\n", mainClassName);
    
  }

}
