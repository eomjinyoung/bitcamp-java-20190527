package com.eomcs.lms.web.json;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public JsonResult list(
      @RequestParam(defaultValue = "1") int pageNo, 
      @RequestParam(defaultValue = "3") int pageSize) 
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
      
    try {
      List<Board> boards = boardService.list(pageNo, pageSize);
      
      HashMap<String,Object> result = new HashMap<>();
      result.put("boards", boards);
      result.put("pageNo", pageNo);
      result.put("pageSize", pageSize);
      result.put("totalPage", totalPage);
      result.put("size", size);
      result.put("beginPage", (pageNo - 2) > 0 ? (pageNo - 2) : 1);
      result.put("endPage", (pageNo + 2) < totalPage ? (pageNo + 2) : totalPage);
      
      
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(result);
      
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
