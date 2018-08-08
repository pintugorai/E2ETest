package com.qa.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static String logPath = "sreenshot";
	private static String logFile = "_log.txt";
	
	private static void writeInFile(String message)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try
		{
			System.out.println(logFile);
			fw = new FileWriter(logFile, true);
			bw = new BufferedWriter(fw);
			bw.write(message);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Unable to create the file " + e);
		}
		finally
		{
			try
			{
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public static void log(String message)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		message = dateFormat.format(date) + " : " + message + "\n";
		
		System.out.println("Writing :" + message);
		//createLogFile();
		
		writeInFile(message);
	}
	
	public static void createLogFile()
	{
		// Create the folder
		File classFolder=new File(logPath);
		if(!classFolder.exists())
		{
			classFolder.mkdir();
		}
	}

	public static void setFilePath(String className, String methodName)
	{
		File classFolder=new File(logPath + "/" + className);
		if(!classFolder.exists())
		{
			classFolder.mkdir();
		}
		
		File methodFolder = new File(logPath + "/" + className + "/" + methodName);
		if(!methodFolder.exists())
		{
			methodFolder.mkdir();
		}
		
		logFile = logPath + "\\" + className + "\\" + methodName + "\\log.txt";
	}
}
