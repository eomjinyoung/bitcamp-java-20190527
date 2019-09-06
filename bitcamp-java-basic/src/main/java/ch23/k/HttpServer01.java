// Apache HttpClient 라이브러리를 이용하여 웹서버 만들기 
package ch23.k;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestHandler;

public class HttpServer01 {
  
  public static void main(String[] args) throws Exception {
    SocketConfig socketConfig = SocketConfig.custom()
        .setSoTimeout(15000)
        .setTcpNoDelay(true)
        .build();

    final HttpServer server = ServerBootstrap.bootstrap()
        .setListenerPort(8888)
        .setServerInfo("Bitcamp/1.1")
        .setSocketConfig(socketConfig)
        .setSslContext(null)
        .setExceptionLogger(new StdErrorExceptionLogger())
        .registerHandler("*", new HttpFileHandler("./webroot"))
        .create();

    server.start();
    System.out.println("서버 실행 중...");
    
    server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        server.shutdown(5, TimeUnit.SECONDS);
      }
    });
  }

  static class StdErrorExceptionLogger implements ExceptionLogger {

    @Override
    public void log(final Exception ex) {
      if (ex instanceof SocketTimeoutException) {
        System.err.println("Connection timed out");
      } else if (ex instanceof ConnectionClosedException) {
        System.err.println(ex.getMessage());
      } else {
        ex.printStackTrace();
      }
    }

  }

  static class HttpFileHandler implements HttpRequestHandler  {

    private final String docRoot;

    public HttpFileHandler(final String docRoot) {
      super();
      this.docRoot = docRoot;
    }

    public void handle(
        final HttpRequest request,
        final HttpResponse response,
        final HttpContext context) throws HttpException, IOException {

      // 클라이언트가 요청한 방식을 알아 낸다.
      String method = request.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
      if (!method.equals("GET")) { // GET 요청이 아니라면 오류 내용을 응답한다.
        throw new MethodNotSupportedException(method + " method not supported");
      }
      
      String target = request.getRequestLine().getUri();

      final File file = new File(this.docRoot, URLDecoder.decode(target, "UTF-8"));
      if (!file.exists()) {

        response.setStatusCode(HttpStatus.SC_NOT_FOUND);
        StringEntity entity = new StringEntity(
            "<html><body><h1>File" + file.getPath() +
            " not found</h1></body></html>",
            ContentType.create("text/html", "UTF-8"));
        response.setEntity(entity);
        System.out.println("File " + file.getPath() + " not found");

      } else if (!file.canRead() || file.isDirectory()) {

        response.setStatusCode(HttpStatus.SC_FORBIDDEN);
        StringEntity entity = new StringEntity(
            "<html><body><h1>Access denied</h1></body></html>",
            ContentType.create("text/html", "UTF-8"));
        response.setEntity(entity);
        System.out.println("Cannot read file " + file.getPath());

      } else {
        HttpCoreContext coreContext = HttpCoreContext.adapt(context);
        HttpConnection conn = coreContext.getConnection(HttpConnection.class);
        response.setStatusCode(HttpStatus.SC_OK);
        FileEntity body = new FileEntity(file, ContentType.create("text/html", (Charset) null));
        response.setEntity(body);
        System.out.println(conn + ": serving file " + file.getPath());
      }
    }

  }

}