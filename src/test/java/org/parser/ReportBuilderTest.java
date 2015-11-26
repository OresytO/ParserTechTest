package org.parser;

import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parser.impl.ParsingResultImpl;
import org.parser.impl.RenderingDataImpl;
import org.parser.impl.ReportBuilderXml;
import org.parser.impl.SummaryDataImpl;
import org.parser.util.Lists;

/**
 * This class contains test to verify that report was built and looks as expected
 * @author Orest Lozynskyy
 */
public class ReportBuilderTest {

  private ReportBuilder builder = new ReportBuilderXml();
  private ParsingResult parsingResult;
  private RenderingData renderingData;
  private StringWriter stringWriter;

  @Before
  public void init()
  {
    parsingResult = new ParsingResultImpl();
    parsingResult.setSummaryData(null);
    renderingData = new RenderingDataImpl();
    stringWriter = new StringWriter(150);
  }

  @Test
  public void verifySummary()
  {
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +
                      "<summary>\r\n" +
                      "<count>1</count>\r\n" +
                      "<duplicates>0</duplicates>\r\n" +
                      "<unnecessary>0</unnecessary>\r\n" +
                      "</summary>\r\n" +
                      "</report>\r\n";

    SummaryData summaryData = new SummaryDataImpl();
    summaryData.setCount(1);
    summaryData.setDuplicates(0);
    summaryData.setUnnecessary(0);
    parsingResult.setSummaryData(summaryData);

    builder.getReport(parsingResult, new StreamResult(stringWriter));

    Assert.assertEquals(expected, stringWriter.toString());
  }

  /**
   * Checks case when we should writes report with single start request and single get request
   */
  @Test
  public void verifyRenderingWithSingleStartAndSingleGet()
  {
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +
                      "<rendering>\r\n" +
                      "<document>114466</document>\r\n" +
                      "<page>0</page>\r\n" +
                      "<uid>1286373733634-5423</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "</rendering>\r\n" +
                      "</report>\r\n";

    renderingData.setDocument("114466");
    renderingData.setPage("0");
    renderingData.setUid("1286373733634-5423");
    renderingData.setStarts(Lists.newArrayList("2010-10-06 09:02:13,631"));
    renderingData.setGets(Lists.newArrayList("2010-10-06 09:06:36,885"));
    parsingResult.setRenderingDatas(Lists.newArrayList(renderingData));

    builder.getReport(parsingResult, new StreamResult(stringWriter));

    Assert.assertEquals(expected, stringWriter.toString());
  }

  /**
   * Checks case when we should writes report with multiple start request and multiple get request
   */
  @Test
  public void verifyRenderingWithMultipleStartAndMultipleGet()
  {
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +
                      //Multiple start and get
                      "<rendering>\r\n" +
                      "<document>114460</document>\r\n" +
                      "<page>1</page>\r\n" +
                      "<uid>1286373733634-5421</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "</rendering>\r\n" +
                      "</report>\r\n";

    renderingData.setDocument("114460");
    renderingData.setPage("1");
    renderingData.setUid("1286373733634-5421");
    renderingData.setStarts(Lists.newArrayList("2010-10-06 09:02:13,631", "2010-10-06 09:02:13,631", "2010-10-06 09:02:13,631"));
    renderingData.setGets(Lists.newArrayList("2010-10-06 09:06:36,885", "2010-10-06 09:06:36,885", "2010-10-06 09:06:36,885"));
    parsingResult.setRenderingDatas(Lists.newArrayList(renderingData));

    builder.getReport(parsingResult, new StreamResult(stringWriter));

    Assert.assertEquals(expected, stringWriter.toString());
  }

  /**
   * Checks case when we should writes report with multiple start request and multiple get request
   */
  @Test
  public void verifyRenderingWithSingleStartAndMultipleGet()
  {
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +
                      //Multiple start and single get
                      "<rendering>\r\n" +
                      "<document>114461</document>\r\n" +
                      "<page>2</page>\r\n" +
                      "<uid>1286373733634-5422</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "</rendering>\r\n" +
                      "</report>\r\n";

    renderingData.setDocument("114461");
    renderingData.setPage("2");
    renderingData.setUid("1286373733634-5422");
    renderingData.setStarts(Lists.newArrayList("2010-10-06 09:02:13,631", "2010-10-06 09:02:13,631", "2010-10-06 09:02:13,631"));
    renderingData.setGets(Lists.newArrayList("2010-10-06 09:06:36,885"));

    builder.getReport(parsingResult, new StreamResult(stringWriter));

    Assert.assertEquals(expected, stringWriter.toString());
  }

  @Test
  public void checkReportWithMultipleRenderings()
  {
    ReportBuilder builder = new ReportBuilderXml();

    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                      "<report>\r\n" +

                      //Single start and multiple get
                      "<rendering>\r\n" +
                      "<document>114462</document>\r\n" +
                      "<page>3</page>\r\n" +
                      "<uid>1286373733634-5423</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "</rendering>\r\n" +


                      //Empty start and single get
                      "<rendering>\r\n" +
                      "<document>114464</document>\r\n" +
                      "<page>5</page>\r\n" +
                      "<uid>1286373733634-5425</uid>\r\n" +
                      "<start/>\r\n" +
                      "<get>2010-10-06 09:06:36,885</get>\r\n" +
                      "</rendering>\r\n" +

                      //Single start and empty get
                      "<rendering>\r\n" +
                      "<document>114465</document>\r\n" +
                      "<page>6</page>\r\n" +
                      "<uid>1286373733634-5426</uid>\r\n" +
                      "<start>2010-10-06 09:02:13,631</start>\r\n" +
                      "<get/>\r\n" +
                      "</rendering>\r\n" +

                      //Empty start and empty get
                      "<rendering>\r\n" +
                      "<document>114466</document>\r\n" +
                      "<page>7</page>\r\n" +
                      "<uid>1286373733634-5427</uid>\r\n" +
                      "<start/>\r\n" +
                      "<get/>\r\n" +
                      "</rendering>\r\n" +

                      "<summary>\r\n" +
                      "<count>7</count>\r\n" +
                      "<duplicates>0</duplicates>\r\n" +
                      "<unnecessary>0</unnecessary>\r\n" +
                      "</summary>\r\n" +
                      "</report>\r\n";

    ParsingResult parsingResult = new ParsingResultImpl();

    RenderingData renderingData2 = new RenderingDataImpl();


    RenderingData renderingData3 = new RenderingDataImpl();
    renderingData3.setDocument("114462");
    renderingData3.setPage("3");
    renderingData3.setUid("1286373733634-5423");
    renderingData3.setStarts(Lists.newArrayList("2010-10-06 09:02:13,631"));
    renderingData3.setGets(Lists.newArrayList("2010-10-06 09:06:36,885", "2010-10-06 09:06:36,885", "2010-10-06 09:06:36,885"));

    RenderingData renderingData5 = new RenderingDataImpl();
    renderingData5.setDocument("114464");
    renderingData5.setPage("5");
    renderingData5.setUid("1286373733634-5425");
    renderingData5.setStarts(Lists.newArrayList(""));
    renderingData5.setGets(Lists.newArrayList("2010-10-06 09:06:36,885"));

    RenderingData renderingData6 = new RenderingDataImpl();
    renderingData6.setDocument("114465");
    renderingData6.setPage("6");
    renderingData6.setUid("1286373733634-5426");
    renderingData6.setStarts(Lists.newArrayList("2010-10-06 09:02:13,631"));
    renderingData6.setGets(Lists.newArrayList(""));

    RenderingData renderingData7 = new RenderingDataImpl();
    renderingData7.setDocument("114466");
    renderingData7.setPage("7");
    renderingData7.setUid("1286373733634-5427");
    renderingData7.setStarts(Lists.newArrayList(""));
    renderingData7.setGets(Lists.newArrayList(""));

    SummaryData summaryData = new SummaryDataImpl();
    summaryData.setCount(7);
    summaryData.setDuplicates(0);
    summaryData.setUnnecessary(0);
    parsingResult.setSummaryData(summaryData);

    StringWriter stringWriter = new StringWriter(150);
    builder.getReport(parsingResult, new StreamResult(stringWriter));

    Assert.assertEquals(expected, stringWriter.toString());
  }
}
