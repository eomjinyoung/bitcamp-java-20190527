// 상속 문법을 이용하여 큐 만들기
package com.eomcs.util.step2;

import com.eomcs.util.Iterator;
import com.eomcs.util.LinkedList;

public class Queue<E> extends LinkedList<E> implements Cloneable {

  @Override
  public Queue<E> clone() throws CloneNotSupportedException {
    // 현재 큐 객체의 노드를 그대로 유지하기 위해 "deep copy"를 실행한다.
    // => 새 큐를 만들고, 
    Queue<E> temp = new Queue<>();
    for (int i = 0; i < size(); i++) {
      // => 현재 큐에서 값을 꺼내 새 큐의 새 노드에 넣는다.
      //    즉 새 큐는 값을 넣을 때 마다 새 노드를 생성하기 때문에 
      //    현재 큐의 노드에는 영향을 끼치지 않는다.
      temp.offer(get(i));
    }
    return temp;
  }

  public void offer(E value) {
    add(value);
  }

  public E poll() {
    return remove(0);
  }

  public boolean empty() {
    return size() == 0;
  }

  // 큐의 데이터를 꺼내줄 Iterator를 제공한다.
  public Iterator<E> createIterator() {
    /*
    Queue<E> clonedQueue = this; // 복제된 commandQueue의 주소가 들어 있다.
    QueueIterator<E> iterator = new QueueIterator<>(clonedQueue);
    return iterator;
    */
    return new QueueIterator();
  }

  // => 이 클래스에서 사용할 클래스는 이 클래스 안에 선언하는 것이 소스 코드 관리에 좋다.
  //    이렇게 클래스에 안에 선언된 클래스를 nested class(중첩 클래스) 라 부른다.
  //    중첩 클래스 중에서 static 이 안 붙은 중첩 클래스를 "non-static nested class" 라 부른다.
  //    non-static nested class를 "inner class"라 부른다.
  // => 중첩 클래스의 가장 큰 효용성은 다른 멤버(메서드)들처럼 다른 멤버를 그냥 사용할 수 있다는 것이다. 
  //    
  private class QueueIterator implements Iterator<E> {
    @Override
    public boolean hasNext() {
      // 중첩 클래스 안에서는 자신을 생성한 바깥 클래스의 인스턴스 주소를 
      // "바깥클래스명.this"라는 변수에 저장한다.
      // 그래서 생성자에서 따로 바깥 클래스의 인스턴스 주소를 받을 필요가 없이
      // 바로 바깥 클래스의 인스턴스를 사용할 수 있다. 
      //return Queue.this.size() > 0; // OK
      
      // 만약 사용하려면 필드나 메서드가 중첩 클래스에 있는 필드나 메서드가 아니라면 
      // 바깥 클래스의 인스턴스 주소를 생략할 수 있다.
      return size() > 0;
    }
    @Override
    public E next() {
      //return Queue.this.poll(); // OK
      return poll(); // 바깥 클래스(Queue)의 인스턴스 주소를 생략할 수 있다.
    }
  }


}









