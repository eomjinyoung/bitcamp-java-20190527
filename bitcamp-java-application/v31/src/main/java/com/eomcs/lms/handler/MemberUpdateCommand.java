package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

public class MemberUpdateCommand implements Command {
  private List<Member> list;
  private Input input;
  
  public MemberUpdateCommand(Input input, List<Member> list) {
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
    
    // 사용자로부터 변경할 값을 입력 받는다.
    String str = input.getStringValue("이름(" + member.getName() + ")? ");
    if (str.length() > 0) {
      member.setName(str);
    }
    
    str = input.getStringValue("이메일(" + member.getEmail() + ")? ");
    if (str.length() > 0) {
      member.setEmail(str);
    }
    
    str = input.getStringValue("암호? ");
    if (str.length() > 0) {
      member.setPassword(str);
    }
    
    str = input.getStringValue("사진(" + member.getPhoto() + ")? ");
    if (str.length() > 0) {
      member.setPhoto(str);
    }
    
    str = input.getStringValue("전화(" + member.getTel() + ")? ");
    if (str.length() > 0) {
      member.setTel(str);
    }
    
    System.out.println("데이터를 변경하였습니다.");
  }

}
