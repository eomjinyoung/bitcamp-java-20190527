package bitcamp.kia;

import bitcamp.Car;

public class K3 implements Car {
  @Override
  public void run() {
    System.out.println("K3.run().... 호출됨!");
  }
}
