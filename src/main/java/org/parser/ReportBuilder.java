package org.parser;

import javax.xml.transform.stream.StreamResult;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface ReportBuilder
{

  void buildReport(ParsingResult parsingResult);

  void getReport(StreamResult result);
}
