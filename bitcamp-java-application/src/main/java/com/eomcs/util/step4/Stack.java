// 상속 문법을 이용하여 스택 만들기
package com.eomcs.util.step4;

import com.eomcs.util.Iterator;
import com.eomcs.util.LinkedList;

public class Stack<E> extends LinkedList<E> implements Cloneable {
  
  @Override
  public Stack<E> clone() throws CloneNotSupportedException {
    // 현재 스택 객체의 노드를 그대로 유지하기 위해 "deep copy"를 실행한다.
    // => 새 스택을 만들고, 
    Stack<E> temp = new Stack<>();
    for (int i = 0; i < size(); i++) {
      // => 현재 스택에서 값을 꺼내 새 스택의 새 노드에 넣는다.
      //    즉 새 스택은 값을 넣을 때 마다 새 노드를 생성하기 때문에 
      //    현재 스택의 노드에는 영향을 끼치지 않는다.
      temp.push(get(i));
    }
    return temp;
  }
  
  public void push(E value) {
    add(value);
  }
  
  public E pop() {
    return remove(size() - 1);
  }
  
  public boolean empty() {
    return size() == 0;
  }
  
  // 스택에서 Iterator를 제공한다.
  public Iterator<E> getIterator() {
    
    // 중첩 클래스를 정의한 후 인스턴스를 딱 한 개 생성하는 용도로 사용한다면 
    // 굳이 클래스 이름을 가질 필요가 없다. 
    // 클래스를 정의하자마자 바로 인스턴스를 만들어 사용하면 편하다.
    // 이렇게 정의하는 중첩 클래스를 "anonymous class"라 부른다.
    //
    return new Iterator<E>() {
      @Override
      public boolean hasNext() {
        return size() > 0;
      }
      @Override
      public E next() {
        return pop();
      }
    };
  }
  
}







