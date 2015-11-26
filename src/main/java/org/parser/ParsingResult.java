package org.parser;

import java.util.List;

/**
 * All parsing results implementations should implements this interface
 * @author Orest Lozynskyy
 */
public interface ParsingResult
{
  List<RenderingData> getRenderingDatas();

  void setRenderingDatas(List<RenderingData> renderingDatas);

  SummaryData getSummaryData();

  void setSummaryData(SummaryData summaryData);
}
