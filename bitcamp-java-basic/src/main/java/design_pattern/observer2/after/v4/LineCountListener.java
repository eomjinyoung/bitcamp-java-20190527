package design_pattern.observer2.after.v4;

// TextAnalyzer로부터 보고를 받아서 
// 텍스트의 줄 수를 세는 관찰자이다.
public class LineCountListener implements CharacterListener {
  int lines = 0;
  boolean isEmpty = true;
  
  @Override
  public void readed(int ch) {
    if (ch == '\n') {
      lines++;
      isEmpty = true;
      
    } else if (ch == -1) {
      if (!isEmpty)
        lines++;
      
    } else {
      isEmpty = false;
    }
  }

  @Override
  public void displayResult() {
    System.out.printf("총 줄 수: %d\n", lines);
    
  }

}
