package org.parser.impl;

import org.parser.SummaryData;

/**
 * Created by OrestO on 24.11.2015.
 */
public class SummaryDataImpl implements SummaryData
{
  private int count;
  private int duplicates;
  private int unnecessary;

  @Override
  public int getCount()
  {
    return count;
  }

  @Override
  public void setCount(int count)
  {
    this.count = count;
  }

  @Override
  public int getDuplicates()
  {
    return duplicates;
  }

  @Override
  public void setDuplicates(int duplicates)
  {
    this.duplicates = duplicates;
  }

  @Override
  public void incrementDuplicates()
  {
    this.duplicates++;
  }

  @Override
  public int getUnnecessary()
  {
    return unnecessary;
  }

  @Override
  public void setUnnecessary(int unnecessary)
  {
    this.unnecessary = unnecessary;
  }
}
