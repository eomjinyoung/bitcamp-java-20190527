package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Component("/board/add")
public class BoardAddController implements PageController {

  @Resource
  private BoardDao boardDao;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    if (request.getMethod().equalsIgnoreCase("GET")) {
      return "/jsp/board/form.jsp";
    }

    Board board = new Board();
    board.setContents(request.getParameter("contents"));
    boardDao.insert(board);

    return "redirect:list";
  }

}
