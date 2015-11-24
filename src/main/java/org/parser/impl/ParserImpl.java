package org.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parser.Parser;
import org.parser.ParsingResult;
import org.parser.ReportData;

/**
 * Created by OrestO on 24.11.2015.
 */
public class ParserImpl implements Parser
{

  private Map<String, ReportData> threadToDataMap;
  private ParsingResult parsingResult;

  // document, page
  private static final Pattern startRenderingPattern = Pattern.compile("(Executing request startRendering)");
  // unique id, start
  private static final Pattern startRenderingReturnPattern = Pattern.compile("(Service startRendering returned)");
  // get
  private static final Pattern getRenderingPattern = Pattern.compile("(Executing request getRendering)");

  public static String[] search(String text)
  {
    Matcher matcher = startRenderingPattern.matcher(text);
    String[] result = new String[matcher.groupCount()];
    for (int i = 0; i < matcher.groupCount(); i++)
    {
      result[i] = matcher.group(i);
    }
    return result;
  }

  @Override
  public ParsingResult parse(Reader reader)
  {
    BufferedReader bufferedReader = new BufferedReader(reader);
    threadToDataMap = new HashMap<>();
    parsingResult = new ParsingResultImpl();
    try
    {
      String currentLine;
      while (null != (currentLine = bufferedReader.readLine()))
      {
        // startRendering was found
        if (startRenderingPattern.matcher(currentLine).find())
        {
          processStartRendering(currentLine);
        }
        // startRendering return was found
        else if (startRenderingReturnPattern.matcher(currentLine).find())
        {
          processStartRenderingReturn(currentLine);
        }
        // getRendering was found
        else if (getRenderingPattern.matcher(currentLine).find())
        {
          processGetRendering(currentLine);
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    // TODO
    return null;
  }


  private static final Pattern timestampPattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");
  private static final Pattern threadPattern = Pattern.compile("(WorkerThread-\\d+)");
  private static final Pattern argumentsPattern = Pattern.compile("(\\d+, \\d+)");
  private static final Pattern uidPattern = Pattern.compile("(\\d+-\\d{4})");

  // 2010-10-06 09:13:56,091 [WorkerThread-19] INFO  [ServiceProvider]: Executing request startRendering with arguments [114274, 0]
  private ReportData processStartRendering(String line)
  {
    String timestamp = timestampPattern.matcher(line).group();
    String thread = threadPattern.matcher(line).group();
    String[] arguments = argumentsPattern.matcher(line).group().split("[, ]");
    String document = arguments[0];
    String page = arguments[1];
    //TODO: Only if new verify check also verify what should be done if not new
    if(threadToDataMap.containsKey(thread))
    {
      parsingResult.getSummaryData().setDuplicates(parsingResult.getSummaryData().getDuplicates() + 1);
    }
    else
    {
      ReportData reportData = new ReportDataImpl();
      reportData.setDocument(document);
      reportData.setPage(page);
      reportData.setStart();
    }
  }

  // 2010-10-06 09:13:56,095 [WorkerThread-19] INFO  [ServiceProvider]: Service startRendering returned 1286374436094-8052
  private ReportData processStartRenderingReturn(String line)
  {
    String timestamp = timestampPattern.matcher(line).group();
    String thread = threadPattern.matcher(line).group();
    String uid = uidPattern.matcher(line).group();

  }

  // 2010-10-06 09:02:11,550 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373729338-5317]
  private ReportData processGetRendering(String line)
  {
    String timestamp = timestampPattern.matcher(line).group();
    String thread = threadPattern.matcher(line).group();
    String uid = uidPattern.matcher(line).group();
  }
}
