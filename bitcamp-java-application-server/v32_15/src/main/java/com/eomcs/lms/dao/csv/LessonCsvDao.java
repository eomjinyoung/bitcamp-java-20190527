package com.eomcs.lms.dao.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonCsvDao extends AbstractCsvDataSerializer<Lesson,Integer> 
    implements LessonDao {
  
  public LessonCsvDao(String file) {
    super(file);
    
    try {
      loadData();
      System.out.println("수업 데이터 로딩 완료!");
      
    } catch (Exception e) {
      System.out.println("수업 데이터 로딩 중 오류 발생!");
    }
  }
  
  @Override
  public void saveData() {
    try {
      super.saveData();
      System.out.println("수업 데이터 저장 완료!");
      
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }
  
  @Override
  protected Lesson createObject(String[] values) {
    // CSV 형식: 번호,강의명,내용,시작일,종료일,총강의시간,일강의시간
    
    Lesson lesson = new Lesson();
    lesson.setNo(Integer.parseInt(values[0]));
    lesson.setTitle(values[1]);
    lesson.setContents(values[2]);
    lesson.setStartDate(Date.valueOf(values[3]));
    lesson.setEndDate(Date.valueOf(values[4]));
    lesson.setTotalHours(Integer.parseInt(values[5]));
    lesson.setDayHours(Integer.parseInt(values[6]));
    
    return lesson;
  }
  
  @Override
  protected String createCSV(Lesson obj) {
    return String.format("%d,%s,%s,%s,%s,%d,%d", 
        obj.getNo(),
        obj.getTitle(),
        obj.getContents(),
        obj.getStartDate(),
        obj.getEndDate(),
        obj.getTotalHours(),
        obj.getDayHours());
  }
  
  @Override
  public int indexOf(Integer key) {
    int i = 0;
    for (Lesson obj : list) {
      if (obj.getNo() == key) {
        return i;
      }
      i++;
    }
    return -1;
  }
  
  @Override
  public int insert(Lesson lesson) throws Exception {
    list.add(lesson);
    return 1;
  }
  
  @Override
  public List<Lesson> findAll() throws Exception {
    return list;
  }
  
  @Override
  public Lesson findBy(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1)
      return null;
    
    return list.get(index);
  }
  
  @Override
  public int update(Lesson lesson) throws Exception {
    int index = indexOf(lesson.getNo());
    if (index == -1)
      return 0;
    
    list.set(index, lesson);
    return 1;
  }
  
  @Override
  public int delete(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1)
      return 0;
    
    list.remove(index);
    return 1;
  }
}








