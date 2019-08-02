// v32_6: 회원 데이터를 다루는 CRUD 명령을 처리한다.
package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.eomcs.lms.domain.Member;

public class ServerApp {

  static ArrayList<Member> memberList = new ArrayList<>();
  
  static ObjectInputStream in;
  static ObjectOutputStream out;
  
  public static void main(String[] args) {
    System.out.println("[수업관리시스템 서버 애플리케이션]");


    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 시작!");
 
      try (Socket clientSocket = serverSocket.accept();
          ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
          ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
        
        System.out.println("클라이언트와 연결되었음.");
        
        // 다른 메서드가 사용할 수 있도록 입출력 스트림을 스태틱 변수에 저장한다.
        ServerApp.in = in;
        ServerApp.out = out;
        
        loop:
        while (true) {
          // 클라이언트가 보낸 명령을 읽는다.
          String command = in.readUTF();
          System.out.println(command + " 요청 처리중...");
          
          // 명령어에 따라 처리한다.
          switch (command) {
            case "/member/add":
              addMember();
              break;
            case "/member/list":
              listMember();
              break;
            case "/member/delete":
              deleteMember();
              break;  
            case "/member/detail":
              detailMember();
              break;
            case "/member/update":
              updateMember();
              break;
            case "quit":
              out.writeUTF("ok");
              break loop;
            default:
              out.writeUTF("fail");
              out.writeUTF("지원하지 않는 명령입니다.");
          }
          out.flush();
          System.out.println("클라이언트에게 응답 완료!");
          
        } // loop:
        out.flush();
      } 
      
      System.out.println("클라이언트와 연결을 끊었음.");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println("서버 종료!");
  }
  
  private static void updateMember0() throws Exception {
    Member member = (Member)in.readObject();
    
    for (Member m : memberList) {
      if (m.getNo() == member.getNo()) {
        // 클라이언트가 보낸 값으로 기존 객체의 값을 바꾼다.
        m.setName(member.getName());
        m.setEmail(member.getEmail());
        m.setPassword(member.getPassword());
        m.setPhoto(member.getPhoto());
        m.setTel(member.getTel());
        m.setRegisteredDate(member.getRegisteredDate());
        
        out.writeUTF("ok");
        return;
      }
    }
    
    fail("해당 번호의 회원이 없습니다.");
  }
  
  private static void updateMember() throws Exception {
    Member member = (Member)in.readObject();
    
    int index = indexOfMember(member.getNo());
    if (index == -1) {
      fail("해당 번호의 회원이 없습니다.");
      return;
    }
    memberList.set(index, member);
    out.writeUTF("ok");
    
    /*
    for (int i = 0; i < memberList.size(); i++) {
      Member m = memberList.get(i);
      if (m.getNo() == member.getNo()) {
        // 기존 객체를 클라이언트가 보낸로 교체한다. 
        memberList.set(i, member);
        out.writeUTF("ok");
        return;
      }
    }
    out.writeUTF("fail");
    out.writeUTF("해당 번호의 회원이 없습니다.");
    */
  }

  private static void detailMember() throws Exception {
    int no = in.readInt();
    
    int index = indexOfMember(no);
    if (index == -1) {
      fail("해당 번호의 회원이 없습니다.");
      return;
    }
    out.writeUTF("ok");
    out.writeObject(memberList.get(index));
    
    /*
    for (Member m : memberList) {
      if (m.getNo() == no) {
        out.writeUTF("ok");
        out.writeObject(m);
        return;
      }
    }
    out.writeUTF("fail");
    out.writeUTF("해당 번호의 회원이 없습니다.");
    */
  }

  private static void deleteMember() throws Exception {
    int no = in.readInt();
    
    int index = indexOfMember(no);
    if (index == -1) {
      fail("해당 번호의 회원이 없습니다.");
      return;
    }
    memberList.remove(index);
    out.writeUTF("ok");
    
    /*
    for (Member m : memberList) {
      if (m.getNo() == no) {
        memberList.remove(m);
        out.writeUTF("ok");
        return;
      }
    }
    out.writeUTF("fail");
    out.writeUTF("해당 번호의 회원이 없습니다.");
    */
  }

  private static void addMember() throws Exception {
    Member member = (Member)in.readObject();
    memberList.add(member);
    out.writeUTF("ok");
  }
  
  private static void listMember() throws Exception {
    out.writeUTF("ok");
    out.reset(); // 기존에 serialize 했던 객체의 상태를 무시하고 다시 serialize 한다.
    out.writeObject(memberList);
  }
  
  private static int indexOfMember(int no) {
    int i = 0;
    for (Member m : memberList) {
      if (m.getNo() == no) {
        return i;
      }
      i++;
    }
    return -1;
  }
  
  private static void fail(String cause) throws Exception {
    out.writeUTF("fail");
    out.writeUTF(cause);
  }
}






