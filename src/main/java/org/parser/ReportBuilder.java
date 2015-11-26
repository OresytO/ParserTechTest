package org.parser;

import javax.xml.transform.stream.StreamResult;

/**
 * All reports builders implementations should implements this interface
 * @author Orest Lozynskyy
 */
public interface ReportBuilder
{

  void getReport (ParsingResult parsingResult, StreamResult resultOutput);
}
