package com.qa.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ActiveDirectoryPropertyReader 
{
	private static String adPropertyFile=ResReference.testProperty+"activedirectory.properties";
	private static Properties prop;
	private static FileInputStream fis;
	
	public ActiveDirectoryPropertyReader() throws IOException
	{
		prop=new Properties();
		fis=new FileInputStream(adPropertyFile);
		prop.load(fis);
	}
	
	public String getAdIP()
	{
		return prop.getProperty("adIP").toString();
	}
	
	public String getAdLDAPPort()
	{
		return prop.getProperty("adLDAPPort").toString();
	}
	
	public String getAdUserName()
	{
		return prop.getProperty("adUserName").toString();
	}
	
	public String getAdpassword()
	{
		return prop.getProperty("adpassword").toString();
	}
	
	public String canonicalPathContainerOrg()
	{
		return prop.getProperty("canonicalPathContainerOrg").toString();
	}
	
}
