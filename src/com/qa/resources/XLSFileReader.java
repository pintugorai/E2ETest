package com.qa.resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSFileReader {

	public  String Path;
	private XSSFWorkbook Workbook=null;
	//private XSSFSheet Sheet = null;
	//private XSSFRow Row=null;
	//private XSSFCell Column=null;
	//public  FileOutputStream FileOut =null;

	public XLSFileReader(String Path)
	{
		this.Path=Path;
		try
		{
			FileInputStream fis = new FileInputStream(Path);
			Workbook=new XSSFWorkbook(fis);
			Workbook.getSheetAt(0);
			fis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isSheetExist(String SheetName)
	{
		//System.out.println(SheetName);
		//System.out.println(Workbook.getSheetName(1));
		if(Workbook.getSheet(SheetName)==null)
			return false;
		else
			return true;
	}

	/**
	 * method which will give the number of rows present in a sheet
	 * function has a limitation that if a sheet has no row but sheet present 
	 * it will give you output one
	 * @param SheetName
	 * @return number of rows
	 */
	public int getRowCount(String SheetName)
	{
		if(isSheetExist(SheetName))
			return (Workbook.getSheet(SheetName).getLastRowNum()+1);
		else
			return 0;
	}

	/**
	 * Returns number of columns in a sheet	  (applicable for our test cases at 1st row specify number of column
	 * @param sheetName
	 * @return Workbook.getSheet(sheetName)
	 */
	public int getColumnCount(String sheetName)
	{
		// check if sheet exists
		if(!isSheetExist(sheetName))
		{
			return -1;
		}
		XSSFRow row = Workbook.getSheet(sheetName).getRow(0);
		if(row==null)
		{
			return -1;
		}
		return row.getLastCellNum();
	}

	// returns the data from a cell(cell means a block)
	public String getCellData(String sheetName, String columnName, int rowNumber)
	{
		int columnNumber=-1;
		DataFormatter fmt = new DataFormatter();
		
		// used to return value as it is in the xls file
		if(!isSheetExist(sheetName))
		{
			//return blank if sheet does not exists
			return "";
		}
		if(rowNumber<=0)
		{
			//return blank if row number is invalid
			return "";
		}

		XSSFSheet sheet=Workbook.getSheet(sheetName);

		//take the 1st column to check if the column exists
		XSSFRow row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++)    
		{
			 //to check if the column exists
			if(row.getCell(i).getStringCellValue().trim().equals(columnName.trim()))
			{
				columnNumber=i;
				break;
			}
		}

		if(columnNumber==-1)
		{
			//return blank if column does not exists
			return "";
		}

		//point to desire row
		row=sheet.getRow(rowNumber-1);
		if(row==null)
		{
			//return blank if row doesnot exists 
			return ""; 
		}

		//point to the desire cell
		XSSFCell cell=row.getCell(columnNumber);
        if(cell==null)
        {
        	//return blank if cell does not exists
        	return "";
        }
        return fmt.formatCellValue(cell);
	}
	
	// returns the data from a cell(cell means a block)
	public String getCellData(String sheetName,int colNum,int rowNum)
	{
		//used to return value as it is in the xls file
		DataFormatter formatData = new DataFormatter();

		if(!isSheetExist(sheetName))
		{
			//return blank if sheet does not exists
			return "";
		}
		if(rowNum<=0 || colNum<=0)
		{
			//return blank if row or column number is invalid
			return "";
		}
		
		//point to desire row
		XSSFRow row=Workbook.getSheet(sheetName).getRow(rowNum-1);
		if(row==null)
		{
			//return blank if row doesnot exists
			return "";
		}

		//point to the desire cell
		XSSFCell cell=row.getCell(colNum-1);
        if(cell==null)
        {
        	//return blank if cell does not exists
        	return "";
        }
        return formatData.formatCellValue(cell);
	}

	// returns true if data is set successfully else false
		public boolean setCellData(String SheetName,String ColName,int RowNum, String Data)
		{
			try
			{
				FileInputStream Fis = new FileInputStream(Path); 
				Workbook = new XSSFWorkbook(Fis);
				Fis.close();
				
				if(RowNum<=0)
					return false;
				
				int index = Workbook.getSheetIndex(SheetName);
				int colNum=-1;
				if(index==-1)
					return false;
				
				XSSFSheet sheet = Workbook.getSheetAt(index);
				
				XSSFRow row=sheet.getRow(0);
				for(int i=0;i<row.getLastCellNum();i++)
				{
					if(row.getCell(i).getStringCellValue().trim().equals(ColName.trim()))
						colNum=i;
				}
				if(colNum==-1)
					return false;
	
				sheet.autoSizeColumn(colNum); 
				row = sheet.getRow(RowNum-1);
				if (row == null)
					row = sheet.createRow(RowNum-1);
				
				XSSFCell cell = row.getCell(colNum);	
				if (cell == null)
			        cell = row.createCell(colNum);
	
			    cell.setCellValue(Data);
			    FileOutputStream FileOut = new FileOutputStream(Path);
				Workbook.write(FileOut);
			    FileOut.close();	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}

		// returns true if data is set successfully else false
		public boolean setCellData(String SheetName,String ColName,int RowNum, String Data,String Url)
		{
			try
				{
				FileInputStream Fis = new FileInputStream(Path); 
				Workbook = new XSSFWorkbook(Fis);
	
				if(RowNum<=0)
					return false;
				
				int index = Workbook.getSheetIndex(SheetName);
				int colNum=-1;
				if(index==-1)
					return false;
				
				
				XSSFSheet sheet = Workbook.getSheetAt(index);
				XSSFRow row=sheet.getRow(0);
				for(int i=0;i<row.getLastCellNum();i++)
				{
					if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(ColName))
						colNum=i;
				}
				
				if(colNum==-1)
					return false;
				sheet.autoSizeColumn(colNum);
				row = sheet.getRow(RowNum-1);
				if (row == null)
					row = sheet.createRow(RowNum-1);
				
				XSSFCell cell = row.getCell(colNum);	
				if (cell == null)
					cell = row.createCell(colNum);
					
				cell.setCellValue(Data);
			    XSSFCreationHelper createHelper = Workbook.getCreationHelper();
			    CellStyle hlink_style = Workbook.createCellStyle();
			    XSSFFont hlink_font = Workbook.createFont();
			    hlink_font.setUnderline(XSSFFont.U_SINGLE);
			    hlink_font.setColor(IndexedColors.BLUE.getIndex());
			    hlink_style.setFont(hlink_font);
			    
			    XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
			    link.setAddress(Url);
			    cell.setHyperlink(link);
			    cell.setCellStyle(hlink_style);
			      
			    FileOutputStream FileOut = new FileOutputStream(Path);
				Workbook.write(FileOut);
			    FileOut.close();	

			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// returns true if sheet is created successfully else false
		public boolean addSheet(String  SheetName)
		{
			FileOutputStream fileOut;
			try
			{
				 Workbook.createSheet(SheetName);	
				 fileOut = new FileOutputStream(Path);
				 Workbook.write(fileOut);
			     fileOut.close();		    
			}
			catch (Exception e)
			{			
				e.printStackTrace();
				return false;
			}
			return true;
		}

		// returns true if sheet is removed successfully else false if sheet does not exist
		public boolean removeSheet(String SheetName)
		{		
			int index = Workbook.getSheetIndex(SheetName);
			if(index==-1)
				return false;
			
			FileOutputStream fileOut;
			try
			{
				Workbook.removeSheetAt(index);
				fileOut = new FileOutputStream(Path);
				Workbook.write(fileOut);
			    fileOut.close();		    
			}
			catch (Exception e)
			{			
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		
		
		// returns true if column is created successfully
		public boolean addColumn(String SheetName,String ColName)
		{
			try
			{				
				FileInputStream Fis = new FileInputStream(Path); 
				Workbook = new XSSFWorkbook(Fis);
				int index = Workbook.getSheetIndex(SheetName);
				if(index==-1)
					return false;
				XSSFCellStyle style = Workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				XSSFSheet sheet=Workbook.getSheetAt(index);
				XSSFRow row = sheet.getRow(0);
				if (row == null)
					row = sheet.createRow(0);
				XSSFCell cell;
				if(row.getLastCellNum() == -1)
					cell = row.createCell(0);
				else
					cell = row.createCell(row.getLastCellNum());
		        cell.setCellValue(ColName);
		        cell.setCellStyle(style);
		        FileOutputStream FileOut = new FileOutputStream(Path);
				Workbook.write(FileOut);
			    FileOut.close();		    
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// removes a column and all the contents
		public boolean removeColumn(String sheetName, int colNum)
		{
			try
			{
			if(!isSheetExist(sheetName))
				return false;
			FileInputStream Fis = new FileInputStream(Path); 
			Workbook = new XSSFWorkbook(Fis);
			XSSFSheet Sheet=Workbook.getSheet(sheetName);
			XSSFCellStyle style = Workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			
			style.setFillPattern(HSSFCellStyle.NO_FILL);
			XSSFRow Row=null;
			XSSFCell Column=null;
			for(int i =0;i<getRowCount(sheetName);i++){
				Row=Sheet.getRow(i);	
				if(Row!=null){
					Column=Row.getCell(colNum);
					if(Column!=null){
						Column.setCellStyle(style);
						Row.removeCell(Column);
					}
				}
			}
			FileOutputStream FileOut = new FileOutputStream(Path);
			Workbook.write(FileOut);
		    FileOut.close();
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}

		//String sheetName, String testCaseName,String keyword ,String URL,String message
		public boolean addHyperLink(String SheetName,String ScreenShotColName,String TestCaseName,int index,String url,String message)
		{
			url=url.replace('\\', '/');
			if(!isSheetExist(SheetName))
				 return false;
		    for(int i=2;i<=getRowCount(SheetName);i++)
		    {
		    	if(getCellData(SheetName, 0, i).equalsIgnoreCase(TestCaseName))
		    	{
		    		setCellData(SheetName, ScreenShotColName, i+index, message,url);
		    		break;
		    	}
		    }
			return true; 
		}
		
		public int getCellRowNum(String SheetName,String ColName,String CellValue)
		{
			for(int i=2;i<=getRowCount(SheetName);i++)
			{
		    	if(getCellData(SheetName,ColName , i).equalsIgnoreCase(CellValue))
		    	{
		    		return i;
		    	}
		    }
			return -1;
		}
		
		public List<String[]> getOrderInfoStateForTimeChnage(int sheetIndex)
		{
    		boolean file = isSheetExist("dateTimeChange"+sheetIndex);
    		System.out.println(file);
			if(file == true)
			{
				int columnCount = getColumnCount("dateTimeChange"+sheetIndex);
	    		int rowCount= getRowCount("dateTimeChange"+sheetIndex);
	    		System.out.println(columnCount +"-------------" +rowCount);
	    		//for each action column in the sheet
	    		List<String[]> list = new ArrayList<>();
	    		for(int col=2;col<=columnCount;col++)
	    		{
	    			for(int row=2;row<=rowCount;row++ )
	    			{
	    				
	    				if(getCellData("dateTimeChange"+sheetIndex, col, row)!= "")
	    				{
	    					String pNum = getCellData("dateTimeChange"+sheetIndex, 1, row);
	    					System.out.println(pNum);
	    					//for each purchase order num
	    					String perform = getCellData("dateTimeChange"+sheetIndex, col, row);
	    					System.out.println(perform);
	    					list.add(new String[]{pNum, perform});
	    				}
	    				else
	    				{
	    					System.out.println("NO ORDER IS HERE.... PLEASE GO AHEAD----------------------->>>>>>>>>>>>");
	    				}
	    					
	    			}
	    		}
	    		return list;
			}
			else
			{
				return null;
			}
		}
		
		public List<String[]> getMessageKeyFromProperties(String sheetName)
		{
    		boolean file = isSheetExist(sheetName);
    		System.out.println("Is sheet exists::::"+file);
			if(file == true)
			{
				int columnCount = getColumnCount(sheetName);
	    		int rowCount= getRowCount(sheetName);
	    		System.out.println("columnCount"+columnCount +"-------------rowCount" +rowCount);
	    		
	    		//for each action column in the sheet
	    		List<String[]> list = new ArrayList<>();
	    		List<String[]> emptyList = new ArrayList<>();
	    		for(int col=1;col<=columnCount;col++)
	    		{
	    			for(int row=2;row<=rowCount;row++ )
	    			{
	    				
	    				if(getCellData(sheetName, col, row)!= "")
	    				{
	    					String autoMessageKey = getCellData(sheetName, 1, row);
	    					System.out.println(autoMessageKey);
	    					//for each purchase order num
	    					String perform = getCellData(sheetName, col, row);
	    					System.out.println(perform);
	    					list.add(new String[]{autoMessageKey, perform});
	    				}
	    				else
	    				{
	    					return emptyList;
	    				}
	    					
	    			}
	    		}
	    		return list;
			}
			else
			{
				return null;
			}
		}
		
		
		public List<String[]> checkLastColumnAndGoAhead(String sheetName, String keyName)
		{
    		boolean file = isSheetExist(sheetName);
    		System.out.println("Is sheet exists::::"+file);
			if(file == true)
			{
				int columnCount = getColumnCount(sheetName);
	    		int rowCount= getRowCount(sheetName);
	    		System.out.println("columnCount"+columnCount +"-------------rowCount" +rowCount);
	    		
	    		//for each action column in the sheet
	    		List<String[]> list = new ArrayList<>();
	    		List<String[]> emptyList = new ArrayList<>();
	    		
	    		for(int row=2;row<=rowCount;row++ )
    			{
    				
    				if(getCellData(sheetName, 3, row)!= "")
    				{
    					
    					int lastColumnNumber = Integer.valueOf(getCellData(sheetName, 3, row));
    					
    					if(lastColumnNumber == 0)
    					{
    						String autoMessageKey = getCellData(sheetName, 1, row);
    						
    						if(autoMessageKey.trim().equalsIgnoreCase(keyName))
    						{
    							
    						}
    						
    					}
    					
//    					else if(lastColumnNumber > 0)
//    					{
//    						String autoMessageKey = getCellData(sheetName, 1, row);
//    						
//    						if(autoMessageKey.trim().equalsIgnoreCase(keyName))
//    						{
//    							
//    						}
//    					}
//    					String autoMessageKey = getCellData(sheetName, 1, row);
//    					System.out.println(autoMessageKey);
//    					//for each purchase order num
//    					String perform = getCellData(sheetName, col, row);
//    					System.out.println(perform);
//    					list.add(new String[]{autoMessageKey, perform});
    				}
    				else
    				{
    					return emptyList;
    				}
    					
    			}
	    		
	    		
	    		
	    		
	    		
	    		
	    		for(int col=1;col<=columnCount;col++)
	    		{
	    			for(int row=2;row<=rowCount;row++ )
	    			{
	    				
	    				if(getCellData(sheetName, col, row)!= "")
	    				{
	    					String autoMessageKey = getCellData(sheetName, 1, row);
	    					System.out.println(autoMessageKey);
	    					//for each purchase order num
	    					String perform = getCellData(sheetName, col, row);
	    					System.out.println(perform);
	    					list.add(new String[]{autoMessageKey, perform});
	    				}
	    				else
	    				{
	    					return emptyList;
	    				}
	    					
	    			}
	    		}
	    		return list;
			}
			else
			{
				return null;
			}
		}
		
		
		
}



