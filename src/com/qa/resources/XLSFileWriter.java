package com.qa.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSFileWriter
{
	private Workbook workbook = null;
	private Sheet sheet = null;
	private int rowIndex = 0;
	private String fileName = "";
	
	public XLSFileWriter(String fileName)
	{
		this.fileName = fileName;
		System.out.println("fileName...."+this.fileName);
		createExcel(fileName);
	}
	
	private void createExcel(String fileName)
	{
		FileInputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(new File(fileName));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
        
		try
		{
			workbook = new XSSFWorkbook(inputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        sheet = workbook.getSheetAt(0);
        
        if(sheet.getFirstRowNum()!=sheet.getLastRowNum())
        {
        	System.out.println("Executing delete operation.....");
        	for (int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++)
    		{
    			sheet.removeRow( sheet.getRow(i) );
    		}
        }
	}
	
	public void entryInExcel(String []info)
	{
		Row r = sheet.createRow(rowIndex++);
		int cellIndex = 0;
		for(int i=0; i<info.length; i++)
		{
			System.out.println("Writing in excel...."+info[i]);
			r.createCell(cellIndex++).setCellValue(info[i]);
			System.out.println("row..."+rowIndex+"cellIndex..."+cellIndex);
		}
	}
	
	public void saveExcel()
	{
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
//			Writer out = new OutputStreamWriter(fos, "UTF8");
//		      out.write(str);
//		      out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
