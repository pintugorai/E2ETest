package com.qa.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileHandler
{
	public String[] getAllFileNames(String directoryName)
	{
		File file = new File(directoryName);
		System.out.println("file.exists.............."+file.exists());
		for(int i=0;i<file.list().length;i++)
		{
			System.out.println(file.list()[i]);
		}
		return file.list();
	}
	
	public boolean isFileCreatedWithinTimeStamps(String filename, Date start, Date end)
	{
		try 
        {
			Path path = Paths.get(filename).toAbsolutePath();
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
	        if((start.compareTo(new Date(attr.lastModifiedTime().to(TimeUnit.MILLISECONDS)))<0) && (end.compareTo(new Date(attr.lastModifiedTime().to(TimeUnit.MILLISECONDS)))>0))
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
        }
        catch (Exception e)
        {
        	System.out.println("error! " + e);
        	return false;
        }
	}
	
	private void removeFile(File file)
	{
		try
		{
			System.out.println("Now removing file");
			file.delete();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void deleteFile(String filePath)
	{
		File file=new File(filePath);
		if(file.isDirectory())
    	{
    		if(file.list().length==0)
    		{
    			removeFile(file);
    			System.out.println("Directory is deleted : " + file.getAbsolutePath());
    		}
    		else
    		{
    			String files[] = file.list();
    			for (String temp : files) 
    			{
    				File fileDelete = new File(file, temp);
    				deleteFile(fileDelete.getAbsolutePath());
    			}
	        	if(file.list().length==0)
	        	{
	        		removeFile(file);
	        	    System.out.println("Directory is deleted : "  + file.getAbsolutePath());
	        	}
        	}
    	}
		else
		{
			try
			{
				File f=new File(filePath);
				System.out.println("Deleting the file...");
				removeFile(f);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	public void writeToFile(String filePath, String content)
	{
		System.out.println("Writing: " + content);
		Writer writer = null;

		try
		{
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(filePath, true), "utf-8"));//TODO false to ovrwrt
		    writer.write(content + System.lineSeparator());
		}
		catch (Exception ex)
		{
		  ex.printStackTrace();
		}
		finally
		{
		   try {writer.close();} catch (Exception ex) {/*ignore*/}
		}
	}
	
	public List<String> readFile(String filePath)
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(filePath));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		List<String> list = new ArrayList<>();
		try
		{
		    String line = br.readLine();
		    while (line != null)
		    {
		    	list.add(line);
		        line = br.readLine();
		    }
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		finally 
		{
		    try
		    {
				br.close();
			}
		    catch (IOException e)
		    {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public String generateImportFile(String usgfilePathWithPermission, boolean isWithXtnsn) throws IOException
	{
		String csv = null;
		if(isWithXtnsn)
		{
			csv = usgfilePathWithPermission.trim() + ".csv";
			System.out.println("Inside generateImportFIle wth xtnsn");
		}
		else
		{
			csv = usgfilePathWithPermission.trim();
			System.out.println("Inside generateImportFIle wthout xtnsn");
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(csv));
		BufferedReader br = null;
		try   
		{
			br = new BufferedReader(new FileReader(usgfilePathWithPermission));
		    String line,updatedLine;
		    while ((line = br.readLine()) != null)
		    {
		    	updatedLine = line.substring(line.indexOf(",")+1);
		    	pw.println(updatedLine.trim());
		    }
		}
		catch(Exception e)
		{
			System.out.println("Exception Occured="+e);
		}
		finally
		{
			pw.close();
			br.close();
		}
		
		return csv;
	}
}
