package com.eomcs.lms.web;

import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController {

  @Resource 
  private MemberDao memberDao;

  @GetMapping("form")
  public void form() {
  }
  
  @PostMapping("add")
  public String add(
      HttpServletRequest request, 
      Member member, 
      MultipartFile file) throws Exception {
    
    String uploadDir = request.getServletContext().getRealPath("/upload/member");
    
    // 업로드 된 사진 파일 처리
    if (!file.isEmpty()) {
      String filename = UUID.randomUUID().toString();
      member.setPhoto(filename);
      file.transferTo(new File(uploadDir + "/" + filename));
    }

    memberDao.insert(member);
    return "redirect:list";
  }
  
  @GetMapping("delete")
  public String delete(int no) 
      throws Exception {

    if (memberDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @GetMapping("detail")
  public void detail(Model model, int no) 
      throws Exception {

    Member member = memberDao.findBy(no);
    if (member == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    } 

    model.addAttribute("member", member);
  }
  
  @GetMapping("list")
  public void list(Model model) throws Exception {

    List<Member> members = memberDao.findAll();
    model.addAttribute("members", members);
  }
  
  @GetMapping("search")
  public void search(Model model, String keyword) throws Exception {

    List<Member> members = memberDao.findByKeyword(keyword);
    model.addAttribute("members", members);
  }
  
  @PostMapping("update")
  public String update(
      HttpServletRequest request,
      Member member,
      MultipartFile file) 
      throws Exception {
    
    String uploadDir = request.getServletContext().getRealPath("/upload/member");

    // 업로드 된 사진 파일 처리
    if (!file.isEmpty()) {
      String filename = UUID.randomUUID().toString();
      member.setPhoto(filename);
      file.transferTo(new File(uploadDir + "/" + filename));
    }
    memberDao.update(member);
    return "redirect:list";
  }
}
