package com.eomcs.lms.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@Component("/member/search")
public class MemberSearchController implements PageController {

  @Resource 
  private MemberDao memberDao;


  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    List<Member> members = memberDao.findByKeyword(
        request.getParameter("keyword"));

    request.setAttribute("members", members);
    return "/jsp/member/search.jsp";
  }
}
