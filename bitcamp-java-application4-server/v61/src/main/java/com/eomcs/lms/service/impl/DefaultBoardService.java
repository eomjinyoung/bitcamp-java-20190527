package com.eomcs.lms.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

// BoardService 기본 구현체 
//
@Service
public class DefaultBoardService implements BoardService {

  @Resource
  private BoardDao boardDao;

  @Override
  public void insert(Board board) throws Exception {
    boardDao.insert(board);
  }

  @Override
  public void delete(int no) throws Exception {
    if (boardDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
  }

  @Override
  public Board get(int no) throws Exception {
    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 
    boardDao.increaseViewCount(no);
    return board;
  }

  @Override
  public List<Board> list() throws Exception {
    return boardDao.findAll();
  }

  @Override
  public void update(Board board) throws Exception {
    boardDao.update(board);
  }
}
