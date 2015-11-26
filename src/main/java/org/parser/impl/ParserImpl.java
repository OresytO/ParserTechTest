package org.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parser.Parser;
import org.parser.ParsingResult;
import org.parser.RenderingData;
import org.parser.SummaryData;
import org.parser.util.Lists;

/**
 * Created by OrestO on 24.11.2015.
 */
public class ParserImpl implements Parser
{

  private Set<String> uniqueDocumentsPages = new HashSet<>();
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
    uniqueDocumentsPages = new HashSet<>();
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
    parsingResult.setRenderingDatas(Lists.newArrayList(uidToRenderingMap.values()));
    SummaryData summaryData = parsingResult.getSummaryData();
    summaryData.setCount(uidToRenderingMap.size());
    summaryData.setUnnecessary((int) uidToRenderingMap.values().stream().filter(i -> i.getGets().isEmpty()).count());
    return parsingResult;
  }


  private static final Pattern timestampPattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");
  private static final Pattern threadPattern = Pattern.compile("(WorkerThread-\\d+)");
  private static final Pattern argumentsPattern = Pattern.compile("(\\d+, \\d+)");
//  private static final Pattern uidPattern = Pattern.compile("(\\d+-\\d+)");
  //2010-10-06 09:06:30,046 [WorkerThread-8] INFO  [ServiceProvider]: Service startRendering returned 1286373990046-389
  private static final Pattern uidReturnPattern = Pattern.compile("Service startRendering returned (\\d+-\\d+)");
  private static final Pattern uidGetPattern = Pattern.compile("Executing request getRendering with arguments \\[(\\d+-\\d+)\\]");

  private final Map<String, RenderingData> threadPool = new HashMap<>();
  private final Map<String, RenderingData> uidToRenderingMap = new HashMap<>();

  // 2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0]
  private void processStartRendering(String line)
  {
    Matcher timestampMatcher = timestampPattern.matcher(line);
    String timestamp = timestampMatcher.find() ? timestampMatcher.group(1) : "";

    Matcher threadMatcher = threadPattern.matcher(line);
    String thread = threadMatcher.find() ? threadMatcher.group(1) : "";

    Matcher argumentsMatcher = argumentsPattern.matcher(line);
    String rawArguments = argumentsMatcher.find() ? argumentsMatcher.group(1) : "";
    
    String[] arguments = rawArguments.split(", ");
    String document = arguments[0];
    String page = arguments[1];

    // This is a duplicate request of startRendering the document and the page was rendered before
    if(!uniqueDocumentsPages.add(document + "_" + page))
    {
      parsingResult.getSummaryData().incrementDuplicates();
    }

    //TODO: Only if new verify check also verify what should be done if not new
    if(threadPool.containsKey(thread))
    {
//      parsingResult.getSummaryData().setDuplicates(parsingResult.getSummaryData().getDuplicates() + 1);
    }
    else
    {
      RenderingData renderingData = new RenderingDataImpl();
      renderingData.setDocument(document);
      renderingData.setPage(page);
      renderingData.addStart(timestamp);
      threadPool.put(thread, renderingData);
    }
  }

  // 2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423
  private void processStartRenderingReturn(String line)
  {
    Matcher timestampMatcher = timestampPattern.matcher(line);
    String timestamp = timestampMatcher.find() ? timestampMatcher.group(1) : "";

    Matcher threadMatcher = threadPattern.matcher(line);
    String thread = threadMatcher.find() ? threadMatcher.group(1) : "";

    Matcher uidMatcher = uidReturnPattern.matcher(line);
    String uid = uidMatcher.find() ? uidMatcher.group(1) : "";

    if (threadPool.containsKey(thread))
    {
      RenderingData renderingData = threadPool.remove(thread);
      if (uidToRenderingMap.containsKey(uid))
      {

        RenderingData previousResult = uidToRenderingMap.get(uid);
        String previousDocument = previousResult.getDocument();
        String previousPage = previousResult.getPage();

        if (previousDocument != null
            && previousPage != null
            && previousDocument.equals(renderingData.getDocument())
            && previousPage.equals(renderingData.getPage()))
        {
          previousResult.addStarts(renderingData.getStarts());
        }
      }
      else
      {
        renderingData.setUid(uid);
        uidToRenderingMap.put(uid, renderingData);
      }
    }
    else
    {
      // TODO: Check this
      // Case when previous request startRendering not in current file
      // We can make statistic but without document, page info
    }
  }

  // 2010-10-06 09:06:36,885 [WorkerThread-2] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373996614-6248]
  private void processGetRendering(String line)
  {
    Matcher timestampMatcher = timestampPattern.matcher(line);
    String timestamp = timestampMatcher.find() ? timestampMatcher.group(1) : "";

    Matcher threadMatcher = threadPattern.matcher(line);
    String thread = threadMatcher.find() ? threadMatcher.group(1) : "";

    Matcher uidMatcher = uidGetPattern.matcher(line);
    String uid = uidMatcher.find() ? uidMatcher.group(1) : "";

    if (uidToRenderingMap.containsKey(uid))
    {
      RenderingData renderingData = uidToRenderingMap.get(uid);
      if (renderingData != null)
      {
        renderingData.addGet(timestamp);
      }
    }
    else
    {
      // TODO: Check this
      // Case when previous return for startRendering not in current file
      // We can make statistic but without document, page info
    }
  }

  private static boolean isDocumentAndPageSame(RenderingData a, RenderingData b)
  {
    return a.getDocument().equals(b.getDocument()) && a.getPage().equals(b.getPage());
  }
}
