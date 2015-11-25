package org.parser;

import java.util.Collection;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface ParsingResult
{
  Collection<RenderingData> getRenderingDatas();

  void setRenderingDatas(Collection<RenderingData> renderingDatas);

  SummaryData getSummaryData();

  void setSummaryData(SummaryData summaryData);
}
