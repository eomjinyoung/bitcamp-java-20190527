package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

@Component("/photoboard/detail")
public class PhotoBoardDetailController {

  @Resource private PhotoBoardDao photoBoardDao;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    PhotoBoard photoBoard = photoBoardDao.findWithFilesBy(no);
    if (photoBoard == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }
    photoBoardDao.increaseViewCount(no);

    request.setAttribute("photoBoard", photoBoard);
    return "/jsp/photoboard/detail.jsp";
  }
}
