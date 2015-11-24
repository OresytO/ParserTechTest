package org.parser.impl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.parser.ParsingResult;
import org.parser.ReportBuilder;
import org.parser.ReportData;
import org.parser.SummaryData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Created by OrestO on 24.11.2015.
 */
public class ReportBuilderXml implements ReportBuilder
{
  private Document doc;

  public ReportBuilderXml()
  {
    try
    {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      doc = docBuilder.newDocument();
    }
    catch (ParserConfigurationException e)
    {
      throw new IllegalStateException("Meet configuration issues with a parser", e);
    }
  }

  @Override
  public void buildReport(ParsingResult parsingResult)
  {
    // report elements
    Element report = doc.createElement("report");
    doc.appendChild(report);

    // TODO: loop here
    // rendering elements
    for (ReportData reportData : parsingResult.getReportDatas())
    {
      report.appendChild(getRendering(reportData));
    }
    // summary elements
    report.appendChild(getSummary(parsingResult.getSummaryData()));
  }

  @Override
  public void getReport(StreamResult result)
  {
    try
    {
      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);

      transformer.transform(source, result);
    }
    catch (TransformerException e)
    {
      throw new IllegalStateException("Can't transform document", e);
    }
  }

  private Node getNode(String elementName, String elementTextValue)
  {
    Element node = doc.createElement(elementName);
    node.appendChild(doc.createTextNode(elementTextValue));
    return node;
  }

  private Node getRendering(ReportData reportData)
  {

    // rendering elements
    Element rendering = doc.createElement("rendering");

    // document elements
    rendering.appendChild(getNode("document", reportData.getDocument()));

    // page elements
    rendering.appendChild(getNode("page", reportData.getPage()));

    // uid elements
    rendering.appendChild(getNode("uid", reportData.getUid()));

    // start elements
    reportData.getStart().forEach(s -> rendering.appendChild(getNode("start", s)));

    // get elements
    reportData.getGet().forEach(g -> rendering.appendChild(getNode("get", g)));

    return rendering;
  }

  private Node getSummary(SummaryData summaryData)
  {
    // rendering elements
    Element rendering = doc.createElement("summary");

    // count elements
    rendering.appendChild(getNode("count", Integer.toString(summaryData.getCount())));

    // duplicates elements
    rendering.appendChild(getNode("duplicates", Integer.toString(summaryData.getDuplicates())));

    // unnecessary elements
    rendering.appendChild(getNode("unnecessary", Integer.toString(summaryData.getUnnecessary())));

    return rendering;
  }
}
