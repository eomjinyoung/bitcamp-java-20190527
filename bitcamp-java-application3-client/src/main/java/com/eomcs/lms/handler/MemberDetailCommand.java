package com.eomcs.lms.handler;

import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

public class MemberDetailCommand implements Command {
  private MemberDao memberDao;
  private Input input;
  
  public MemberDetailCommand(Input input, MemberDao memberDao) {
    this.input = input;
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");
    
    try {
      Member member = memberDao.findBy(no);
      if (member == null) {
        System.out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      System.out.printf("이름: %s\n", member.getName());
      System.out.printf("이메일: %s\n", member.getEmail());
      System.out.printf("암호: %s\n", member.getPassword());
      System.out.printf("사진: %s\n", member.getPhoto());
      System.out.printf("전화: %s\n", member.getTel());
      System.out.printf("가입일: %s\n", member.getRegisteredDate());

    } catch (Exception e) {
      System.out.println("데이터 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
