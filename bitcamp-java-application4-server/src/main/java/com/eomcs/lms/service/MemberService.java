package com.eomcs.lms.service;

import java.util.List;
import com.eomcs.lms.domain.Member;

// 역할:
// => 회원 관리 업무를 수행
// => 트랜잭션 제어
// => 여러 페이지 컨트롤러가 사용한다.
//
public interface MemberService {
  List<Member> list() throws Exception;
  List<Member> search(String keyword) throws Exception;
  Member get(int no) throws Exception;
  void insert(Member board) throws Exception;
  void update(Member board) throws Exception;
  void delete(int no) throws Exception;
}










