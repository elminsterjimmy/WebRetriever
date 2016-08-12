package com.elminster.retrieve.web.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.config.CommonConfiguration;
import com.elminster.common.util.ExceptionUtil;

public class Configuration extends CommonConfiguration {

  private static final Log logger = LogFactory.getLog(Configuration.class);
  private static final String CONFIG_NAME = "web-retrieve.properties";
  
  public static final BooleanKey PROXY_ENABLE = new BooleanKey("httpclient.proxy.enable", false);
  public static final String PROXY_HOST = "httpclient.proxy.host";
  public static final String PROXY_PORT = "httpclient.proxy.port";
  public static final BooleanKey PROXY_CREDENTIALS_REQUIRED = new BooleanKey("httpclient.proxy.credentials.required", false);
  public static final String PROXY_CREDENTIALS_AUTH_SCOPE = "httpclient.proxy.credentials.auth.scope";
  public static final String PROXY_CREDENTIALS_USERNAME = "httpclient.proxy.credentials.username";
  public static final String PROXY_CREDENTIALS_PASSWORD = "httpclient.proxy.credentials.password";

  public static final Configuration INSTANCE = new Configuration();
  
  @Override
  protected void loadResources() {
    super.loadResources();
    File configFile = new File(CONFIG_NAME);
    if (!configFile.exists()) {
      logger.warn(String.format("[%s] is not found, use default configuration.", CONFIG_NAME));
    } else {
      try (InputStream fis = new FileInputStream(configFile)) {
        properties.load(fis);
      } catch (IOException e) {
        logger.error(String.format("Failed to load [%s]. Cause [%s].", CONFIG_NAME, ExceptionUtil.getFullStackTrace(e)));
      }
    }
  }
}
