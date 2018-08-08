package com.qa.resources;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ArchiveFileManager
{
	private void closeFileInputStream(FileInputStream obj)
	{
		try
		{
			if(obj!=null)
			{
				obj.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("Unable to close: " + obj);
			System.out.println(e);
		}
	}
	
	private void closeZipInputStream(ZipInputStream obj)
	{
		try
		{
			if(obj!=null)
			{
				obj.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("Unable to close: " + obj);
			System.out.println(e);
		}
	}
	
	private void closeFileOutputStream(FileOutputStream obj)
	{
		try
		{
			if(obj!=null)
			{
				obj.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("Unable to close: " + obj);
			System.out.println(e);
		}
	}
	
	public void unzip(String zipFilePath, String destDir) 
	{
        File dir = new File(destDir);

        if(!dir.exists())
        {
        	dir.mkdirs();
        }
        
        byte[] buffer = new byte[1024];
        try 
        {
        	FileInputStream fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            
            ZipEntry ze = zis.getNextEntry();
            while(ze != null)
            {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                
                new File(newFile.getParent()).mkdirs();
                
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0)
                {
                	fos.write(buffer, 0, len);
                }
                closeFileOutputStream(fos);

                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            closeZipInputStream(zis);
            closeFileInputStream(fis);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
     }
	
	public void zipSingleFile(File file, String zipFileName) 
	{
        try 
        {
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            //add a new Zip Entry to the ZipOutputStream
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0)
            {
                zos.write(buffer, 0, len);
            }
            
            //Close the zip entry to write to zip file
            zos.closeEntry();
            //Close resources
            zos.close();
            fis.close();
            fos.close();
            System.out.println(file.getCanonicalPath()+" is zipped to "+zipFileName);
            
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
	
	public void zipFiles(List<String> files,String destDir,String sourceFile)
	{    
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        try
        {
            fos = new FileOutputStream(destDir);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for(String filePath:files)
            {
                File input = new File(sourceFile+filePath);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                System.out.println("Zipping the file: "+input.getName());
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4*1024];
                int size = 0;
                while((size = fis.read(tmp)) != -1)
                {
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            System.out.println("Done... Zipped the files...");
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(fos != null) fos.close();
            }
            catch(Exception ex)
            {
                 
            }
        }
    }
	
}
