package com.eomcs.lms.dao.csv;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public abstract class AbstractCsvDataSerializer<T,K> {
  
  // 서브 클래스에서 저장된 데이터를 조회할 수 있도록 하기 위해 접근 범위를 protected로 한다.
  protected ArrayList<T> list = new ArrayList<>();
  
  // 내부에서만 사용할 필드이기 때문에, 외부에서는 사용해서는 안되는 필드이기 때문에 private으로 한다.
  private File file;
  
  public AbstractCsvDataSerializer(String file) {
    this.file = new File(file);
  }
  
  protected void loadData() throws IOException, ClassNotFoundException {
    
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(new FileInputStream(file)))) {
      
      String line = null;
      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");
        T obj = createObject(values);
        list.add(obj);
      }
    }
  }
  
  protected void saveData() throws FileNotFoundException, IOException {
    try (PrintStream out = new PrintStream(
        new BufferedOutputStream(new FileOutputStream(file)))) {
      
      for (T obj : list) {
        out.println(createCSV(obj));
      }
    }
  }
  
  // 서브 클래스에게 문자열 배열에 대해 객체를 생성하는 책임을 넘긴다.
  // => 위의 loadData() 메서드에서 기본 기능을 정의하고, 
  //    특정 기능에 대해서는  
  //    서브 클래스에서 완성하도록 유도하는 설계 기법을 
  //    "template method" 패턴이라 부른다.
  //
  protected abstract T createObject(String[] values);
  protected abstract String createCSV(T obj);
  
  
  // 서브 클래스에서 특정 값을 가지고 데이터를 찾는 기능을 반드시 구현하도록 강제하자!
  public abstract int indexOf(K key);
}








