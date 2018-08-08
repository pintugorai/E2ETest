package com.qa.resources;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.qa.utils.FileHandling;

public class TestData
{
	//private String resFilePath = ResReference.testData;
	public class ISP
	{
		public String URL, username, password;
	}
	public ISP getISPInfo()
	{
		// 0 -> URL
		// 1 -> uname
		// 2 -> psw
		String ispInfoFile = "ispinfo.xml";
		FileHandler file = new FileHandling();
		Document document = null;
		try
		{
			document = file.getXmlDocument(ResReference.testData + ispInfoFile);
		} 
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			System.out.println(e);
		}
		String []info = getISPData(document);
		ISP isp = new ISP();
		isp.URL = info[0];
		isp.username = info[1];
		isp.password = info[2];
		return isp;
	}
	
	private String[] getISPData(Document xmlDocument)
	{
		Element element = (Element) xmlDocument.getElementsByTagName("root").item(0);
		String []info = new String[3];
		info[0] = element.getElementsByTagName("URL").item(0).getTextContent().trim();
		info[1] = element.getElementsByTagName("username").item(0).getTextContent().trim();
		info[2] = element.getElementsByTagName("password").item(0).getTextContent().trim();
		return info;
	}
}
