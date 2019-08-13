package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

public class MemberUpdateCommand implements Command {
  private MemberDao memberDao;
  
  public MemberUpdateCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try {
      int no = Input.getIntValue(in, out, "번호? ");
      
      Member member = memberDao.findBy(no);
      if (member == null) {
        out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      
      // 사용자로부터 변경할 값을 입력 받는다.
      String str = Input.getStringValue(in, out, "이름(" + member.getName() + ")? ");
      if (str.length() > 0) {
        member.setName(str);
      }
      
      str = Input.getStringValue(in, out, "이메일(" + member.getEmail() + ")? ");
      if (str.length() > 0) {
        member.setEmail(str);
      }
      
      str = Input.getStringValue(in, out, "암호? ");
      if (str.length() > 0) {
        member.setPassword(str);
      }
      
      str = Input.getStringValue(in, out, "사진(" + member.getPhoto() + ")? ");
      if (str.length() > 0) {
        member.setPhoto(str);
      }
      
      str = Input.getStringValue(in, out, "전화(" + member.getTel() + ")? ");
      if (str.length() > 0) {
        member.setTel(str);
      }
      
      memberDao.update(member);
      out.println("데이터를 변경하였습니다.");

    } catch (Exception e) {
      out.println("데이터 변경에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
