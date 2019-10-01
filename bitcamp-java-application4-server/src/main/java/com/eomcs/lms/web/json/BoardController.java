package com.eomcs.lms.web.json;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

// @RestController
// => request handler의 리턴 값이 응답 데이터임을 선언한다.
// => 리턴 값은 내부에 설정된 HttpMessageConverter에 의해 JSON 문자열로 변환되어 보내진다.
//
@RestController("json.BoardController")
@RequestMapping("/json/board")
public class BoardController {

  @Resource
  private BoardDao boardDao;

  @PostMapping("add")
  public JsonResult add(Board board) 
      throws Exception {
    try {
      boardDao.insert(board);
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
      if (boardDao.delete(no) == 0) {
        throw new Exception("해당 데이터가 없습니다.");
      }
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
      Board board = boardDao.findBy(no);
      if (board == null) {
        throw new Exception("해당 번호의 데이터가 없습니다!");
      } 
      boardDao.increaseViewCount(no);
  
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
      List<Board> boards = boardDao.findAll();
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
      boardDao.update(board);
      return new JsonResult().setState(JsonResult.SUCCESS);
      
    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

}
