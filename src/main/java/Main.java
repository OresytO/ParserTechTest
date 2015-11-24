import javax.xml.transform.stream.StreamResult;

import org.parser.Parser;
import org.parser.ReportBuilder;
import org.parser.impl.ParserImpl;
import org.parser.impl.ReportBuilderXml;

public class Main
{

  public static void main(String[] args)
  {
    Parser parser = new ParserImpl();
    ReportBuilder builder = new ReportBuilderXml();
    // builder.buildReport(new StreamResult(new File("C:\\file.xml")));
    builder.buildReport(parser.parse());
    builder.getReport(new StreamResult(System.out));
  }
}
