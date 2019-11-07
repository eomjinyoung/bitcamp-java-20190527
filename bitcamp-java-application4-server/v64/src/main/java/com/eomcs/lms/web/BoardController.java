package com.eomcs.lms.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public void list(
      @RequestParam(defaultValue = "1") int pageNo, 
      @RequestParam(defaultValue = "3") int pageSize, 
      Model model) 
      throws Exception {
    
    // 총 페이지 개수 알아내기
    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }
    int size = boardService.size();
    int totalPage = size / pageSize; // 13 / 3 = 4.x
    if (size % pageSize > 0) {
      totalPage++;
    }
    
    // 요청하는 페이지 번호가 유효하지 않을 때는 기본 값으로 1페이지로 지정한다.
    if (pageNo < 1 || pageNo > totalPage) {
      pageNo = 1;
    }
      
    List<Board> boards = boardService.list(pageNo, pageSize);
    
    model.addAttribute("boards", boards);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("totalPage", totalPage);
    model.addAttribute("size", size);
    model.addAttribute("beginPage", (pageNo - 2) > 0 ? (pageNo - 2) : 1);
    model.addAttribute("endPage", (pageNo + 2) < totalPage ? (pageNo + 2) : totalPage);
    
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
