package ch22.c.ex2.byte_stream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataInputStream extends FileInputStream {

  public DataInputStream(String name) throws FileNotFoundException {
    super(name);
  }
  
  public int readInt() throws IOException {
    int value = 0;
    value |= read() << 24;
    value |= read() << 16;
    value |= read() << 8;
    value |= read();
    return value;
  }
  
  public short readShort() throws IOException {
    short value = 0;
    value |= read() << 8;
    value |= read();
    return value;
  }
  
  public long readLong() throws IOException {
    long value = 0;
    value |= read() << 56;
    value |= read() << 48;
    value |= read() << 40;
    value |= read() << 32;
    value |= read() << 24;
    value |= read() << 16;
    value |= read() << 8;
    value |= read();
    return value;
  }
  
  public boolean readBoolean() throws IOException {
    return read() == 1 ? true : false;
  }
  
  public String readUTF() throws IOException {
    
  }
  
}




    
    
    
    