package com.eomcs.lms.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Controller
public class BoardController {

  @Resource
  private BoardDao boardDao;

  @RequestMapping("/board/form")
  public String form() {
    return "/jsp/board/form.jsp";
  }
  
  @RequestMapping("/board/add")
  public String add(Board board) 
      throws Exception {
    boardDao.insert(board);
    return "redirect:list";
  }
  
  @RequestMapping("/board/delete")
  public String delete(int no) 
      throws Exception {
    if (boardDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @RequestMapping("/board/detail")
  public String detail(Map<String,Object> model, int no) 
      throws Exception {

    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 
    boardDao.increaseViewCount(no);

    model.put("board", board);
    return "/jsp/board/detail.jsp";
  }
  
  @RequestMapping("/board/list")
  public String list(Map<String,Object> model) 
      throws Exception {
    
    List<Board> boards = boardDao.findAll();
    model.put("boards", boards);
    return "/jsp/board/list.jsp";
  }
  
  @RequestMapping("/board/update")
  public String update(Board board) 
      throws Exception {
    boardDao.update(board);
    return "redirect:list";
  }

}
