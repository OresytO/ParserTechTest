package org.parser;

import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.junit.Test;
import org.parser.impl.ParsingResultImpl;
import org.parser.impl.ReportBuilderXml;

/**
 * Created by OrestO on 25.11.2015.
 */
public class ReportBuilderTest {

  @Test
  public void firstTest()
  {
    ReportBuilder builder = new ReportBuilderXml();

    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +
                      "<rendering>\r\n" +
                      "<document>114466</document>\r\n" +
                      "<page>0</page>\r\n" +
                      "<uid>1286373733634-5423</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "</rendering>\r\n" +
                      "<summary>\r\n" +
                      "<count>1</count>\r\n" +
                      "<duplicates>0</duplicates>\r\n" +
                      "<unnecessary>0</unnecessary>\r\n" +
                      "</summary>\r\n" +
                      "</report>\r\n";

    ParsingResult parsingResult = new ParsingResultImpl();
    //TODO: createTest
    StringWriter stringWriter = new StringWriter(150);

    builder.getReport(parsingResult, new StreamResult(stringWriter));
    Assert.assertEquals(expected, stringWriter.toString());
  }

  @Test
  public void secondTest()
  {
    ReportBuilder builder = new ReportBuilderXml();

    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +
                      "<rendering>\r\n" +
                      "<document>114466</document>\r\n" +
                      "<page>0</page>\r\n" +
                      "<uid>1286373733634-5423</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "</rendering>\r\n" +
                      "<summary>\r\n" +
                      "<count>1</count>\r\n" +
                      "<duplicates>0</duplicates>\r\n" +
                      "<unnecessary>0</unnecessary>\r\n" +
                      "</summary>\r\n" +
                      "</report>\r\n";

    ParsingResult parsingResult = new ParsingResultImpl();
    //TODO: createTest
    StringWriter stringWriter = new StringWriter(150);

    builder.getReport(parsingResult, new StreamResult(stringWriter));
    Assert.assertEquals(expected, stringWriter.toString());
  }
}
