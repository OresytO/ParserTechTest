package org.parser.impl;

import java.util.List;

import org.parser.ParsingResult;
import org.parser.ReportData;
import org.parser.SummaryData;

/**
 * Created by OrestO on 24.11.2015.
 */
public class ParsingResultImpl implements ParsingResult
{
  private List<ReportData> reportDatas;
  private SummaryData summaryData;

  @Override
  public List<ReportData> getReportDatas()
  {
    return reportDatas;
  }

  @Override
  public void setReportDatas(List<ReportData> reportDatas)
  {
    this.reportDatas = reportDatas;
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
