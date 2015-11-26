package org.parser;

import java.io.StringReader;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.parser.impl.*;
import org.parser.util.Lists;

/**
 * Created by OrestO on 25.11.2015.
 */
public class ParserTest {

  @Test
  public void noGetRequests()
  {
    Parser parser = new ParserImpl();
    String toRead = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423";

    ParsingResult parsingResult = parser.parse(new StringReader(toRead));

    RenderingData renderingData = parsingResult.getRenderingDatas().get(0);
    Assert.assertEquals("Verify document", "114466", renderingData.getDocument());
    Assert.assertEquals("Verify page", "0", renderingData.getPage());
    Assert.assertEquals("Verify uid", "1286373733634-5423", renderingData.getUid());
    Assert.assertEquals("Verify starts", Lists.newArrayList("2010-10-06 09:02:13,631"), renderingData.getStarts());
    Assert.assertNotNull("Verify gets", renderingData.getGets());
    Assert.assertEquals("Verify gets", 0, renderingData.getGets().size());

    SummaryData summaryData = parsingResult.getSummaryData();
    Assert.assertEquals("Verify SummaryData count", 1, summaryData.getCount());
    Assert.assertEquals("Verify SummaryData duplicates", 0, summaryData.getDuplicates());
    Assert.assertEquals("Verify SummaryData unnecessary", 1, summaryData.getUnnecessary());
  }

  @Test
  public void singleGetRequest()
  {
    Parser parser = new ParserImpl();
    String toRead = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423\r\n" +
                    "2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]";

    ParsingResult parsingResult = parser.parse(new StringReader(toRead));

    RenderingData renderingData = parsingResult.getRenderingDatas().get(0);
    Assert.assertEquals("Verify document", "114466", renderingData.getDocument());
    Assert.assertEquals("Verify page", "0", renderingData.getPage());
    Assert.assertEquals("Verify uid", "1286373733634-5423", renderingData.getUid());
    Assert.assertEquals("Verify starts", Lists.newArrayList("2010-10-06 09:02:13,631"), renderingData.getStarts());
    Assert.assertEquals("Verify gets", Lists.newArrayList("2010-10-06 09:06:36,885"), renderingData.getGets());

    SummaryData summaryData = parsingResult.getSummaryData();
    Assert.assertEquals("Verify SummaryData count", 1, summaryData.getCount());
    Assert.assertEquals("Verify SummaryData duplicates", 0, summaryData.getDuplicates());
    Assert.assertEquals("Verify SummaryData unnecessary", 0, summaryData.getUnnecessary());
  }

  @Test
  public void multipleGetRequests ()
  {
    Parser parser = new ParserImpl();
    String toRead = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423\r\n" +
                    "2010-10-06 09:06:36,000 [WorkerThread-3] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]\r\n" +
                    "2010-10-06 09:06:36,000 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]\r\n" +
                    "2010-10-06 09:06:35,000 [WorkerThread-5] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]\r\n" +
                    "2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]";

    ParsingResult parsingResult = parser.parse(new StringReader(toRead));

    RenderingData renderingData = parsingResult.getRenderingDatas().get(0);
    Assert.assertEquals("Verify document", "114466", renderingData.getDocument());
    Assert.assertEquals("Verify page", "0", renderingData.getPage());
    Assert.assertEquals("Verify uid", "1286373733634-5423", renderingData.getUid());
    Assert.assertEquals("Verify starts", Lists.newArrayList("2010-10-06 09:02:13,631"), renderingData.getStarts());
    List<String> gets = Lists.newArrayList("2010-10-06 09:06:36,000", "2010-10-06 09:06:36,000", "2010-10-06 09:06:35,000", "2010-10-06 09:06:36,885");
    Assert.assertEquals("Verify gets", gets, renderingData.getGets());

    SummaryData summaryData = parsingResult.getSummaryData();
    Assert.assertEquals("Verify SummaryData count", 1, summaryData.getCount());
    Assert.assertEquals("Verify SummaryData duplicates", 0, summaryData.getDuplicates());
    Assert.assertEquals("Verify SummaryData unnecessary", 0, summaryData.getUnnecessary());
  }

  @Test
  public void multipleRenderingDataWithDuplicationDocumentAndPage()
  {
    Parser parser = new ParserImpl();
    String toRead = "2010-10-06 09:02:13,631 [WorkerThread-1] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-1] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423\r\n" +
                    "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5424\r\n" +
                    "2010-10-06 09:02:13,631 [WorkerThread-3] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]\r\n" +
                    "2010-10-06 09:02:13,634 [WorkerThread-3] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5425\r\n" +
                    "2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5423]\r\n" +
                    "2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5424]\r\n" +
                    "2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373733634-5425]";

    ParsingResult parsingResult = parser.parse(new StringReader(toRead));

    RenderingData renderingData = parsingResult.getRenderingDatas().get(0);
    Assert.assertEquals("Verify document", "114466", renderingData.getDocument());
    Assert.assertEquals("Verify page", "0", renderingData.getPage());
    Assert.assertEquals("Verify uid", "1286373733634-5424", renderingData.getUid());
    Assert.assertEquals("Verify starts", Lists.newArrayList("2010-10-06 09:02:13,631"), renderingData.getStarts());
    List<String> gets = Lists.newArrayList("2010-10-06 09:06:36,885");
    Assert.assertEquals("Verify gets", gets, renderingData.getGets());

    renderingData = parsingResult.getRenderingDatas().get(1);
    Assert.assertEquals("Verify document", "114466", renderingData.getDocument());
    Assert.assertEquals("Verify page", "0", renderingData.getPage());
    Assert.assertEquals("Verify uid", "1286373733634-5425", renderingData.getUid());
    Assert.assertEquals("Verify starts", Lists.newArrayList("2010-10-06 09:02:13,631"), renderingData.getStarts());
    gets = Lists.newArrayList("2010-10-06 09:06:36,885");
    Assert.assertEquals("Verify gets", gets, renderingData.getGets());

    renderingData = parsingResult.getRenderingDatas().get(2);
    Assert.assertEquals("Verify document", "114466", renderingData.getDocument());
    Assert.assertEquals("Verify page", "0", renderingData.getPage());
    Assert.assertEquals("Verify uid", "1286373733634-5423", renderingData.getUid());
    Assert.assertEquals("Verify starts", Lists.newArrayList("2010-10-06 09:02:13,631"), renderingData.getStarts());
    gets = Lists.newArrayList("2010-10-06 09:06:36,885");
    Assert.assertEquals("Verify gets", gets, renderingData.getGets());

    SummaryData summaryData = parsingResult.getSummaryData();
    Assert.assertEquals("Verify SummaryData count", 3, summaryData.getCount());
    Assert.assertEquals("Verify SummaryData duplicates", 2, summaryData.getDuplicates());
    Assert.assertEquals("Verify SummaryData unnecessary", 0, summaryData.getUnnecessary());
  }

  @Test
  public void noResponseToStartFound()
  {
    //TODO:
  }

  @Test
  public void meetResponseWithoutRequest()
  {
    //TODO:
  }


}
