package design_pattern.observer.after3;

public class SafeBeltCarObserver implements CarObserver {

  @Override
  public void carStarted() {
    System.out.println("안전벨트 착용 여부 검사");
    
  }
}
