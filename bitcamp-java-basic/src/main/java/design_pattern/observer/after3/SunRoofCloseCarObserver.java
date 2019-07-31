package design_pattern.observer.after3;

public class SunRoofCloseCarObserver implements CarObserver {

  @Override
  public void carStopped() {
    System.out.println("썬루프를 닫는다.");
  }
}
