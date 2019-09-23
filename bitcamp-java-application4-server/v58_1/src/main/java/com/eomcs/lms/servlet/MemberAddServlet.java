package com.eomcs.lms.servlet;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  String uploadDir;
  private MemberDao memberDao;

  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    memberDao = appCtx.getBean(MemberDao.class);
    uploadDir = getServletContext().getRealPath("/upload/member");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    request.setAttribute("viewUrl", "/jsp/member/form.jsp");
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    try {
      Member member = new Member();
      
      member.setName(request.getParameter("name"));
      member.setEmail(request.getParameter("email"));
      member.setPassword(request.getParameter("password"));
      member.setTel(request.getParameter("tel"));

      // 업로드 된 사진 파일 처리
      Part photoPart = request.getPart("photo");
      if (photoPart != null && photoPart.getSize() > 0) {
        String filename = UUID.randomUUID().toString();
        member.setPhoto(filename);
        photoPart.write(uploadDir + "/" + filename);
      }
      
      memberDao.insert(member);
      request.setAttribute("viewUrl", "redirect:list");
      
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("refresh", "list");
    } 
  }
}
