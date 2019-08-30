package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.util.Input;

public class BoardUpdateCommand implements Command {
  
  private SqlSessionFactory sqlSessionFactory;
  
  public BoardUpdateCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public String getCommandName() {
    return "/board/update";
  }
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      
      int no = Input.getIntValue(in, out, "번호? ");
      
      Board board = boardDao.findBy(no);
      if (board == null) {
        out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      
      String str = Input.getStringValue(in, out, "내용? ");
      if (str.length() > 0) {
        board.setContents(str);
        boardDao.update(board);
        out.println("데이터를 변경하였습니다.");
        
      } else {
        out.println("데이터 변경을 취소합니다.");
      }
    
    } catch (Exception e) {
      out.println("데이터 변경에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
