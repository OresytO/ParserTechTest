package org.parser;

import java.util.List;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface ParsingResult
{
  List<ReportData> getReportDatas();

  void setReportDatas(List<ReportData> reportDatas);

  SummaryData getSummaryData();

  void setSummaryData(SummaryData summaryData);
}
