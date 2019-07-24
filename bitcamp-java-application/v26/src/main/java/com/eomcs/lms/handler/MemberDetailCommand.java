package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

public class MemberDetailCommand implements Command {
  private List<Member> list;
  private Input input;
  
  public MemberDetailCommand(Input input, List<Member> list) {
    this.input = input;
    this.list = list;
  }

  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Member 객체를 찾는다.
    Member member = null;
    for (int i = 0; i < list.size(); i++) {
      Member temp = list.get(i);
      if (temp.getNo() == no) {
        member = temp;
        break;
      }
    }
    
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
  }

}
