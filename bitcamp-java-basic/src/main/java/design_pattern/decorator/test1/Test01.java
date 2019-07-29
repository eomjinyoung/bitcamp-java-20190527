package design_pattern.decorator.test1;

public class Test01 {

  public static void main(String[] args) {
    Dept d1 = new Dept();
    d1.name = "개발팀";
    
    Dept d2 = new Dept();
    d2.name = "구축팀";
    
    Dept d3 = new Dept();
    d3.name = "연구팀";
    
    Employee e1 = new Employee();
    e1.name = "홍길동(부서장)";
    
    d1.add(e1);
    d1.add(d2);
    d2.add(d3);
    

  }

}
