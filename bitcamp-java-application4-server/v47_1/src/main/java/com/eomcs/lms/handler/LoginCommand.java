package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.HashMap;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Component;
import com.eomcs.util.Input;

@Component("/auth/login")
public class LoginCommand implements Command {
  
  private MemberDao memberDao;
  
  public LoginCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
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
