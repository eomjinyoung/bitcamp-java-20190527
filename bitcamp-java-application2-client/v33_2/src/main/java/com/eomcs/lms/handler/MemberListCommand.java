package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

public class MemberListCommand implements Command {
  private MemberDao memberDao;
  
  public MemberListCommand(Input input, MemberDao memberDao) {
    this.memberDao = memberDao;
  }
  
  @Override
  public void execute() {
    try {
      List<Member> members = memberDao.findAll();
      for (Member member : members) {
        System.out.printf("%s, %s, %s, %s, %s\n", 
            member.getNo(), member.getName(), member.getEmail(), 
            member.getTel(), member.getRegisteredDate());
      }

    } catch (Exception e) {
      System.out.println("데이터 목록 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
