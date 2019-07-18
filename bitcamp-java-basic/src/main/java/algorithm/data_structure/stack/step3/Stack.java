// 상속 문법을 이용하여 스택 만들기
package algorithm.data_structure.stack.step3;

public class Stack<E> extends LinkedList<E> {
   
  public void push(E value) {
    add(value);
  }
  
  public E pop() {
    return remove(size() - 1);
  }
  
  public boolean empty() {
    return size() == 0;
  }
}
