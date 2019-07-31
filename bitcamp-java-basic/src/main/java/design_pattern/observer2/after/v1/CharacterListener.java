package design_pattern.observer2.after.v1;

// 옵저버에게 한 문자를 읽을 때 마다 알려주기 위해 메서드를 호출할 때
// 그 호출할 메서드의 규칙을 정의한다.
public interface CharacterListener {
  void readed(int ch);
  void displayResult();
}
