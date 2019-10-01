package com.eomcs.lms.web.json;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

// @RestController
// => request handler의 리턴 값이 응답 데이터임을 선언한다.
// => 리턴 값은 내부에 설정된 HttpMessageConverter에 의해 JSON 문자열로 변환되어 보내진다.
//
@RestController("json.BoardController")
@RequestMapping("/json/board")
public class BoardController {

  @Resource
  private BoardService boardService;

  @PostMapping("add")
  public JsonResult add(Board board) 
      throws Exception {
    try {
      boardService.insert(board);
      return new JsonResult().setState(JsonResult.SUCCESS);
      
    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }
  
  @GetMapping("delete")
  public JsonResult delete(int no) 
      throws Exception {
    try {
      boardService.delete(no);
      return new JsonResult().setState(JsonResult.SUCCESS);
      
    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }
  
  @GetMapping("detail")
  public JsonResult detail(int no) 
      throws Exception {
    try {
      Board board = boardService.get(no);
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(board);
      
    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }
  
  @GetMapping("list")
  public JsonResult list() 
      throws Exception {
    try {
      List<Board> boards = boardService.list();
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(boards);
      
    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }
  
  @PostMapping("update")
  public JsonResult update(Board board) 
      throws Exception {
    try {
      boardService.update(board);
      return new JsonResult().setState(JsonResult.SUCCESS);
      
    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

}
