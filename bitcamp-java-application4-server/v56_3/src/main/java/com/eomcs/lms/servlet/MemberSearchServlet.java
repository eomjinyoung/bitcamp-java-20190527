package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@WebServlet("/member/search")
public class MemberSearchServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private MemberDao memberDao;

  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    memberDao = appCtx.getBean(MemberDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>회원 검색</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "</head>");
    out.println("<body><h1>회원 검색</h1>");
    
    try {
      out.println("<table class='table table-hover'>");
      out.println("<tr><th>번호</th><th>이름</th><th>이메일</th><th>전화</th><th>등록일</th></tr>");
      
      List<Member> members = memberDao.findByKeyword(
          request.getParameter("keyword"));
      for (Member member : members) {
        out.printf("<tr>"
            + "<td>%d</td>"
            + "<td><a href='/member/detail?no=%d'>%s</a></td>"
            + "<td>%s</td>"
            + "<td>%s</td>"
            + "<td>%s</td></tr>\n", 
            member.getNo(),
            member.getNo(),
            member.getName(), 
            member.getEmail(), 
            member.getTel(),
            member.getRegisteredDate());
      }
      out.println("</table>");
      
    } catch (Exception e) {
      out.println("<p>데이터 검색에 실패했습니다!</p>");
      throw new RuntimeException(e);
    
    } finally {
      out.println("</body></html>");
    }
  }
}
