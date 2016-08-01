package com.elminster.retrieve.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;

import com.elminster.common.retrieve.RetrieveException;
import com.elminster.retrieve.web.data.Method;

/**
 * The default web retriever.
 * 
 * @author jgu
 * @version 1.0
 */
public class DefaultWebRetriever extends WebRetriever {

  public DefaultWebRetriever(String url, Method method) {
    super(url, method);
  }

  @Override
  protected void configHttpMethod(HttpClient client, HttpMethod httpMethod) throws RetrieveException {
  }

}
