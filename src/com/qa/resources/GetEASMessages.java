package com.qa.resources;

import java.io.FileInputStream;
import java.util.Properties;

public class GetEASMessages 
{
	private static String propertyFile=ResReference.uiRes + "easmessage.properties"; 
	private static Properties prop;
	private static FileInputStream fis;
	
	public GetEASMessages() 
	{
		try{
		prop=new Properties();
		fis=new FileInputStream(propertyFile);
		prop.load(fis);
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace().toString());
		}
	}
	
	public String getProperty(String propertName)
	{
		return prop.getProperty(propertName).trim();
	}
}
