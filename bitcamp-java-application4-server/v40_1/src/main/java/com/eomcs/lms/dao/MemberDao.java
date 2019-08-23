package com.eomcs.lms.dao;

import java.util.List;
import com.eomcs.lms.domain.Member;

// 회원 관리 DAO의 사용 규칙을 정의한다.
public interface MemberDao {
  int insert(Member member) throws Exception;
  List<Member> findAll() throws Exception;
  Member findBy(int no) throws Exception;
  List<Member> findByKeyword(String keyword) throws Exception;
  int update(Member member) throws Exception;
  int delete(int no) throws Exception;
}








