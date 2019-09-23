package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@Component("/member/detail")
public class MemberDetailController implements PageController {

  @Resource 
  private MemberDao memberDao;


  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Member member = memberDao.findBy(no);
    if (member == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 

    request.setAttribute("member", member);
    return "/jsp/member/detail.jsp";
  }
}
