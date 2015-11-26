package org.parser;

import java.io.Reader;

/**
 * All parsers implementations should implements this interface
 * @author Orest Lozynskyy
 */
public interface Parser
{
  ParsingResult parse(Reader reader);
}
