package com.eomcs.lms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

  public static void main(String[] args) {
    System.out.println("[수업관리시스템 서버 애플리케이션]");

    // 클라이언트의 연결을 처리할 객체 준비
    // => new ServerSocket(포트번호)
    //    
    // 포트번호? 
    // => 클라이언트가 서버에 연결할 때 사용할 사서함 번호
    // => 사서함 번호는 한 컴퓨터에서 중복 사용할 수 없다.
    //
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 시작!");

      // => 클라이언트의 연결 요청이 들어올 때까지 기다리다가
      //    요청이 들어오는 즉시 승인을 한 후, 연결 정보를 리턴 한다.
      try (Socket clientSocket = serverSocket.accept()) {
        System.out.println("클라이언트와 연결되었음.");
      
      } // try 블록을 벗어날 때 clientSocket.close()가 자동으로 호출되어 
        // 클라이언트와의 연결을 끊는다. 
      System.out.println("클라이언트와 연결을 끊었음.");
      
    } catch (IOException e) {
      // 예외가 발생하면 일단 어디에서 예외가 발생했는지 확인하기 위해 호출 정보를 모두 출력한다.
      e.printStackTrace();
    }

    System.out.println("서버 종료!");
  }
}






