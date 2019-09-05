package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

@Component
public class LoginCommand {
  
  private MemberDao memberDao;
  
  public LoginCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @RequestMapping("/auth/login") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void execute(BufferedReader in, PrintStream out) {
    try {
      HashMap<String,Object> params = new HashMap<>();
      params.put("email", Input.getStringValue(in, out, "이메일? "));
      params.put("password", Input.getStringValue(in, out, "암호? "));
      
      Member member = memberDao.findByEmailPassword(params);
      
      if (member == null) {
        out.println("이메일 또는 암호가 맞지 않습니다!");
      } else {
        out.printf("%s 님 환영합니다.\n", member.getName());
      }
    } catch (Exception e) {
      out.println("로그인 실행에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
