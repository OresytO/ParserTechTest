package org.parser;

import java.util.List;

/**
 * All rendering data implementations should implements this interface
 * @author Orest Lozynskyy
 */
public interface RenderingData
{
  String getDocument();

  void setDocument(String document);

  String getPage();

  void setPage(String page);

  String getUid();

  void setUid(String uid);

  List<String> getStarts();

  void setStarts(List<String> start);

  void addStart(String newStart);

  void addStarts(List<String> newStarts);

  List<String> getGets();

  void setGets(List<String> get);

  void addGet(String newGet);

  void addGets(List<String> newGets);
}
