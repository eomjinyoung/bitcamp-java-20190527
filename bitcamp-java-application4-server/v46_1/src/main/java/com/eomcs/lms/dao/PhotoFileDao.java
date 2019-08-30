package com.eomcs.lms.dao;

import java.util.List;
import com.eomcs.lms.domain.PhotoFile;

// DAO 사용 규칙을 정의한다.
public interface PhotoFileDao {
  int insert(PhotoFile photoFile) throws Exception;
  List<PhotoFile> findAll(int boardNo) throws Exception;
  int deleteAll(int boardNo) throws Exception;
}








