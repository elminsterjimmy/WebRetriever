package com.elminster.retrieve.web.exception;

import com.elminster.common.retrieve.RetrieveException;
import com.elminster.common.util.StringUtil;

public class WebRetrieveException extends RetrieveException {

  /**
   * 
   */
  private static final long serialVersionUID = -8418776053838953971L;

  public WebRetrieveException(String url, String message) {
    super(getUrlInfo(url).append(message).toString());
  }
  
  public WebRetrieveException(String url, Throwable e) {
    super(getUrlInfo(url).toString(), e);
  }

  private static StringBuilder getUrlInfo(String url) {
    if (StringUtil.isEmpty(url)) {
      url = "Unknown URL";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Exception on retrieve URL: [").append(url).append("]. ");
    return sb;
  }
}
