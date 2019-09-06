package ch23.k;

import java.io.File;
import java.net.URLDecoder;

public class Test01 {

  public static void main(String[] args) throws Exception {
    String url = "/aaa/okok/a%20%20%23%20%21.gif";
    System.out.println(URLDecoder.decode(url, "UTF-8"));
    
    File f = new File("./webroot", URLDecoder.decode(url, "UTF-8"));
    System.out.println(f.getCanonicalPath());

  }

}
