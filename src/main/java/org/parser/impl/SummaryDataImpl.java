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

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    SummaryDataImpl that = (SummaryDataImpl) o;

    if (count != that.count)
      return false;
    if (duplicates != that.duplicates)
      return false;
    return unnecessary == that.unnecessary;

  }

  @Override
  public int hashCode()
  {
    int result = count;
    result = 31 * result + duplicates;
    result = 31 * result + unnecessary;
    return result;
  }
}
