package org.parser;

import java.io.Reader;

/**
 * Created by OrestO on 24.11.2015.
 */
public interface Parser
{
  ParsingResult parse(Reader reader);
}
