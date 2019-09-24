package com.eomcs.lms.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

@Component("/photoboard/list")
public class PhotoBoardListController {

  @Resource private PhotoBoardDao photoBoardDao;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    List<PhotoBoard> photoBoards = photoBoardDao.findAll();
    request.setAttribute("photoBoards", photoBoards);
    return "/jsp/photoboard/list.jsp";
  }
}
