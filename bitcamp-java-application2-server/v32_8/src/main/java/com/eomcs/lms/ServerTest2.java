package com.eomcs.lms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import com.eomcs.lms.domain.Lesson;

public class ServerTest2 {

  static ObjectOutputStream out;
  static ObjectInputStream in;
  
  public static void main(String[] args) throws Exception {
    System.out.println("[수업관리시스템 서버 애플리케이션 테스트]");

    try (Socket socket = new Socket("localhost", 8888);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
      
      System.out.println("서버와 연결되었음.");
      
      // 다른 메서드가 입출력 객체를 사용할 수 있도록 스태틱 변수에 저장한다.
      ServerTest2.in = in;
      ServerTest2.out = out;
      
      
      Lesson lesson = new Lesson();
      lesson.setNo(1);
      lesson.setTitle("자바프로그래밍");
      lesson.setContents("okok");
      lesson.setStartDate(Date.valueOf("2019-1-1"));
      lesson.setEndDate(Date.valueOf("2019-2-1"));
      lesson.setTotalHours(200);
      lesson.setDayHours(4);
      
      if (!add(lesson)) {
        error();
      }
      System.out.println("------------------");
      
      lesson = new Lesson();
      lesson.setNo(2);
      lesson.setTitle("자바프로그래밍2");
      lesson.setContents("okok2");
      lesson.setStartDate(Date.valueOf("2019-2-2"));
      lesson.setEndDate(Date.valueOf("2019-3-3"));
      lesson.setTotalHours(400);
      lesson.setDayHours(3);

      if (!add(lesson)) {
        error();
      }
      System.out.println("------------------");
      
      if (!list()) {
        error();
      }
      System.out.println("------------------");
      
      if (!delete()) {
        error();
      }
      System.out.println("------------------");
      
      if (!list()) {
        error();
      }
      System.out.println("------------------");
      
      if (!detail()) {
        error();
      }
      System.out.println("------------------");
      
      lesson = new Lesson();
      lesson.setNo(1);
      lesson.setTitle("자바 웹 프로그래밍");
      lesson.setContents("웹개발자 양성과정");
      lesson.setStartDate(Date.valueOf("2019-5-27"));
      lesson.setEndDate(Date.valueOf("2019-11-27"));
      lesson.setTotalHours(400);
      lesson.setDayHours(3);
      
      if (!update(lesson)) {
        error();
      }
      System.out.println("------------------");
      
      if (!list()) {
        error();
      }
      System.out.println("------------------");
      
      if (!quit()) {
        error();
      }
    
    } catch (RequestException e) {
      System.out.printf("오류: %s\n", in.readUTF());
      
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("서버와 연결 끊음.");
  }
  
  private static void error() throws Exception {
    System.out.printf("오류: %s\n", in.readUTF());
  }

  private static boolean quit() throws Exception {
    out.writeUTF("quit");
    out.flush();
    System.out.print("quit 요청함 => ");
    
    if (!in.readUTF().equals("ok"))
      return false;
    
    System.out.println("처리 완료!");
    return true;
  }

  private static boolean delete() throws Exception {
    out.writeUTF("/lesson/delete");
    out.writeInt(2);
    out.flush();
    System.out.print("delete 요청함 => ");
    
    if (!in.readUTF().equals("ok"))
      return false;
    
    System.out.println("처리 완료!");
    return true;
  }
  
  private static boolean detail() throws Exception {
    out.writeUTF("/lesson/detail");
    out.writeInt(1);
    out.flush();
    System.out.print("detail 요청함 => ");
    
    if (!in.readUTF().equals("ok"))
      return false;
    
    System.out.println("처리 완료!");
    System.out.println(in.readObject());
    
    return true;
  }
  
  private static boolean update(Lesson obj) throws Exception {
    out.writeUTF("/lesson/update");
    out.writeObject(obj);
    out.flush();
    System.out.print("update 요청함 => ");
    
    if (!in.readUTF().equals("ok"))
      return false;
    
    System.out.println("처리 완료!");
    return true;
  }

  private static boolean list() throws Exception {
    out.writeUTF("/lesson/list");
    out.flush();
    System.out.print("list 요청함 => ");
    
    if (!in.readUTF().equals("ok"))
      return false;
    
    System.out.println("처리 완료!");
    
    @SuppressWarnings("unchecked")
    List<Lesson> list = (List<Lesson>)in.readObject();
    System.out.println("------------------");
    for (Lesson obj : list) {
      System.out.println(obj);
    }
    list.clear();
    return true;
  }

  private static boolean add(Lesson obj) throws IOException, RequestException {
    out.writeUTF("/lesson/add");
    out.writeObject(obj);
    out.flush();
    System.out.print("add 요청함 => ");
    
    if (!in.readUTF().equals("ok"))
      return false;
    
    System.out.println("처리 완료!");
    return true;
  }
}






