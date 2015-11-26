package org.parser.impl;

import java.util.LinkedList;
import java.util.List;

import org.parser.ParsingResult;
import org.parser.RenderingData;
import org.parser.SummaryData;

/**
 * This class holds all rendering results
 * @author Orest Lozynskyy
 */
public class ParsingResultImpl implements ParsingResult
{
  private List<RenderingData> renderingDatas;
  private SummaryData summaryData;

  public ParsingResultImpl()
  {
    summaryData = new SummaryDataImpl();
    renderingDatas = new LinkedList<>();
  }

  public List<RenderingData> getRenderingDatas()
  {
    return renderingDatas;
  }

  public void setRenderingDatas(List<RenderingData> renderingDatas)
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

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ParsingResultImpl that = (ParsingResultImpl) o;

    if (renderingDatas != null ? !renderingDatas.equals(that.renderingDatas) : that.renderingDatas != null)
      return false;
    return summaryData != null ? summaryData.equals(that.summaryData) : that.summaryData == null;

  }

  @Override
  public int hashCode()
  {
    int result = renderingDatas != null ? renderingDatas.hashCode() : 0;
    result = 31 * result + (summaryData != null ? summaryData.hashCode() : 0);
    return result;
  }
}
