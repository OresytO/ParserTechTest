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
import org.parser.RenderingData;
import org.parser.ReportBuilder;
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
  public void getReport(ParsingResult parsingResult, StreamResult result)
  {
    // report elements
    Element report = doc.createElement("report");
    doc.appendChild(report);

    // rendering elements
    for (RenderingData renderingData : parsingResult.getRenderingDatas())
    {
      report.appendChild(getRendering(renderingData));
    }
    // summary elements
    report.appendChild(getSummary(parsingResult.getSummaryData()));

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

  private Node getRendering(RenderingData renderingData)
  {

    // rendering elements
    Element rendering = doc.createElement("rendering");

    // document elements
    rendering.appendChild(getNode("document", renderingData.getDocument()));

    // page elements
    rendering.appendChild(getNode("page", renderingData.getPage()));

    // uid elements
    rendering.appendChild(getNode("uid", renderingData.getUid()));

    // start elements
    renderingData.getStarts().forEach(s -> rendering.appendChild(getNode("start", s)));

    // get elements
    renderingData.getGets().forEach(g -> rendering.appendChild(getNode("get", g)));

    return rendering;
  }

  private Node getSummary(SummaryData summaryData)
  {
    // summary element
    Element rendering = doc.createElement("summary");

    // count element
    rendering.appendChild(getNode("count", Integer.toString(summaryData.getCount())));

    // duplicates element
    rendering.appendChild(getNode("duplicates", Integer.toString(summaryData.getDuplicates())));

    // unnecessary element
    rendering.appendChild(getNode("unnecessary", Integer.toString(summaryData.getUnnecessary())));

    return rendering;
  }
}
