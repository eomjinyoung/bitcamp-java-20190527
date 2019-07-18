package com.eomcs.lms.handler;

import java.sql.Date;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Input;
import com.eomcs.util.LinkedList;

public class MemberHandler {
  private LinkedList<Member> memberList = new LinkedList<>();
  private Input input;
  
  public MemberHandler(Input input) {
    this.input = input;
  }
  
  public void listMember() {
    Member[] members = memberList.toArray(new Member[] {});
    for (Member member : members) {
      System.out.printf("%s, %s, %s, %s, %s\n", 
          member.getNo(), member.getName(), member.getEmail(), 
          member.getTel(), member.getRegisteredDate());
    }
  }

  public void addMember() {
    Member member = new Member();
    member.setNo(input.getIntValue("번호? "));
    member.setName(input.getStringValue("이름? "));
    member.setEmail(input.getStringValue("이메일? "));
    member.setPassword(input.getStringValue("암호? "));
    member.setPhoto(input.getStringValue("사진? "));
    member.setTel(input.getStringValue("전화? "));
    member.setRegisteredDate(new Date(System.currentTimeMillis())); 
      
    memberList.add(member);
    
    System.out.println("저장하였습니다.");
  }
  
  public void detailMember() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Member 객체를 찾는다.
    Member member = null;
    for (int i = 0; i < memberList.size(); i++) {
      Member temp = memberList.get(i);
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

  public void updateMember() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Member 객체를 찾는다.
    Member member = null;
    for (int i = 0; i < memberList.size(); i++) {
      Member temp = memberList.get(i);
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

  public void deleteMember() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Member 객체를 찾는다.
    for (int i = 0; i < memberList.size(); i++) {
      Member temp = memberList.get(i);
      if (temp.getNo() == no) {
        memberList.remove(i);
        System.out.println("데이터를 삭제하였습니다.");
        return;
      }
    }
    
    System.out.println("해당 번호의 데이터가 없습니다!");
    
  }
}
