package com.eomcs.lms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;

public class MemberAddCommand implements Command {
  private List<Member> list;
  private Input input;
  
  public MemberAddCommand(Input input, List<Member> list) {
    this.input = input;
    this.list = list;
  }

  @Override
  public void execute() {
    Member member = new Member();
    member.setNo(input.getIntValue("번호? "));
    member.setName(input.getStringValue("이름? "));
    member.setEmail(input.getStringValue("이메일? "));
    member.setPassword(input.getStringValue("암호? "));
    member.setPhoto(input.getStringValue("사진? "));
    member.setTel(input.getStringValue("전화? "));
    member.setRegisteredDate(new Date(System.currentTimeMillis())); 
      
    list.add(member);
    
    System.out.println("저장하였습니다.");
  }

}
