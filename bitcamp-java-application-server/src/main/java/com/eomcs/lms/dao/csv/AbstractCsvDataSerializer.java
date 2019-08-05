package com.eomcs.lms.dao.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class AbstractCsvDataSerializer<T,K> {
  
  // 서브 클래스에서 저장된 데이터를 조회할 수 있도록 하기 위해 접근 범위를 protected로 한다.
  protected ArrayList<T> list = new ArrayList<>();
  
  // 내부에서만 사용할 필드이기 때문에, 외부에서는 사용해서는 안되는 필드이기 때문에 private으로 한다.
  private File file;
  
  public AbstractCsvDataSerializer(String file) {
    this.file = new File(file);
  }
  
  @SuppressWarnings("unchecked")
  protected void loadData() throws IOException, ClassNotFoundException {
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(new FileInputStream(file)))) {
      
      while ((String line = in.readLine()) != null) {
        
      }
      
      //list = (ArrayList<T>) in.readObject();
    }
  }
  
  protected void saveData() throws FileNotFoundException, IOException {
    try (
      ObjectOutputStream out = new ObjectOutputStream(
          new FileOutputStream(file))) {
      out.writeObject(list);
    }
  }
  
  // 서브 클래스에서 특정 값을 가지고 데이터를 찾는 기능을 반드시 구현하도록 강제하자!
  public abstract int indexOf(K key);
}








