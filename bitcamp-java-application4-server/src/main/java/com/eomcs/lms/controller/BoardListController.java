package com.eomcs.lms.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@Component("/board/list")
public class BoardListController implements PageController {
  
  //@Autowired // 스프링 애노테이션
  @Resource // java 표준 애노테이션
  private BoardDao boardDao;  // Spring IoC 컨테이너가 의존 객체를 자동으로 주입해 준다.
  
  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    List<Board> boards = boardDao.findAll();
    request.setAttribute("boards", boards);
    return "/jsp/board/list.jsp";
  }
}
