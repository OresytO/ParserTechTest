package org.parser;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface SummaryData
{
  int getCount();

  void setCount(int count);

  int getDuplicates();

  void setDuplicates(int duplicates);

  void incrementDuplicates();

  int getUnnecessary();

  void setUnnecessary(int unnecessary);
}
