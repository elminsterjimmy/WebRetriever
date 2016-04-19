package com.elminster.retrieve.web;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.elminster.common.retrieve.RetrieveException;
import com.elminster.retrieve.web.cookie.PermitAllCookiesSpec;
import com.elminster.retrieve.web.data.Method;
import com.elminster.retrieve.web.exception.WebRetrieveException;

/**
 * The retriever auto inject cookies.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class CookieInjectRetriever extends WebRetriever {

  /**
   * Constructor.
   * 
   * @param url
   *          the url
   * @param method
   *          the HTTP method (GET, PUT, POST, DELETE)
   */
  public CookieInjectRetriever(String url, Method method) {
    super(url, method);
  }

  /**
   * @see com.elminster.retrieve.web.WebRetriever#configHttpClient(org.apache.commons.httpclient.HttpClient)
   */
  @Override
  protected void configHttpClient(HttpClient client) throws RetrieveException {
    Cookie[] cookies;
    try {
      cookies = readCookies();
      injectLoginCookies(client, cookies);
    } catch (Exception e) {
      throw new WebRetrieveException(url, "Failed to inject cookies. Caused by: " + e);
    }
  }

  /**
   * Inject the login cookies into the http client.
   * 
   * @param client
   *          the http client
   * @throws IOException
   *           on error
   */
  protected void injectLoginCookies(HttpClient client, Cookie[] cookies) throws IOException {
    client.getState().addCookies(cookies);

    CookiePolicy.registerCookieSpec("PermitAllCookiesSpec", PermitAllCookiesSpec.class);
    client.getParams().setCookiePolicy("PermitAllCookiesSpec");
  }

  /**
   * Read the cookies.
   * 
   * @return the cookies
   * @throws IOException
   *           on error
   */
  abstract protected Cookie[] readCookies() throws Exception;

  /**
   * Create a new cookie with domain, name, value, path and expires.
   * 
   * @param domain
   *          the domain
   * @param name
   *          the name
   * @param value
   *          the value
   * @param path
   *          the path
   * @param expires
   *          the expires
   * @return a new cookie
   */
  protected Cookie createCookie(String domain, String name, String value, String path, long expires) {
    Cookie cookie = new Cookie();
    cookie.setDomain(domain);
    cookie.setName(name);
    cookie.setValue(value);
    cookie.setPath(path);
    cookie.setExpiryDate(new Date(System.currentTimeMillis() + expires));
    return cookie;
  }
}
