package com.eomcs.lms.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Member;

public class MemberSerialDao {
  
  ArrayList<Member> list = new ArrayList<>();
  File file;
  
  public MemberSerialDao(String file) throws ClassNotFoundException {
    this.file = new File(file);
    
    try {
      loadData();
    } catch (IOException e) {
      System.out.println("회원 데이터 로딩 중 오류 발생!");
    }
  }
  
  @SuppressWarnings("unchecked")
  private void loadData() throws IOException, ClassNotFoundException {
    try (ObjectInputStream in = new ObjectInputStream(
          new FileInputStream(file))) {
      list = (ArrayList<Member>) in.readObject();
      System.out.println("회원 데이터 로딩 완료!");
    }
  }
  
  public void saveData() {
    try (
      ObjectOutputStream out = new ObjectOutputStream(
          new FileOutputStream(file))) {
      
      out.writeObject(list);
      System.out.println("회원 데이터 저장 완료!");
      
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }
  
  public int insert(Member member) throws Exception {
    list.add(member);
    return 1;
  }
  
  public List<Member> findAll() throws Exception {
    return list;
  }
  
  public Member findBy(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1)
      return null;
    
    return list.get(index);
  }
  
  public int update(Member member) throws Exception {
    int index = indexOf(member.getNo());
    if (index == -1)
      return 0;
    
    list.set(index, member);
    return 1;
  }
  
  public int delete(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1)
      return 0;
    
    list.remove(index);
    return 1;
  }
  
  private int indexOf(int no) {
    int i = 0;
    for (Member obj : list) {
      if (obj.getNo() == no) {
        return i;
      }
      i++;
    }
    return -1;
  }
}








