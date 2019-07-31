package design_pattern.observer.after;

public class Test06 {

  public static void main(String[] args) {
    Car car = new Car();
    
    car.addObserver(new SafeBeltCarObserver());
    car.addObserver(new EngineOilCarObserver());
    car.addObserver(new BreakOilCarObserver());
    car.addObserver(new LightOffCarObserver());
    
    // 예) 5월 5일 - 자동차 시동을 끌 때 썬루프 자동 닫기 기능을 추가
    car.addObserver(new SunRoofCloseCarObserver());
    
    car.start();
    car.run();
    car.stop();
  }

}








