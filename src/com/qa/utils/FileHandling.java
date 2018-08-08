package com.qa.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class FileHandling
{
	public String ReadFileData(String filepath)
	{
		return "";
	}
	
	public Document getXmlDocument(String filename) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	public String convertFromHtml(String htmlData)
	{
		htmlData = htmlData.replaceAll("\\<.*?>","");
		htmlData = htmlData.replaceAll("&nbsp;","");
		htmlData = htmlData.replaceAll("&amp;","");
		return htmlData;
	}
}