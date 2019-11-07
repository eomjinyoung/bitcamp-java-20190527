package com.eomcs.lms.service;

import java.util.List;
import com.eomcs.lms.domain.PhotoBoard;

public interface PhotoBoardService {
  List<PhotoBoard> list() throws Exception;
  PhotoBoard get(int no) throws Exception;
  void insert(PhotoBoard board) throws Exception;
  void update(PhotoBoard board) throws Exception;
  void delete(int no) throws Exception;
}
