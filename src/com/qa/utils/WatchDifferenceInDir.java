package com.qa.utils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.util.concurrent.TimeUnit;

public class WatchDifferenceInDir
{
	private String directoryPathToWatch;
	private long startTime = 0;
	private WatchService watchService = null;
	
	public WatchDifferenceInDir(String directoryPathToWatch)
	{
		this.directoryPathToWatch = directoryPathToWatch;
	}
	
	public void startWatch()
	{
		try 
		{
			System.out.println("1");
			Path downloadFolderPath = Paths.get(directoryPathToWatch);
			watchService = FileSystems.getDefault().newWatchService();
			downloadFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			System.out.println(StandardWatchEventKinds.ENTRY_CREATE);
			startTime = System.currentTimeMillis();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String verifyWatchForFileType(String fileExtension)
	{
		String downloadedFileName = null;
		boolean found = false;
		boolean valid = true;
		
		do 
		{
			long timeOut = 20;
			WatchKey watchKey = null;
			try
			{
				watchKey = watchService.poll(timeOut,TimeUnit.SECONDS);
			}
			catch (InterruptedException e)
			{
				System.out.println("InterruptedException occured:");
				e.printStackTrace();
			}
			long currentTime = (System.currentTimeMillis()-startTime)/1000;
			System.out.println("currentTime: " + currentTime);
//			if(currentTime>timeOut)
//			{
//				System.out.println("Download operation timed out.. Expected file was not downloaded");
//				return downloadedFileName;
//			}
			System.out.println("2");
			for (WatchEvent<?> event : watchKey.pollEvents())
			{
				Kind<?> kind = event.kind();
				if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) 
				{
					String fileName = event.context().toString();
					System.out.println("New File Created:" + fileName);
					if(fileName.endsWith(fileExtension))
					{
						downloadedFileName = fileName;
						System.out.println("Downloaded file found with extension " + fileExtension + ". File name is " + fileName);
						TestUtils.delay(500);
						found = true;
					}
				}
			}
			if(found)
			{
				System.out.println("3");
				return downloadedFileName;
			}
			else
			{
				currentTime = (System.currentTimeMillis()-startTime)/1000;
				if(currentTime>timeOut)
				{
					System.out.println("Failed to download expected file");
					return null;
				}
				valid = watchKey.reset();
			}
		} while (valid);
		return null;
	}
}
