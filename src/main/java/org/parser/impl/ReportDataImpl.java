package org.parser.impl;

import java.util.List;

import org.parser.ReportData;

/**
 * Created by OrestO on 24.11.2015.
 */
public class ReportDataImpl implements ReportData
{
  private String document;
  private String page;
  private String uid;
  private List<String> start;
  private List<String> get;

  @Override
  public String getDocument()
  {
    return document;
  }

  @Override
  public void setDocument(String document)
  {
    this.document = document;
  }

  @Override
  public String getPage()
  {
    return page;
  }

  @Override
  public void setPage(String page)
  {
    this.page = page;
  }

  @Override
  public String getUid()
  {
    return uid;
  }

  @Override
  public void setUid(String uid)
  {
    this.uid = uid;
  }

  @Override
  public List<String> getStart()
  {
    return start;
  }

  @Override
  public void setStart(List<String> start)
  {
    this.start = start;
  }

  @Override
  public List<String> getGet()
  {
    return get;
  }

  @Override
  public void setGet(List<String> get)
  {
    this.get = get;
  }
}
