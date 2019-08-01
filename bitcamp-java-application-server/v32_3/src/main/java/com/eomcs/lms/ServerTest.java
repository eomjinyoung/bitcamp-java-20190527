package com.eomcs.lms;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest {

  public static void main(String[] args) {
    System.out.println("[수업관리시스템 서버 애플리케이션 테스트]");

    try (Socket socket = new Socket("192.168.0.37", 8888);
        //서버와의 입출력을 위해 스트림 객체를 준비한다.
        //=> 버퍼를 사용할 경우, 데이터를 보내는 쪽에서는 출력 스트림을 먼저 준비하라!
        PrintWriter out = new PrintWriter(
            new BufferedOutputStream(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream()))) {
      
      System.out.println("서버와 연결되었음.");
      
      // 서버에 출력한다.
      out.println("'강사' 입니다!");
      out.flush(); // 주의! 버퍼로 출력한 내용을 서버로 보내도록 강제해야 한다. 
      System.out.println("서버에 데이터를 보냈음.");
      
      // 서버가 보낸 데이터를 읽는다.
      String response = in.readLine();
      System.out.println("서버로부터 데이터를 받았음.");
      
      // 서버가 보낸 데이터를 콘솔창에 출력한다.
      System.out.println("-->" + response);

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("서버와 연결 끊음.");
  }
}






