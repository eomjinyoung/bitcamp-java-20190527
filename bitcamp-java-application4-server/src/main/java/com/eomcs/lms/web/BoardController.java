package com.eomcs.lms.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Resource
  private BoardService boardService;

  @GetMapping("form")
  public void form() {
  }
  
  @PostMapping("add")
  public String add(Board board) throws Exception {
    boardService.insert(board);
    return "redirect:list";
  }
  
  @GetMapping("delete")
  public String delete(int no) throws Exception {
    boardService.delete(no);
    return "redirect:list";
  }
  
  @GetMapping("detail")
  public void detail(Model model, int no) 
      throws Exception {
    Board board = boardService.get(no);
    model.addAttribute("board", board);
  }
  
  @GetMapping("list")
  public void list(Model model) 
      throws Exception {
    List<Board> boards = boardService.list();
    model.addAttribute("boards", boards);
    
    // 뷰 URL을 리턴하지 않으면, 프론트 컨트롤러는 
    // 요청 핸들러의 URL을 뷰 URL로 사용한다.
    // 즉 list() 메서드는 다음의 URL을 리턴한 것과 같다.
    // return "board/list";
  }
  
  @PostMapping("update")
  public String update(Board board) 
      throws Exception {
    boardService.update(board);
    return "redirect:list";
  }

}
