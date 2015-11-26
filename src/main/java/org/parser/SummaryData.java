package org.parser;

/**
 * All summary data implementations should implements this interface
 * @author Orest Lozynskyy
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
