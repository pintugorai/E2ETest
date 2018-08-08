package com.qa.resources;

import java.util.ArrayList;
import java.util.HashMap;

public class TestConfigHandler
{
	//to check if the suite is runnable or not
	public boolean isSuiteRunnable(XLSFileReader xlsFileRead,String suiteName)
	{
		boolean flag=false;
		for(int rowNumber=2;rowNumber<=xlsFileRead.getRowCount("TestSuites");rowNumber++)      //strting from two as 1st row only contains column names
		{
			if(xlsFileRead.getCellData("TestSuites","TSID",rowNumber).trim().equals(suiteName.trim()))
			{
				if(xlsFileRead.getCellData("TestSuites","Runmode",rowNumber).trim().equalsIgnoreCase("Y"))
				{
					flag=true;
					break;
				}
			}
		}
		//to release the memory 
		xlsFileRead=null;
		return flag;
	}	

	public static boolean isTestCaseRunnable(XLSFileReader xlsFileRead, String testCaseName)
	{
		System.out.println(testCaseName);
		boolean flag=false;
		for(int rowNumber=2;rowNumber<=xlsFileRead.getRowCount("TestCases");rowNumber++)
		{

			if(xlsFileRead.getCellData("TestCases","TCID",rowNumber).trim().equals(testCaseName.trim()))
			{
				if(xlsFileRead.getCellData("TestCases","Runmode",rowNumber).trim().equalsIgnoreCase("Y"))
				{
					flag=true;
					break;
				}
			}
		}
		//to release the memory
		xlsFileRead=null;
		return flag;
	}

	//return data to a two dimentional array
	public  Object[][] getData(XLSFileReader xlsFileRead,String testCaseName)
	{
		ArrayList<String> listOfTestData=new ArrayList<String>();
		
		//if the sheet is not present
		if(!xlsFileRead.isSheetExist(testCaseName))
		{
			//cleanup object
			xlsFileRead=null;
			
			//hypothetical object array one row no column
			return new Object[1][0];
		}
		else
		{
			int row=xlsFileRead.getRowCount(testCaseName);
			int col=xlsFileRead.getColumnCount(testCaseName);
			listOfTestData=getParam(xlsFileRead, testCaseName, col);
			Object[][] data=new Object[row-1][1];

			for(int rowNum=2;rowNum<=row;rowNum++)
			{
				HashMap<String, String> hashTable = new HashMap<String, String>();	
				for(int ColNum=1;ColNum<=col;ColNum++)
				{
					hashTable.put(listOfTestData.get(ColNum-1),xlsFileRead.getCellData(testCaseName, ColNum, rowNum));
					// data[RowNum-2][ColNum-1]=xr.getCellData(TestCaseName, ColNum, RowNum);
				}
				data[rowNum-2][0]=hashTable;
			}
			listOfTestData=null;
			return data;
		}
	}

	public ArrayList<String> getParam(XLSFileReader xlsFileRead, String testCaseName, int columnCount)
	{
		ArrayList<String> listData=new ArrayList<String>();
		for(int rowNumber=1;rowNumber<=columnCount;rowNumber++)
		{
			listData.add(xlsFileRead.getCellData(testCaseName,rowNumber,1).trim());
		}
		return listData;
	}

	public  Object[][] getData1(XLSFileReader xlsFileRead, String testCaseName)
	{
		//if the sheet is not present
		if(!xlsFileRead.isSheetExist(testCaseName))
		{
			//cleanup object
			xlsFileRead=null;
			
			//hypothetical object array one row no column
			return new Object[1][0];
		}

		int row=xlsFileRead.getRowCount(testCaseName);
		int col=xlsFileRead.getColumnCount(testCaseName);
		Object[][] data=new Object[row-1][col];
		for(int rowNum=2;rowNum<=row;rowNum++)
		{
			for(int ColNum=1;ColNum<=col;ColNum++)
			{
				data[rowNum-2][ColNum-1]=xlsFileRead.getCellData(testCaseName, ColNum, rowNum);
			}
		}
		return data;
	}
}
