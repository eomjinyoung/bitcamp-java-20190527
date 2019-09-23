package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Component("/board/detail")
public class BoardDetailController implements PageController {

  @Resource
  private BoardDao boardDao;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 

    boardDao.increaseViewCount(no);

    request.setAttribute("board", board);
    return "/jsp/board/detail.jsp";
  }
}
