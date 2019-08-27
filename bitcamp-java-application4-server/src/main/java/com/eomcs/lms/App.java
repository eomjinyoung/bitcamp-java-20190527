// v43_1 : Mybatis 도입하기
package com.eomcs.lms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.dao.mariadb.BoardDaoImpl;
import com.eomcs.lms.dao.mariadb.LessonDaoImpl;
import com.eomcs.lms.dao.mariadb.MemberDaoImpl;
import com.eomcs.lms.dao.mariadb.PhotoBoardDaoImpl;
import com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl;
import com.eomcs.lms.handler.BoardAddCommand;
import com.eomcs.lms.handler.BoardDeleteCommand;
import com.eomcs.lms.handler.BoardDetailCommand;
import com.eomcs.lms.handler.BoardListCommand;
import com.eomcs.lms.handler.BoardUpdateCommand;
import com.eomcs.lms.handler.Command;
import com.eomcs.lms.handler.LessonAddCommand;
import com.eomcs.lms.handler.LessonDeleteCommand;
import com.eomcs.lms.handler.LessonDetailCommand;
import com.eomcs.lms.handler.LessonListCommand;
import com.eomcs.lms.handler.LessonUpdateCommand;
import com.eomcs.lms.handler.LoginCommand;
import com.eomcs.lms.handler.MemberAddCommand;
import com.eomcs.lms.handler.MemberDeleteCommand;
import com.eomcs.lms.handler.MemberDetailCommand;
import com.eomcs.lms.handler.MemberListCommand;
import com.eomcs.lms.handler.MemberSearchCommand;
import com.eomcs.lms.handler.MemberUpdateCommand;
import com.eomcs.lms.handler.PhotoBoardAddCommand;
import com.eomcs.lms.handler.PhotoBoardDeleteCommand;
import com.eomcs.lms.handler.PhotoBoardDetailCommand;
import com.eomcs.lms.handler.PhotoBoardListCommand;
import com.eomcs.lms.handler.PhotoBoardUpdateCommand;
import com.eomcs.util.DataSource;
import com.eomcs.util.PlatformTransactionManager;

public class App {

  private static final int CONTINUE = 1;
  private static final int STOP = 0;

  HashMap<String,Command> commandMap = new HashMap<>();
  int state;
  
  // 스레드풀
  ExecutorService executorService = Executors.newCachedThreadPool();
  
  DataSource dataSource;
  
  public App() throws Exception {

    // 처음에는 클라이언트 요청을 처리해야 하는 상태로 설정한다.
    state = CONTINUE;
    
    try {
      // 커넥션 관리자를 준비한다.
      dataSource = new DataSource(
          "org.mariadb.jdbc.Driver",
          "jdbc:mariadb://localhost/bitcampdb",
          "bitcamp",
          "1111");

      // 트랜잭션 관리자를 준비한다.
      PlatformTransactionManager txManager = 
          new PlatformTransactionManager(dataSource);
      
      // Mybatis의 SQL 실행 도구 준비
      // => Mybatis 설정 파일을 읽을 때 사용할 입력스트림 도구를 준비한다.
      InputStream inputStream = 
          Resources.getResourceAsStream("com/eomcs/lms/conf/mybatis-config.xml");
      
      // => SQL을 실행할 때 사용할 도구(SqlSession;샌드위치)를 만들어주는 
      //    생성기(SqlSessionFactory;파리바게트) 공장(SqlSessionFactoryBuilder)를 준비한다.
      //
      /*
      SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
      SqlSessionFactory factory = builder.build(inputStream);
      SqlSession sqlSession = factory.openSession();
      */
      SqlSessionFactory sqlSessionFactory =
        new SqlSessionFactoryBuilder().build(inputStream);
      
      // Command 객체가 사용할 데이터 처리 객체를 준비한다.
      BoardDao boardDao = new BoardDaoImpl(sqlSessionFactory);
      MemberDao memberDao = new MemberDaoImpl(sqlSessionFactory);
      LessonDao lessonDao = new LessonDaoImpl(sqlSessionFactory);
      PhotoBoardDao photoBoardDao = new PhotoBoardDaoImpl(dataSource);
      PhotoFileDao photoFileDao = new PhotoFileDaoImpl(dataSource);

      // 클라이언트 명령을 처리할 커맨드 객체를 준비한다.
      commandMap.put("/lesson/add", new LessonAddCommand(lessonDao));
      commandMap.put("/lesson/delete", new LessonDeleteCommand(lessonDao));
      commandMap.put("/lesson/detail", new LessonDetailCommand(lessonDao));
      commandMap.put("/lesson/list", new LessonListCommand(lessonDao));
      commandMap.put("/lesson/update", new LessonUpdateCommand(lessonDao));

      commandMap.put("/member/add", new MemberAddCommand(memberDao));
      commandMap.put("/member/delete", new MemberDeleteCommand(memberDao));
      commandMap.put("/member/detail", new MemberDetailCommand(memberDao));
      commandMap.put("/member/list", new MemberListCommand(memberDao));
      commandMap.put("/member/update", new MemberUpdateCommand(memberDao));
      commandMap.put("/member/search", new MemberSearchCommand(memberDao));

      commandMap.put("/board/add", new BoardAddCommand(boardDao));
      commandMap.put("/board/delete", new BoardDeleteCommand(boardDao));
      commandMap.put("/board/detail", new BoardDetailCommand(boardDao));
      commandMap.put("/board/list", new BoardListCommand(boardDao));
      commandMap.put("/board/update", new BoardUpdateCommand(boardDao));

      commandMap.put("/photoboard/add", 
          new PhotoBoardAddCommand(txManager, photoBoardDao, photoFileDao));
      commandMap.put("/photoboard/delete", 
          new PhotoBoardDeleteCommand(txManager, photoBoardDao, photoFileDao));
      commandMap.put("/photoboard/detail", 
          new PhotoBoardDetailCommand(photoBoardDao, photoFileDao));
      commandMap.put("/photoboard/list", new PhotoBoardListCommand(photoBoardDao));
      commandMap.put("/photoboard/update", 
          new PhotoBoardUpdateCommand(txManager, photoBoardDao, photoFileDao));
      
      commandMap.put("/auth/login", new LoginCommand(memberDao));
      
    } catch (Exception e) {
      System.out.println("DBMS에 연결할 수 없습니다!");
      throw e;
    }

  }

  @SuppressWarnings("static-access")
  private void service() {

    try (ServerSocket serverSocket = new ServerSocket(8888);) {
      System.out.println("애플리케이션 서버가 시작되었음!");

      while (true) {
        // 클라이언트가 접속하면 작업을 수행할 Runnable 객체를 만들어 스레드풀에 맡긴다.
        executorService.submit(new CommandProcessor(serverSocket.accept()));
        
        // 한 클라이언트가 serverstop 명령을 보내면 종료 상태로 설정되고 
        // 다음 요청을 처리할 때 즉시 실행을 멈춘다.
        if (state == STOP)
          break;
      }

      // 스레드풀에게 실행 종료를 요청한다.
      // => 스레드풀은 자신이 관리하는 스레드들이 실행이 종료되었는지 감시한다.
      executorService.shutdown();
      
      // 스레드풀이 관리하는 모든 스레드가 종료되었는지 매 0.5초마다 검사한다.
      // => 스레드풀의 모든 스레드가 실행을 종료했으면 즉시 main 스레드를 종료한다.
      while (!executorService.isTerminated()) {
        Thread.currentThread().sleep(500);
      }
      
      System.out.println("애플리케이션 서버를 종료함!");

    } catch (Exception e) {
      System.out.println("소켓 통신 오류!");
      e.printStackTrace();
    }
  }

  class CommandProcessor implements Runnable {
    
    Socket socket;
    
    public CommandProcessor(Socket socket) {
      this.socket = socket;
    }
    
    @Override
    public void run() {
      try (Socket socket = this.socket;
          BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream()));
          PrintStream out = new PrintStream(socket.getOutputStream())) {

        System.out.println("클라이언트와 연결됨!");

        // 클라이언트가 보낸 명령을 읽는다.
        String request = in.readLine();
        if (request.equals("quit")) {
          out.println("Good bye!");
          
        } else if (request.equals("serverstop")) {
          state = STOP;
          out.println("Good bye!");
          
        } else {
          // non-static 중첩 클래스는 바깥 클래스의 인스턴스 멤버를 사용할 수 있다.
          Command command = commandMap.get(request);
          if (command == null) {
            out.println("해당 명령을 처리할 수 없습니다.");
          } else {
            command.execute(in, out);
          }
        }
        out.println("!end!");
        out.flush();

        System.out.println("클라이언트와 연결 끊음!");

      } catch (Exception e) {
        System.out.println("클라이언트와 통신 오류!");
        
      } finally {
        // 현재 스레드가 클라이언트 요청을 처리했으면 (정상처리든 오류가 발생했든)
        // 현재 스레드에 보관된 커넥션 객체를 제거해야 한다.
        // 그래야만 다음 클라이언트 요청이 들어 왔을 때 
        // 새 커넥션 객체를 사용할 것이다.
        dataSource.clearConnection();
      }
    }
  }
  
  public static void main(String[] args) {
    try {
      App app = new App();
      app.service();

    } catch (Exception e) {
      System.out.println("시스템 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}










