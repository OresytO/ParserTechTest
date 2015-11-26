import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.transform.stream.StreamResult;

import org.parser.Parser;
import org.parser.ParsingResult;
import org.parser.ReportBuilder;
import org.parser.impl.ParserImpl;
import org.parser.impl.ReportBuilderXml;

public class Main
{

  public static void main(String[] args) throws FileNotFoundException {
    long start = System.currentTimeMillis();
    Parser parser = new ParserImpl();
    ReportBuilder builder = new ReportBuilderXml();
    ParsingResult parsingResult = parser.parse(new FileReader("C:\\Users\\OrestO\\Desktop\\server\\server.log"));
    builder.getReport(parsingResult, new StreamResult(System.out));
    System.out.println((System.currentTimeMillis() - start));
  }
}
