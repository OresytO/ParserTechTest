package org.parser.impl;

import java.util.LinkedList;
import java.util.List;

import org.parser.RenderingData;

/**
 * Created by OrestO on 24.11.2015.
 */
public class RenderingDataImpl implements RenderingData
{
  private String document;
  private String page;
  private String uid;
  private List<String> starts = new LinkedList<>();
  private List<String> gets = new LinkedList<>();

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

  public List<String> getStarts()
  {
    return starts;
  }

  public void setStarts(List<String> starts)
  {
    this.starts = starts;
  }

  @Override
  public void addStart(String newStart)
  {
    this.starts.add(newStart);
  }

  @Override
  public void addStarts(List<String> newStarts)
  {
    this.starts.addAll(newStarts);
  }

  public List<String> getGets()
  {
    return gets;
  }

  public void setGets(List<String> gets)
  {
    this.gets = gets;
  }

  @Override
  public void addGet(String newGet)
  {
    this.gets.add(newGet);
  }

  @Override
  public void addGets(List<String> newGets)
  {
    this.gets.addAll(newGets);
  }
}
