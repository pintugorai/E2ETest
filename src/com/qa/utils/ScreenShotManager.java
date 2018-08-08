package com.qa.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.ensimtest.base.Driver;

public class ScreenShotManager {
	
	public static class ScreenShotConfig
	{
		private static String screenShotPath = "";
		
		private static int screenShotIndex = 0;
		
		private static String getTimeStamp()
		{
			DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
			Date date = new Date();
			return dateFormat.format(date);
		}
		
		public static String getNextScreenShotName()
		{
			screenShotIndex++;
			int finalIndex = 1000000 + screenShotIndex;
			String indexStr = ("" + finalIndex).trim().substring(1);
			indexStr = getTimeStamp() +"_"+ indexStr.trim()+ ".png";
			System.out.println("New image file: " + indexStr);
			return indexStr;
		}
		
		public static void resetCounter()
		{
			screenShotIndex = 0;
		}
		
		public static void setPath(String parentFolder, String methodFolderName)
		{
			screenShotIndex = 0;
			screenShotPath = "sreenshot/"+parentFolder+"/" + methodFolderName;
			//screenShotPath = "D:\\Knowledge based\\easAutomation\\IDM Automation\\ScreenToBeDelete";
			
			// Create parent dir...
			File classFolder=new File("sreenshot/"+parentFolder);
			if(!classFolder.exists())
			{
				classFolder.mkdir();
			}
			
			// Create child dir...
			File methodFolder=new File("sreenshot/"+parentFolder+"/" + methodFolderName);
			if(!methodFolder.exists())
			{
				methodFolder.mkdir();
			}
		}
		
	}

	public void takeScreenShot(String methodName, String classname) {
		try {
			int counter=1;
			File classFolder=new File("sreenshot/"+classname);
			if(!classFolder.exists())
			{
				classFolder.mkdir();
			}
			
			File imageFile=new File("sreenshot/"+classname+"/"+methodName+".png");
			while(true)
			{
				if(imageFile.exists())
				{
					imageFile=new File("sreenshot/"+classname+"/"+methodName+counter+".png");
					counter++;
				}
				else
				{
					break;
				}
			
			}
			
			File scrFile = ((TakesScreenshot)Driver.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, imageFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void takeContinuousScreenShot()
	{
		System.out.println(ScreenShotConfig.screenShotPath);
		// Create the image
		File imageFile=new File(ScreenShotConfig.screenShotPath + "/" + ScreenShotConfig.getNextScreenShotName());
		File scrFile = null;
		try
		{
			scrFile = ((TakesScreenshot)Driver.driver).getScreenshotAs(OutputType.FILE);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		try
		{
			FileUtils.copyFile(scrFile, imageFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
