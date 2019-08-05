package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.eomcs.lms.Servlet;
import com.eomcs.lms.dao.serial.LessonSerialDao;
import com.eomcs.lms.domain.Lesson;

public class LessonServlet implements Servlet {

  LessonSerialDao lessonDao;
  
  ObjectInputStream in;
  ObjectOutputStream out;
  
  public LessonServlet(ObjectInputStream in, ObjectOutputStream out) throws ClassNotFoundException {
    this.in = in;
    this.out = out;
    
    lessonDao = new LessonSerialDao("./lesson.ser");
  }
  
  public void saveData() {
    lessonDao.saveData();
  }
  
  @Override
  public void service(String command) throws Exception {
    switch (command) {
      case "/lesson/add":
        addLesson();
        break;
      case "/lesson/list":
        listLesson();
        break;
      case "/lesson/delete":
        deleteLesson();
        break;  
      case "/lesson/detail":
        detailLesson();
        break;
      case "/lesson/update":
        updateLesson();
        break;
      default:
        out.writeUTF("fail");
        out.writeUTF("지원하지 않는 명령입니다.");
    }
  }

  private void updateLesson() throws Exception {
    Lesson lesson = (Lesson)in.readObject();
    
    if (lessonDao.update(lesson) == 0) {
      fail("해당 번호의 수업이 없습니다.");
      return;
    }
    
    out.writeUTF("ok");
  }

  private void detailLesson() throws Exception {
    int no = in.readInt();
    
    Lesson lesson = lessonDao.findBy(no);
    if (lesson == null) {
      fail("해당 번호의 수업이 없습니다.");
      return;
    }
    out.writeUTF("ok");
    out.writeObject(lesson);
  }

  private void deleteLesson() throws Exception {
    int no = in.readInt();
    
    if (lessonDao.delete(no) == 0) {
      fail("해당 번호의 수업이 없습니다.");
      return;
    }
    
    out.writeUTF("ok");
  }

  private void addLesson() throws Exception {
    Lesson lesson = (Lesson)in.readObject();
    
    if (lessonDao.insert(lesson) == 0) {
      fail("수업을 입력할 수 없습니다.");
      return;
    }
    
    out.writeUTF("ok");
  }
  
  private void listLesson() throws Exception {
    out.writeUTF("ok");
    out.reset(); // 기존에 serialize 했던 객체의 상태를 무시하고 다시 serialize 한다.
    out.writeObject(lessonDao.findAll());
  }
  
  private void fail(String cause) throws Exception {
    out.writeUTF("fail");
    out.writeUTF(cause);
  }
}
