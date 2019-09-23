package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Component("/board/update")
public class BoardUpdateController implements PageController {

  @Resource
  private BoardDao boardDao;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setContents(request.getParameter("contents"));
    boardDao.update(board);

    return "redirect:list";
  }
}
