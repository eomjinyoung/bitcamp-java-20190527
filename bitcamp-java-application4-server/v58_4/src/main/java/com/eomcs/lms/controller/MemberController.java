package com.eomcs.lms.controller;

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@Controller
public class MemberController {

  @Resource 
  private MemberDao memberDao;

  @RequestMapping("/member/add")
  public String add(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    if (request.getMethod().equalsIgnoreCase("GET")) {
      return "/jsp/member/form.jsp";
    }

    String uploadDir = request.getServletContext().getRealPath("/upload/member");
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
    return "redirect:list";
  }
  
  @RequestMapping("/member/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));
    if (memberDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @RequestMapping("/member/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Member member = memberDao.findBy(no);
    if (member == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 

    request.setAttribute("member", member);
    return "/jsp/member/detail.jsp";
  }
  
  @RequestMapping("/member/list")
  public String list(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    List<Member> members = memberDao.findAll();

    request.setAttribute("members", members);
    return "/jsp/member/list.jsp";
  }
  
  @RequestMapping("/member/search")
  public String search(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    List<Member> members = memberDao.findByKeyword(
        request.getParameter("keyword"));

    request.setAttribute("members", members);
    return "/jsp/member/search.jsp";
  }
  
  @RequestMapping("/member/update")
  public String update(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    String uploadDir = request.getServletContext().getRealPath("/upload/member");

    Member member = new Member();
    member.setNo(Integer.parseInt(request.getParameter("no")));
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

    memberDao.update(member);
    return "redirect:list";
  }
}
