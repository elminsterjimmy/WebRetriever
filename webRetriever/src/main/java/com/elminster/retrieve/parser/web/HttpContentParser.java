package com.elminster.retrieve.parser.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.parser.IStringParser;
import com.elminster.common.parser.ParseException;
import com.elminster.common.util.StringUtil;

/**
 * Base Http parser.
 * 
 * @author jgu
 * @version 1.0
 * @param <T> the parsed type
 */
abstract public class HttpContentParser<T> implements IStringParser<T> {
  /** the logger. **/
  protected static final Log logger = LogFactory.getLog(HttpContentParser.class);
  
  /**
   * @see com.elminster.common.parser.IParser#parse(java.lang.String)
   */
  @Override
  public T parse(String httpContent) throws ParseException {
    if (logger.isDebugEnabled()) {
      logger.debug(httpContent);
    }
    // fix some illegal syntax
    HtmlCleaner cleaner = new HtmlCleaner();
    TagNode tagNode = cleaner.clean(httpContent);
    Document doc = null;
    try {
      doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
    } catch (Exception e) {
      throw new ParseException("Failed to create DOM.");
    }
    return parseDoc(doc);
  }

  /**
   * Parse the transformed HTTP document.
   * @param doc the HTTP document
   * @return 
   * @throws ParseException on error
   */
  abstract protected T parseDoc(Document doc) throws ParseException;
  
  /**
   * Tidy up the span text by removing spaces, new lines from its both head and tail.
   * @param spanText the span text
   * @return the tidy text
   */
  protected String tidyUpSpanText(final String spanText) {
    String rtn = spanText;
    if (null != spanText) {
      // trim both
      rtn = StringUtil.chompTrim(spanText);
      // remove first and last " if available
      if (rtn.length() >= 2) {
        char first = rtn.charAt(0);
        char last = rtn.charAt(rtn.length() - 1);
        if (CharacterConstants.DOUBLE_QUOTE == first && CharacterConstants.DOUBLE_QUOTE == last) {
          rtn = StringUtil.chompTrim(rtn.substring(1, rtn.length() - 1));
        }
      }
    }
    return rtn;
  }
  
  /**
   * Parse a String to integer.
   * @param str the String
   * @param failMsg the message will be logged if failed
   * @return the parsed integer
   */
  protected Integer parseString2Integer(String str, String failMsg) {
    Integer rtn = 0;
    try {
      rtn = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
      logger.warn(failMsg + "Caused by: " + nfe);
    }
    return rtn;
  }

}
