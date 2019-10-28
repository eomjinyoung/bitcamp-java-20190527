package com.eomcs.lms.service;

import java.util.List;
import com.eomcs.lms.domain.Lesson;

// 역할:
// => 수업 관리 업무를 수행
// => 트랜잭션 제어
// => 여러 페이지 컨트롤러가 사용한다.
//
public interface LessonService {
  List<Lesson> list() throws Exception;
  Lesson get(int no) throws Exception;
  void insert(Lesson lesson) throws Exception;
  void update(Lesson lesson) throws Exception;
  void delete(int no) throws Exception;
}










