package com.eomcs.lms.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.PhotoBoardService;

@Service
public class DefaultPhotoBoardService implements PhotoBoardService {

  @Resource private PhotoBoardDao photoBoardDao;
  @Resource private PhotoFileDao photoFileDao;

  @Transactional
  @Override
  public void insert(PhotoBoard photoBoard) throws Exception {
    if (photoBoard.getFiles().size() == 0) {
      throw new Exception("사진 파일 없음!");
    }
    photoBoardDao.insert(photoBoard);
    for (PhotoFile file : photoBoard.getFiles()) {
      // 사진 파일 데이터를 저장하기 전에,
      // 이전에 저장한 사진 게시물 번호를 먼저 설정한다.
      // 사진 파일 데이터를 저장할 때 이 게시물 번호를 사용하기 때문이다.
      file.setBoardNo(photoBoard.getNo()); 
      photoFileDao.insert(file);
    }
  }

  @Transactional
  @Override
  public void delete(int no) throws Exception {
    if (photoBoardDao.findBy(no) == null) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    photoFileDao.deleteAll(no);
    photoBoardDao.delete(no);
  }

  @Override
  public PhotoBoard get(int no) throws Exception {
    PhotoBoard photoBoard = photoBoardDao.findWithFilesBy(no);
    if (photoBoard == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }
    photoBoardDao.increaseViewCount(no);
    return photoBoard;
  }

  @Override
  public List<PhotoBoard> list() throws Exception {
    return photoBoardDao.findAll();
  }

  @Transactional
  @Override
  public void update(PhotoBoard photoBoard) throws Exception {
    if (photoBoard.getFiles().size() == 0) {
      throw new Exception("사진 파일 없음!");
    }

    photoBoardDao.update(photoBoard);
    photoFileDao.deleteAll(photoBoard.getNo());
    for (PhotoFile file : photoBoard.getFiles()) {
      // 사진 파일 데이터를 저장하기 전에,
      // 이전에 저장한 사진 게시물 번호를 먼저 설정한다.
      // 사진 파일 데이터를 저장할 때 이 게시물 번호를 사용하기 때문이다.
      file.setBoardNo(photoBoard.getNo()); 
      photoFileDao.insert(file);
    }
  }
}
