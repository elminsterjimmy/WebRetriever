package com.elminster.retrieve.web.data;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;

import com.elminster.common.retrieve.RetrieveException;

/**
 * The HTTP response.
 * 
 * @author jgu
 * @version 1.0
 */
public class Response {
  
  private HttpMethod httpMethod;
  
  public Response(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }
  
  /**
   * @return the headers
   */
  public Header[] getHeaders() {
    return httpMethod.getResponseHeaders();
  }
  /**
   * @return the body
   * @throws IOException 
   */
  public String getBody() throws RetrieveException {
    try {
      return httpMethod.getResponseBodyAsString();
    } catch (IOException e) {
      throw new RetrieveException(e);
    }
  }
  /**
   * @return the body as inputstream
   * @throws IOException 
   */
  public InputStream getBodyAsInputStream() throws RetrieveException {
    try {
      return httpMethod.getResponseBodyAsStream();
    } catch (IOException e) {
      throw new RetrieveException(e);
    }
  }
  /**
   * @return the body as byte array
   * @throws IOException 
   */
  public byte[] getBodyAsByteArray() throws RetrieveException {
    try {
      return httpMethod.getResponseBody();
    } catch (IOException e) {
      throw new RetrieveException(e);
    }
  }
  /**
   * @return the footers
   */
  public Header[] getFooters() {
    return httpMethod.getResponseFooters();
  }
}
