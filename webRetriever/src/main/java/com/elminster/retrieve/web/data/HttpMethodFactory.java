package com.elminster.retrieve.web.data;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public class HttpMethodFactory {

  public static final HttpMethodFactory INSTANCE = new HttpMethodFactory();
  
  private HttpMethodFactory() {}
  
  public HttpMethod getHttpMethod(Method method) {
    HttpMethod httpMethod = null;
    switch (method){
      case PUT_METHOD:
        httpMethod = new PutMethod();
        break;
      case POST_METHOD:
        httpMethod = new PostMethod();
        break;
      case GET_METHOD:
        httpMethod = new GetMethod();
        break;
      case DELETE_METHOD:
        httpMethod = new DeleteMethod();
        break;
      default:
        throw new IllegalArgumentException("Unknown method type.");
    }
    return httpMethod;
  }
  
  public void setGlobalProxy() {
    
  }
}
