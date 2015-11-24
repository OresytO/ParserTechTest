package org.parser;

import java.util.List;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface ReportData
{
  String getDocument();

  void setDocument(String document);

  String getPage();

  void setPage(String page);

  String getUid();

  void setUid(String uid);

  List<String> getStart();

  void setStart(List<String> start);

  List<String> getGet();

  void setGet(List<String> get);
}
