package com.eomcs.lms.web.json;

public class JsonResult {
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  
  private String state;
  private String message;
  private Object result;
  
  @Override
  public String toString() {
    return "JsonResult [state=" + state + ", message=" + message + ", result=" + result + "]";
  }
  
  public String getState() {
    return state;
  }
  public JsonResult setState(String state) {
    this.state = state;
    return this;
  }
  public String getMessage() {
    return message;
  }
  public JsonResult setMessage(String message) {
    this.message = message;
    return this;
  }
  public Object getResult() {
    return result;
  }
  public JsonResult setResult(Object result) {
    this.result = result;
    return this;
  }
}
