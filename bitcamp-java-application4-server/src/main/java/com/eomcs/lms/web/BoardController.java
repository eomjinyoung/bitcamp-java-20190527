package com.eomcs.lms.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Resource
  private BoardDao boardDao;

  @RequestMapping("form")
  public void form() {
  }
  
  @RequestMapping("add")
  public String add(Board board) 
      throws Exception {
    boardDao.insert(board);
    return "redirect:list";
  }
  
  @RequestMapping("delete")
  public String delete(int no) 
      throws Exception {
    if (boardDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @RequestMapping("detail")
  public void detail(Model model, int no) 
      throws Exception {

    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 
    boardDao.increaseViewCount(no);

    model.addAttribute("board", board);
  }
  
  @RequestMapping("list")
  public void list(Model model) 
      throws Exception {
    
    List<Board> boards = boardDao.findAll();
    model.addAttribute("boards", boards);
  }
  
  @RequestMapping("update")
  public String update(Board board) 
      throws Exception {
    boardDao.update(board);
    return "redirect:list";
  }

}
