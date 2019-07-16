package com.eomcs.util;

import java.util.Arrays;

public class ArrayList<E> {
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
  
  public void add(E obj) {
    if (this.size == list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(this.list, newCapacity);
    }
    this.list[this.size++] = obj;
  }
  
  public Object[] toArray() {
    return Arrays.copyOf(this.list, this.size); // new Object[this.size];
  }
  
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
  
  public int size() {
    return this.size;
  }
  
  @SuppressWarnings("unchecked")
  public E get(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException(String.format("인덱스 = %s", index));
    
    return (E) list[index];
  }
  
  @SuppressWarnings("unchecked")
  public E set(int index, E obj) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException(String.format("인덱스 = %s", index));
    
    E old = (E) list[index];
    list[index] = obj;
    
    return old;
  }
  
  public E remove(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException(String.format("인덱스 = %s", index));
    
    @SuppressWarnings("unchecked")
    E old = (E) list[index];
    
    
    
    return old;
  }
  
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    list.add("홍길동");
    list.add("임꺽정");
    list.add("심청이");
    
    String old = list.set(1, "안중근");
    System.out.println("원래값 : " + old);
    
    
    System.out.println(list.get(0));
    System.out.println(list.get(1));
    System.out.println(list.get(2));
  }
  
}









