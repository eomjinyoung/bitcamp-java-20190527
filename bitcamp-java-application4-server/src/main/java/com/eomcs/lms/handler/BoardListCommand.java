package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardListCommand implements Command {
  
  private SqlSessionFactory sqlSessionFactory;
  
  public BoardListCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  public String getCommandName() {
    return "/board/list";
  }
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      
      List<Board> boards = boardDao.findAll();
      for (Board board : boards) {
        out.printf("%s, %s, %s, %s\n", 
            board.getNo(), board.getContents(), 
            board.getCreatedDate(), board.getViewCount());
      }
      
    } catch (Exception e) {
      out.println("데이터 목록 조회에 실패했습니다!");
      e.printStackTrace();
    }
  }

}
