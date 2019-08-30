package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Component;
import com.eomcs.util.Input;

@Component("/member/add")
public class MemberAddCommand implements Command {
  private MemberDao memberDao;

  public MemberAddCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try {
      Member member = new Member();
      member.setName(Input.getStringValue(in, out, "이름? "));
      member.setEmail(Input.getStringValue(in, out, "이메일? "));
      member.setPassword(Input.getStringValue(in, out, "암호? "));
      member.setPhoto(Input.getStringValue(in, out, "사진? "));
      member.setTel(Input.getStringValue(in, out, "전화? "));
      
      memberDao.insert(member);
      out.println("저장하였습니다.");
      
    } catch (Exception e) {
      out.println("데이터 저장에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
