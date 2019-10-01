package com.eomcs.lms.web.json;

import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;

@RestController("json.MemberController")
@RequestMapping("/json/member")
public class MemberController {

  @Resource private MemberService memberService;

  String uploadDir;

  public MemberController(ServletContext sc) {
    uploadDir = sc.getRealPath("/upload/member");
  }

  @PostMapping("add")
  public JsonResult add(Member member, MultipartFile file) throws Exception {
    try {
      member.setPhoto(writeFile(file));
      memberService.insert(member);
      return new JsonResult().setState(JsonResult.SUCCESS);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("delete")
  public JsonResult delete(int no) throws Exception {
    try {
      memberService.delete(no);
      return new JsonResult().setState(JsonResult.SUCCESS);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("detail")
  public JsonResult detail(int no) throws Exception {
    try {
      Member member = memberService.get(no);
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(member);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("list")
  public JsonResult list() throws Exception {
    try {
      List<Member> members = memberService.list();
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(members);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("search")
  public JsonResult search(String keyword) throws Exception {
    try {
      List<Member> members = memberService.search(keyword);
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(members);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @PostMapping("update")
  public JsonResult update(Member member, MultipartFile file) throws Exception {
    try {
      member.setPhoto(writeFile(file));
      memberService.update(member);
      return new JsonResult().setState(JsonResult.SUCCESS);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  private String writeFile(MultipartFile file) throws Exception {
    if (file.isEmpty())
      return null;

    String filename = UUID.randomUUID().toString();
    file.transferTo(new File(uploadDir + "/" + filename));
    return filename;
  }
}
