package com.qa.resources;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.qa.utils.FileHandling;

public class AppData
{
	//private String resFilePath = ResReference.uiRes;
	private String resUserFilePath = "resource\\oracleTestData\\userInfo.xml"; 
	
	protected String[] readAppData(Object className)
	{
		String fullName = className.getClass().getCanonicalName().toString();
		String packageName = className.getClass().getPackage().toString().replace("package", "").trim();
		String subClassName = className.getClass().getSimpleName().toString();
		String parentClassName = fullName.replace(packageName, "").replace(subClassName, "").replace(".", "").trim();
		FileHandling file = new FileHandling();
		Document document = null;
		try
		{
			document = file.getXmlDocument(ResReference.uiRes + parentClassName + ".xml");
		} 
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			System.out.println(e);
		}
		return getXmlNodeKeyValuePair(document, subClassName);	
	}
	
	private String[] getXmlNodeKeyValuePair(Document document, String nodeName)
	{
		Element element = (Element) document.getElementsByTagName(nodeName).item(0);
		String []pair = new String[2];
		pair[0] = element.getElementsByTagName("key").item(0).getTextContent().trim();
		pair[1] = element.getElementsByTagName("value").item(0).getTextContent().trim();
		return pair;
	}
	
	protected String getJSONdataOfUser(String username)
	{
		Document document = null;
		FileHandling file = new FileHandling();
		try
		{
			document = file.getXmlDocument(resUserFilePath);
		} 
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			System.out.println(e);
		}
		Element element = (Element) document.getElementsByTagName(username).item(0);
		return element.getTextContent().trim();
	}
}
