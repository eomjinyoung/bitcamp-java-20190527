package com.eomcs.util;

import java.util.Arrays;

// ArrayList 클래스를 List 규칙에 따라 작성한다.
// => 클래스를 선언할 때 어떤 규칙을 따를 것인지 지정해야 한다.
//       class 클래스명 implements 규칙1, 규칙2, 규칙3 {...}
//
public class ArrayList<E> implements List<E> {
  private static final int DEFAULT_CAPACITY = 100;
  
  private Object[] list;
  private int size = 0;
  
  public ArrayList() {
    this(DEFAULT_CAPACITY);
  }
  
  public ArrayList(int initialCapacity) {
    if (initialCapacity < 50 || initialCapacity > 10000)
      list = new Object[DEFAULT_CAPACITY];
    else
      list = new Object[initialCapacity];
  }
  
  // 인터페이스(규칙)에 정의된 메서드를 구현할 때는 오버라이딩 하는 방법과 같다.
  // => public을 더 제한할 수 없다.
  // => @Override 애노테이션을 붙일 수 있다.
  @Override
  public boolean add(E obj) {
    if (this.size == list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(this.list, newCapacity);
    }
    this.list[this.size++] = obj;
    return true;
  }
  
  @Override
  public Object[] toArray() {
    return Arrays.copyOf(this.list, this.size); // new Object[this.size];
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E[] toArray(E[] a) {
    if (a.length < size) {
      // 파라미터로 넘겨 받은 배열의 크기가 저장된 데이터의 개수 보다 작다면 
      // 이 메서드에서 새 배열을 만든다.
      return (E[]) Arrays.copyOf(list, size, a.getClass()); // 세 번째 파라미터로 지정한 타입의 배열이 생성된다.
    }
    System.arraycopy(list, 0, a, 0, size);
    if (a.length > size)
      a[size] = null;
    return a;
  }
  
  @Override
  public int size() {
    return this.size;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E get(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException(String.format("인덱스 = %s", index));
    
    return (E) list[index];
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E set(int index, E obj) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException(String.format("인덱스 = %s", index));
    
    E old = (E) list[index];
    list[index] = obj;
    
    return old;
  }
  
  @Override
  public E remove(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException(String.format("인덱스 = %s", index));
    
    @SuppressWarnings("unchecked")
    E old = (E) list[index];
    
    // 방법1: 직접 반복문을 이용하여 삭제 처리하기
    /*
    for (int i = index + 1; i < size; i++) {
      list[i-1] = list[i];
    }
    */
    
    // 방법2: 배열 복사 기능을 이용하여 삭제 처리하기 
    System.arraycopy(list, index + 1, list, index, size - (index + 1));

    // 값을 삭제한 후 맨 끝 값이 들어 있던 방을 null로 설정한다.
    // => 레펀런스가 남아있지 않게 하여 가비지가 정상적으로 이뤄지도록 한다.
    list[--size] = null;
    
    return old;
  }
  
  @Override
  public void clear() {
    for (int i = 0; i < size; i++) 
      list[i] = null;
    size = 0;
  }
  
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    list.add("000");
    list.add("111");
    list.add("222");
    list.add("333");
    list.add("444");
    list.add("555");
    
    String old = list.set(2, "okok"); // list.remove(2);
    System.out.println("원래값 : " + old);
    System.out.println("-----------------------");
    
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }
  
  
  
}









