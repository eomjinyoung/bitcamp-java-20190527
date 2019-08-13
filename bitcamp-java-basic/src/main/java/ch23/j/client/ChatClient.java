package ch23.j.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClient extends Frame implements ActionListener {
  private static final long serialVersionUID = 1L;

  TextField addressTF = new TextField(20);
  TextField portTF = new TextField(4);
  Button connectBtn = new Button("연결");
  TextArea chattingPane = new TextArea();
  TextField messageTF = new TextField();
  Button sendBtn = new Button("보내기");
  
  public ChatClient(String title) {
    super(title);
    this.setSize(600, 480);
    
    // 윈도우 관련 이벤트를 처리할 담당자를 설정한다.
    // => 옵저버 패턴
    // => 윈도우 이벤트가 발생했을 때 보고를 받을 객체를 등록한다.
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    
    Panel topPane = new Panel();
    topPane.add(addressTF);
    topPane.add(portTF);
    topPane.add(connectBtn);
    this.add(topPane, BorderLayout.NORTH);

    this.add(chattingPane, BorderLayout.CENTER);
    
    Panel bottomPane = new Panel();
    bottomPane.setLayout(new BorderLayout());
    bottomPane.add(messageTF, BorderLayout.CENTER);
    bottomPane.add(sendBtn, BorderLayout.EAST);
    
    this.add(bottomPane, BorderLayout.SOUTH);
    
    // 연결 버튼에 대해 옵저버(리스너) 등록하기
    connectBtn.addActionListener(this);
    
    // 보내기 버튼에 대해 옵저버(리스너) 등록하기
    sendBtn.addActionListener(this);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == sendBtn) {
      System.out.println("보내기 눌렀네!");
      
    } else if (e.getSource() == connectBtn) {
      System.out.println("연결 눌렀네!");
    }
  }
  
  private void connectChatServer() {
    try {
      Socket socket = new Socket(
          addressTF.getText(), 
          Integer.parseInt(portTF.getText()));
      BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));
      PrintStream out = new PrintStream(socket.getOutputStream());
      
      
    } catch (Exception e) {
      
    }
  }
  
  public static void main(String[] args) {
    ChatClient app = new ChatClient("비트 채팅!");
    app.setVisible(true);
  }
}

// Java 윈도우 프로그래밍
// 1) AWT
// 2) Swing
// 3) Eclise SWT
// 4) JavaFX