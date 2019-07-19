package com.eomcs.util;

// 목록을 다루는 객체가 반드시 갖춰야할 기능을 규칙으로 정의한다.
// 사용하는 측에는 이 규칙에 따라 일관된 방식으로 클래스를 사용할 수 있다.
public interface List<E> {
  
  // 기능을 정의할 때는 메서드 시그너처만 선언한다.
  // 기능의 구현은 클래스에서 할 것이다.
  // => 규칙은 무조건 공개되어야 한다. 따라서 public이다.
  // => 규칙에 정의된 메서드는 클래스에서 구현해야 한다. 그래서 abstract이다.
  // => public, abstract modifier는 생략할 수 있다. 
  
  /*public abstract*/ boolean add(E value);
  /*public abstract*/ E get(int index);
  /*public abstract*/ E set(int index, E value);
  /*public abstract*/ E remove(int index);
  /*public abstract*/ Object[] toArray();
  /*public abstract*/ E[] toArray(E[] a);
  /*public abstract*/ int size();
  /*public abstract*/ void clear();
}






