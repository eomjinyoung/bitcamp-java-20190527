package com.eomcs.lms.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Controller
public class BoardController {

  @Resource
  private BoardDao boardDao;

  @RequestMapping("/board/add")
  public String add(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    if (request.getMethod().equalsIgnoreCase("GET")) {
      return "/jsp/board/form.jsp";
    }

    Board board = new Board();
    board.setContents(request.getParameter("contents"));
    boardDao.insert(board);

    return "redirect:list";
  }
  
  @RequestMapping("/board/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    if (boardDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @RequestMapping("/board/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) 
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
  
  @RequestMapping("/board/list")
  public String list(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    List<Board> boards = boardDao.findAll();
    request.setAttribute("boards", boards);
    return "/jsp/board/list.jsp";
  }
  
  @RequestMapping("/board/update")
  public String update(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setContents(request.getParameter("contents"));
    boardDao.update(board);

    return "redirect:list";
  }

}
