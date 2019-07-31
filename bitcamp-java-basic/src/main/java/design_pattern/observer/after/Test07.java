package design_pattern.observer.after;

public class Test07 {

  public static void main(String[] args) {
    
    CarObserver o1 = new SafeBeltCarObserver();
    CarObserver o2 = new EngineOilCarObserver();
    CarObserver o3 = new BreakOilCarObserver();
    CarObserver o4 = new LightOffCarObserver();
    CarObserver o5 = new SunRoofCloseCarObserver();
    
    Car car = new Car();
    
    car.addObserver(o1);
    car.addObserver(o2);
    car.addObserver(o3);
    car.addObserver(o4);
    car.addObserver(o5);
    
    car.start();
    car.run();
    car.stop();
    
    System.out.println("---------------------");
    
    // Observer 패턴은 리스너를 쉽게 추가하고 제거할 수 있다.
    // 언제든 특정 상태에 대해 관심이 없다면 제거하면 된다.
    // => 실행 중에도 제거할 수 있다.
    System.out.println("[사용하지 않을 옵저버를 제거 한 후]");
    car.removeObserver(o1);
    car.removeObserver(o3);
    car.removeObserver(o5);
    
    car.start();
    car.run();
    car.stop();
  }

}








