package com.qa.utils;

import java.io.File;

import com.ensimtest.resource.FetchFilesFromDir;
import com.ensimtest.resource.TestDataDirType;

public class DownloadedFileHandler 
{

	
	public boolean isFilePresentContains(String fileName)
	{
		boolean flag=false;
		FetchFilesFromDir fetch = new FetchFilesFromDir();
		String directoryName=fetch.getFilePath(TestDataDirType.DownloadSample, "");
		System.out.println(directoryName);
		File folder = new File(directoryName);
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0;i<listOfFiles.length;i++)
		{
			if(listOfFiles[i].toString().contains(fileName))
			{
				flag=true;
				break;
			}
		}
		
		return flag;
		
	}
	
	
}
