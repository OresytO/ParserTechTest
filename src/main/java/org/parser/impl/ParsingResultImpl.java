package org.parser.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.parser.ParsingResult;
import org.parser.RenderingData;
import org.parser.SummaryData;

/**
 * Created by OrestO on 24.11.2015.
 */
public class ParsingResultImpl implements ParsingResult
{
  private Collection<RenderingData> renderingDatas;
  private SummaryData summaryData;

  public ParsingResultImpl() {
    summaryData = new SummaryDataImpl();
    renderingDatas = new LinkedList<>();
  }

  public Collection<RenderingData> getRenderingDatas()
  {
    return renderingDatas;
  }

  public void setRenderingDatas(Collection<RenderingData> renderingDatas)
  {
    this.renderingDatas = renderingDatas;
  }

  @Override
  public SummaryData getSummaryData()
  {
    return summaryData;
  }

  @Override
  public void setSummaryData(SummaryData summaryData)
  {
    this.summaryData = summaryData;
  }
}
