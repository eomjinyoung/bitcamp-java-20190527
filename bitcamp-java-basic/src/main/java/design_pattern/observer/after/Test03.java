package design_pattern.observer.after;

public class Test03 {

  public static void main(String[] args) {
    // 예) 2월 30일 - 자동차 시동을 걸 때 엔진 오일 유무를 검사하는 기능을 추가 
    Car car = new Car();
    
    car.addObserver(new SafeBeltCarObserver());
    
    // 새 옵저버를 추가로 등록한다.
    car.addObserver(new EngineOilCarObserver());

    car.start();
    car.run();
    car.stop();
  }

}








