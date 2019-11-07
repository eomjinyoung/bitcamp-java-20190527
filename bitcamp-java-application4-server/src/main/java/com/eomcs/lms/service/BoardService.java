package com.eomcs.lms.service;

import java.util.List;
import com.eomcs.lms.domain.Board;

// 역할:
// => 게시물 관리 업무를 수행
// => 트랜잭션 제어
// => 여러 페이지 컨트롤러가 사용한다.
//
public interface BoardService {
  List<Board> list(int pageNo, int pageSize) throws Exception;
  Board get(int no) throws Exception;
  void insert(Board board) throws Exception;
  void update(Board board) throws Exception;
  void delete(int no) throws Exception;
  int size() throws Exception;
}










