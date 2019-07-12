package com.eomcs.lms.handler;

import com.eomcs.lms.domain.Lesson;

public class LessonList {
  private Lesson[] list = new Lesson[100];
  private int size = 0;
  
  public void add(Lesson lesson) {
    this.list[this.size++] = lesson;
  }
  
  public Lesson[] toArray() {
    Lesson[] arr = new Lesson[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.list[i];
    }
    return arr;
  }
}
