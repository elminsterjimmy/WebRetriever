package com.elminster.retrieve.web.data;

import org.apache.commons.httpclient.Header;

/**
 * The HTTP response.
 * 
 * @author jgu
 * @version 1.0
 */
public class Response {

  private Header[] headers;
  private String body;
  private Header[] footers;
  
  /**
   * @return the headers
   */
  public Header[] getHeaders() {
    return headers;
  }
  /**
   * @param headers the headers to set
   */
  public void setHeaders(Header[] headers) {
    this.headers = headers;
  }
  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }
  /**
   * @param body the body to set
   */
  public void setBody(String body) {
    this.body = body;
  }
  /**
   * @return the footers
   */
  public Header[] getFooters() {
    return footers;
  }
  /**
   * @param footers the footers to set
   */
  public void setFooters(Header[] footers) {
    this.footers = footers;
  }
}
