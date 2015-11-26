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
import org.parser.RenderingData;
import org.parser.SummaryData;
import org.parser.util.Lists;

/**
 * This class represents default parser implementation
 * @author Orest Lozynskyy
 */
public class ParserImpl implements Parser
{
  private ParsingResult parsingResult;
  private Map<String, RenderingData> uidToOrphanRenderingMap = new HashMap<>();
  private final Map<String, RenderingData> threadPool = new HashMap<>();
  private final Map<String, RenderingData> uidToRenderingMap = new HashMap<>();

  // start
  private static final Pattern startRenderingPattern = Pattern.compile("(Executing request startRendering)");
  // return uid
  private static final Pattern startRenderingReturnPattern = Pattern.compile("(Service startRendering returned)");
  // get
  private static final Pattern getRenderingPattern = Pattern.compile("(Executing request getRendering)");

  private static final Pattern timestampPattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");
  private static final Pattern threadPattern = Pattern.compile("(WorkerThread-\\d+)");
  private static final Pattern argumentsPattern = Pattern.compile("(\\d+, \\d+)");
  private static final Pattern uidReturnPattern = Pattern.compile("Service startRendering returned (\\d+-\\d+)");
  private static final Pattern uidGetPattern = Pattern.compile("Executing request getRendering with arguments \\[(\\d+-\\d+)\\]");

  @Override
  public ParsingResult parse(Reader reader)
  {
    BufferedReader bufferedReader = new BufferedReader(reader);
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
      throw new IllegalArgumentException("Can't read from given reader", e);
    }

    parsingResult.setRenderingDatas(Lists.newArrayList(uidToRenderingMap.values()));

    SummaryData summaryData = parsingResult.getSummaryData();
    summaryData.setCount(uidToRenderingMap.size());
    summaryData.setUnnecessary((int) uidToRenderingMap.values().stream().filter(i -> i.getGets().isEmpty()).count());
    return parsingResult;
  }

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

    if(threadPool.containsKey(thread))
    {
      RenderingData renderingData = threadPool.get(thread);
      if (renderingData != null && renderingData.getDocument().equals(document) && renderingData.getPage().equals(page))
      {
        renderingData.addStart(timestamp);
      }
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

  private void processStartRenderingReturn(String line)
  {
    Matcher threadMatcher = threadPattern.matcher(line);
    String thread = threadMatcher.find() ? threadMatcher.group(1) : "";

    Matcher uidMatcher = uidReturnPattern.matcher(line);
    String uid = uidMatcher.find() ? uidMatcher.group(1) : "";

    RenderingData renderingData;
    if (threadPool.containsKey(thread))
    {
      renderingData = threadPool.remove(thread);
    }
    else
    {
      renderingData = new RenderingDataImpl();
    }
    if (uidToRenderingMap.containsKey(uid))
    {
      //TODO: merge or duplicate?
      RenderingData previousResult = uidToRenderingMap.get(uid);
      String previousDocument = previousResult.getDocument();
      String previousPage = previousResult.getPage();

      if (previousDocument != null && previousPage != null && previousDocument.equals(renderingData.getDocument()) && previousPage.equals(renderingData.getPage()))
      {
        previousResult.addStarts(renderingData.getStarts());
        parsingResult.getSummaryData().incrementDuplicates();
      }
    }
    else
    {
      renderingData.setUid(uid);
      uidToRenderingMap.put(uid, renderingData);
    }
  }

  private void processGetRendering(String line)
  {
    Matcher timestampMatcher = timestampPattern.matcher(line);
    String timestamp = timestampMatcher.find() ? timestampMatcher.group(1) : "";

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
      // Case when previous return for startRendering not in current file
      // We can make statistic but without document, page info
      RenderingData orphanRenderingData;
      if (uidToOrphanRenderingMap.containsKey(uid))
      {
        uidToOrphanRenderingMap.get(uid).addGet(timestamp);
      }
      else
      {
        orphanRenderingData = new RenderingDataImpl();
        orphanRenderingData.setUid(uid);
        orphanRenderingData.addGet(timestamp);
        uidToOrphanRenderingMap.put(uid, orphanRenderingData);
      }
    }
  }
}
