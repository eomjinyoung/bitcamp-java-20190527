package com.eomcs.lms.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;

// 애플리케이션이 시작되거나 종료될 때 보고를 받는 옵저버이다.
// 옵저버의 역할을 수행하려면 옵저버 호출 규칙에 따라 작성해야 한다.
// 즉 ApplicationContextListener를 구현해야 한다.
//
public class DataLoaderListener implements ApplicationContextListener{
  
  // Command 객체가 사용할 Collection 준비
  ArrayList<Lesson> lessonList = new ArrayList<>();
  LinkedList<Member> memberList = new LinkedList<>();
  ArrayList<Board> boardList = new ArrayList<>();
  
  // 애플리케이션이 시작될 때 수업관리 데이터를 로딩한다.
  @Override
  public void contextInitialized() {
    // 이전에 저장된 애플리케이션 데이터를 로딩한다.
    loadLessonData();
    loadMemberData();
    loadBoardData();
  }
  
  // 애플리케이션이 종료될 때 수업관리 데이터를 저장한다.
  @Override
  public void contextDestroyed() {
    // 애플리케이션의 실행을 종료하기 전에 데이터를 저장한다.
    saveLessonData();
    saveMemberData();
    saveBoardData();
  }
  
  
  @SuppressWarnings("unchecked")
  private void loadLessonData() {
    File file = new File("./lesson.ser");
    
    FileInputStream in = null; 
    ObjectInputStream in2 = null;
    
    try {
      in = new FileInputStream(file);
      
      // 바이트 배열을 읽어 객체로 복원해 주는 객체 준비
      in2 = new ObjectInputStream(in);
      
      lessonList = (ArrayList<Lesson>) in2.readObject();
      
    } catch (FileNotFoundException e) {
      System.out.println("읽을 파일을 찾을 수 없습니다!");
      
    } catch (Exception e) {
      System.out.println("파일을 읽는 중에 오류가 발생했습니다!");
      
    } finally {
      try {in2.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
    }
  }
  
  private void saveLessonData() {
    
    File file = new File("./lesson.ser");
    
    FileOutputStream out = null;
    ObjectOutputStream out2 = null;
    
    try {
      out = new FileOutputStream(file);
      
      // 객체를 통째로 바이트 배열로 변환해주는 출력 스트림 준비하기
      out2 = new ObjectOutputStream(out);
      
      // lessonList를 통째로 출력하기
      out2.writeObject(lessonList);
      
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      e.printStackTrace();
      
    } finally {
      try {out2.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {} 
    }
  }
  
  @SuppressWarnings("unchecked")
  private void loadMemberData() {
    File file = new File("./member.ser");
    
    FileInputStream in = null; 
    ObjectInputStream in2 = null;
    
    try {
      in = new FileInputStream(file);
      in2 = new ObjectInputStream(in);
      
      memberList = (LinkedList<Member>) in2.readObject();
          
    } catch (FileNotFoundException e) {
      System.out.println("읽을 파일을 찾을 수 없습니다!");
      
    } catch (Exception e) {
      System.out.println("파일을 읽는 중에 오류가 발생했습니다!");
      
    } finally {
      try {in2.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
    }
    
  }
  
  private void saveMemberData() {
    File file = new File("./member.ser");
    
    FileOutputStream out = null;
    ObjectOutputStream out2 = null;
    
    try {
      out = new FileOutputStream(file);
      out2 = new ObjectOutputStream(out);
      
      out2.writeObject(memberList);
      
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      e.printStackTrace();
      
    } finally {
      try {out2.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {} 
    }
  }
  
  @SuppressWarnings("unchecked")
  private void loadBoardData() {
    File file = new File("./board.ser");
    
    FileInputStream in = null; 
    ObjectInputStream in2 = null;
    
    try {
      in = new FileInputStream(file);
      in2 = new ObjectInputStream(in);
      
      boardList = (ArrayList<Board>) in2.readObject();
      
    } catch (FileNotFoundException e) {
      System.out.println("읽을 파일을 찾을 수 없습니다!");
      
    } catch (Exception e) {
      System.out.println("파일을 읽는 중에 오류가 발생했습니다!");
      
    } finally {
      try {in2.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
    }
    
  }
  
  private void saveBoardData() {
    
    File file = new File("./board.ser");
    
    FileOutputStream out = null;
    ObjectOutputStream out2 = null;
    
    try {
      out = new FileOutputStream(file);
      out2 = new ObjectOutputStream(out);
      
      out2.writeObject(boardList);
      
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      e.printStackTrace();
      
    } finally {
      try {out2.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {} 
    }
  }
  
}
