package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;

@Component("/photoboard/delete")
public class PhotoBoardDeleteController {
  
  @Resource private PlatformTransactionManager txManager;
  @Resource private PhotoBoardDao photoBoardDao;
  @Resource private PhotoFileDao photoFileDao;
  
  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      
      if (photoBoardDao.findBy(no) == null) {
        throw new Exception("해당 데이터가 없습니다.");
      }
      
      photoFileDao.deleteAll(no);
      photoBoardDao.delete(no);
      
      txManager.commit(status);
      return "redirect:list";
      
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
}
