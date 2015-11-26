package org.parser;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.junit.Test;
import org.parser.impl.ParserImpl;
import org.parser.impl.ReportBuilderXml;

/**
 * Created by OrestO on 25.11.2015.
 */
public class ParserTest {

  @Test
  public void firstTest()
  {
    Parser parser = new ParserImpl();
    ReportBuilder builder = new ReportBuilderXml();
    String toRead = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423\r\n" +
                    "2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]";

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

    builder.buildReport(parser.parse(new StringReader(toRead)));
    StringWriter stringWriter = new StringWriter(150);
    builder.getReport(new StreamResult(stringWriter));
    Assert.assertEquals(expected, stringWriter.toString());
  }
}
