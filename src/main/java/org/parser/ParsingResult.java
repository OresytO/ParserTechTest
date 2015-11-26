package org.parser;

import java.util.List;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface ParsingResult
{
  List<RenderingData> getRenderingDatas();

  void setRenderingDatas(List<RenderingData> renderingDatas);

  SummaryData getSummaryData();

  void setSummaryData(SummaryData summaryData);
}
