package com.elminster.retrieve.web;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.retrieve.IRetriever;
import com.elminster.common.retrieve.RetrieveException;
import com.elminster.retrieve.web.conf.Configuration;
import com.elminster.retrieve.web.data.HttpMethodFactory;
import com.elminster.retrieve.web.data.Method;
import com.elminster.retrieve.web.data.Response;
import com.elminster.retrieve.web.exception.WebRetrieveException;

/**
 * The base retriever.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class WebRetriever implements IRetriever<Response> {

  /** the logger. */
  private static final Log logger = LogFactory.getLog(WebRetriever.class);

  /** the url. */
  protected final String url;

  /** the HTTP method used in the retriever. */
  protected final Method method;

  private ProxyHost globalProxyHost;

  private AuthScope globalProxyAuthScope;

  private Credentials globalPorxyCredentials;

  /**
   * Constructor.
   * 
   * @param url
   *          the url
   * @param method
   *          the HTTP method (GET, PUT, POST, DELETE)
   * @param parser
   *          the parser
   */
  public WebRetriever(String url, Method method) {
    this.url = url;
    this.method = method;
    if (Configuration.INSTANCE.getBooleanProperty(Configuration.PROXY_ENABLE)) {
      String host = Configuration.INSTANCE.getStringProperty(Configuration.PROXY_HOST);
      int port = Configuration.INSTANCE.getIntegerProperty(Configuration.PROXY_PORT);
      this.globalProxyHost = new ProxyHost(host, port);
      if (Configuration.INSTANCE.getBooleanProperty(Configuration.PROXY_CREDENTIALS_REQUIRED)) {
//        String scope = Configuration.INSTANCE.getStringProperty(Configuration.PROXY_CREDENTIALS_AUTH_SCOPE);
        String username = Configuration.INSTANCE.getStringProperty(Configuration.PROXY_CREDENTIALS_USERNAME);
        String password = Configuration.INSTANCE.getStringProperty(Configuration.PROXY_CREDENTIALS_PASSWORD);
        globalProxyAuthScope = AuthScope.ANY;
        globalPorxyCredentials = new UsernamePasswordCredentials(username, password);
      }
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Response retrieve() throws RetrieveException {
    try {
      HttpClient client = new HttpClient();
      configHttpClient(client);
      HttpMethod httpMethod = HttpMethodFactory.INSTANCE.getHttpMethod(method);
      httpMethod.setURI(new URI(url, false));
      configHttpMethod(client, httpMethod);
      if (logger.isInfoEnabled()) {
        logger.info("Starting calling url [" + url + "].");
      }
      if (logger.isDebugEnabled()) {
        logger.debug("The calling headers: ");
        Header[] headers = httpMethod.getRequestHeaders();
        for (Header header : headers) {
          logger.debug(header.toString());
        }
      }
      client.executeMethod(httpMethod);
      int status = httpMethod.getStatusCode();
      if (200 != status) {
        dumpResponse(httpMethod);
        throw new WebRetrieveException(url, "Status fails. Status = " + status);
      } else {
        if (logger.isInfoEnabled()) {
          logger.info("Ending calling url [" + url + "].");
        }
        return new Response(httpMethod);
      }
    } catch (IOException e) {
      throw new WebRetrieveException(url, e);
    }
  }
  
  public void setProxyWithCredentials(final ProxyHost proxyHost, final AuthScope authscope, 
      final Credentials credentials) {
    this.globalProxyHost = proxyHost;
    this.globalProxyAuthScope = authscope;
    this.globalPorxyCredentials = credentials;
  }
  
  public void setProxy(final ProxyHost proxyHost) {
    this.globalProxyHost = proxyHost;
  }
  
  private void dumpResponse(HttpMethod httpMethod) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("dump response()");
      logger.debug("dump response headers");
      Header[] responseHeaders = httpMethod.getResponseHeaders();
      if (null != responseHeaders) {
        for (Header header : responseHeaders) {
          logger.debug(header.getName() + ":" + header.getValue());
        }
      }
      logger.debug("dump response body");
      logger.debug(httpMethod.getResponseBodyAsString());
      logger.debug("dump response footers");
      Header[] responseFooterss = httpMethod.getResponseFooters();
      if (null != responseFooterss) {
        for (Header footer : responseFooterss) {
          logger.debug(footer.getName() + ":" + footer.getValue());
        }
      }
    }
  }
  
  /**
   * Config the Http method.
   * @param client the Http client
   * @param httpMethod the Http method
   */
  abstract protected void configHttpMethod(HttpClient client, HttpMethod httpMethod) throws RetrieveException;

  /**
   * Config the Http client.
   * @param client the Http client
   */
  protected void configHttpClient(HttpClient client) throws RetrieveException {
    if (null != globalProxyHost) {
      client.getHostConfiguration().setProxyHost(globalProxyHost);
      if (null != globalProxyAuthScope) {
        client.getState().setProxyCredentials(globalProxyAuthScope, globalPorxyCredentials);
      }
    }
  }
}
