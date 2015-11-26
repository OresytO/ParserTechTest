package org.parser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by olozyn on 26.11.2015.
 */
public class Lists {

  public static <E> List<E> newArrayList(Collection<E> e)
  {
    if (e == null)
    {
      return new ArrayList<>(0);
    }

    return new ArrayList<>(e);
  }

  @SafeVarargs
  public static <E> List<E> newArrayList(E ... e)
  {
    if (e == null)
    {
      return new ArrayList<>(0);
    }

    List<E> list = new ArrayList<>(e.length);
    Collections.addAll(list, e);
    return list;
  }
}
