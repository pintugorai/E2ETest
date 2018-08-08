package com.qa.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLFileReader
{
	private List<String> list = new ArrayList<>();
	
	private void lookInXml(Node node, String path)
    {
    	list.add(path+"///"+node.getNodeName());
    	NodeList listOfElements = node.getChildNodes();
    	for(int i=0; i<listOfElements.getLength(); i++)
    	{
    		Node inner = listOfElements.item(i);
    		if(inner.getNodeType()==Node.TEXT_NODE)
    		{
    			// Add in list
    			if(inner.getTextContent().trim().equals("")==false)
    			{
 //   				System.out.println(path+"///"+node.getNodeName()+"///"+ inner.getTextContent().trim());
    				list.add(path+"///"+node.getNodeName()+"///"+ inner.getTextContent().trim());
    			}
    		}
    		else
    		{
    			lookInXml(inner, path+"///"+node.getNodeName());
    		}
    	}
    }
	
	public List<String> getXMLAsListFormat(String xmlFilePath)
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse (new File(xmlFilePath));
		    Node node = doc.getDocumentElement();
		    node.normalize();
		    list = new ArrayList<>();
			lookInXml(node, ".");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return list;
	}
}
