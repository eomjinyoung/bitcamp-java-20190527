package com.eomcs.lms.handler;

import com.eomcs.lms.dao.MemberDao;
import com.eomcs.util.Input;

public class MemberDeleteCommand implements Command {
  private MemberDao memberDao;
  private Input input;

  public MemberDeleteCommand(Input input, MemberDao memberDao) {
    this.input = input;
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");

    try {
      if (memberDao.delete(no) > 0) {
        System.out.println("데이터를 삭제하였습니다.");
      } else {
        System.out.println("해당 데이터가 없습니다.");
      }

    } catch (Exception e) {
      System.out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
    }

  }
}
