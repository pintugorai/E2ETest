package com.qa.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
	private static String propertyFile= ResReference.testProperty + "framework.properties"; 
	private static Properties prop;
	private static FileInputStream fis;
	private static String dbpropertyFile= ResReference.testProperty + "DBInfo.properties";
	
	public PropertyReader() throws IOException
	{
		prop=new Properties();
		fis=new FileInputStream(propertyFile);
		prop.load(fis);
	}
	
	public PropertyReader(String dbProperty) throws IOException
	 {
	  prop=new Properties();
	  fis=new FileInputStream(propertyFile);
	  prop.load(fis);
	  dbpropertyFile = ResReference.testProperty + dbProperty + ".properties";
	 }

	public String getURL() throws IOException
	{

		return prop.getProperty("URL").trim();			
	}
	public String getBrowserName() throws IOException
	{

		return prop.getProperty("BrowserName").trim();			
	}
	
	public String getSeapMode() throws IOException
	{

		return prop.getProperty("SeapMode").trim();			
	}
	
	public String getDBConnectionString() throws IOException
	{
		Properties prop1=new Properties();
		FileInputStream fis1=new FileInputStream(dbpropertyFile);
		prop1.load(fis1);
		return prop1.getProperty("dbConn").trim();
	}
	
	public int getDefaultBPTIntervalSec() throws IOException
	{

		return Integer.parseInt(prop.getProperty("BPTInterval").trim());			
	}

}
