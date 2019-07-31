package design_pattern.observer.before.v5;

public class Test01 {

  public static void main(String[] args) {
    Car car = new Car();
    
    car.start();
    
    car.run();
    
    car.stop();
    
    // Car 클래스를 생성한 후 
    // 1) 자동차의 시동을 걸 때 안전벨트 착용 여부를 검사하는 기능을 추가한다.
    // 프로젝트 완료한 다음 시간이 지난 후 
    // 
    // 2) 자동차 시동 걸 때 엔진 오일 검사 기능을 추가한다.
    // 업그레이드를 수행한 다음 시간이 지난 후
    // 
    // 3) 자동차 시동 걸 때 브레이크 오일 검사 기능을 추가한다.
    // 업그레이드를 수행한 다음 시간이 지난 후 
    // 
    // 4) 시동 끌 때 자동차 전조등을 자동으로 끄는 기능을 추가한다.
    // 업그레이드를 수행한 다음 시간이 지난 후 
    //
    
  }

}








