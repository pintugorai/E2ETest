package com.qa.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XLSFileOperation
{
	public class XLSFileMatchResult
	{
		public boolean isMatch = false;
		public List<String> mismatches = new ArrayList<>();
		public String errorMsg = "<Default #erroMsg>";
	}

	public XLSFileMatchResult excelMatching(List<String> activityLogAtRunTime, String excelFile) 
	{
		FileInputStream expectedXL = null;
		XLSFileMatchResult result = new XLSFileMatchResult();
		result.isMatch = true;
		try
		{
			expectedXL = new FileInputStream(new File(excelFile)); 
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
			result.errorMsg = e.toString();
			return result;
		}
		
		Workbook workbook2 = null;
		try 
		{
			workbook2 = new XSSFWorkbook(expectedXL);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			result.errorMsg = e.toString();
			return result;
		}
        
        Sheet sheet2 = workbook2.getSheetAt(0);
        System.out.println(activityLogAtRunTime.size()+"---activity log -row@@@@@@@@@@@@@@@@@@@-----"+(sheet2.getLastRowNum()+1));
		if(activityLogAtRunTime.size()>(sheet2.getLastRowNum()+1))
		{
			System.out.println("actual sheet has more row");
			result.errorMsg = "actual sheet has more row";
			return result;
		}
		else
		{
			if(activityLogAtRunTime.size()<(sheet2.getLastRowNum()+1))
			{
				System.out.println("expected sheet has more row");
				result.errorMsg = "expected sheet has more row";
				return result;
			}
			else
			{
				boolean isMatched;
				int noOfRows = activityLogAtRunTime.size();
				
				for(int rowPosition=0; rowPosition<noOfRows; rowPosition++)
				{
					String finalRowInExcel = sheet2.getRow(rowPosition).getCell(0).getStringCellValue().trim();
					isMatched = isMatchFound(activityLogAtRunTime, finalRowInExcel);
					if(!isMatched)
					{
						System.out.println("Mismatch found");
						result.isMatch = false;
						result.errorMsg = "Failed for excel record: " + finalRowInExcel;
					}
				}
			}
			return result;
		}
	}
	
	private boolean isMatchFound(List<String> activityLogAtRunTime, String finalRowInExcel)
	{
		System.out.println("Row in excel: " + finalRowInExcel);
		boolean isMatched = false;
		
		for(int i=0;i<activityLogAtRunTime.size();i++)
		{
			String Actualvalue = activityLogAtRunTime.get(i).trim();
			System.out.println("\tMatching with: " + Actualvalue);
			if(Actualvalue.trim().equals(finalRowInExcel.trim()))
			{
				isMatched = true;
				break;
			}
		}
		return isMatched;
	}
	
	// Compare Two Cells
    public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
   
        if((cell1 == null) && (cell2 == null)) {
            return true;
        } else if((cell1 == null) || (cell2 == null)) {
            return false;
        }
        
        boolean equalCells = false;
        int type1 = cell1.getCellType();
        int type2 = cell2.getCellType();
        if (type1 == type2) 
        {
            if (cell1.getCellStyle().equals(cell2.getCellStyle())) 
            {
                // Compare cells based on its type
//            	try
//            	{
                switch (cell1.getCellType()) 
                {
                	case XSSFCell.CELL_TYPE_FORMULA:
                    if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                        equalCells = true;
                    }
                    break;
                    
                    
                	case XSSFCell.CELL_TYPE_STRING:
                	
                		if(cell1.getStringCellValue() == null && cell2.getStringCellValue() == null)
                    	{
                    		System.out.println("Null value is converted to String.............");
                    		equalCells = true;
                    	}
                		
	                	else if (cell1.getStringCellValue().equalsIgnoreCase(cell2.getStringCellValue())) 
	                    {
	                    	System.out.println("cell1.getStringCellValue()---------------------->"+cell1.getStringCellValue());
	                        equalCells = true;
	                    }
                      
                    break;
                    
                case XSSFCell.CELL_TYPE_NUMERIC:
                	
                	cell1.setCellType(Cell.CELL_TYPE_STRING);
            		cell2.setCellType(Cell.CELL_TYPE_STRING);
            		if(cell1.getStringCellValue().equalsIgnoreCase(cell2.getStringCellValue()))
            		{
            			System.out.println(cell1.getStringCellValue()+"  ---Somehow handled ---  "+cell2.getStringCellValue());
            			equalCells = true;
            		}
                	
//                        if(cell1.getNumericCellValue() == cell1.getNumericCellValue())
//                        equalCells = true;
                  
                    break;
                    
                    
                case XSSFCell.CELL_TYPE_BLANK:
                    if (cell2.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
                    	System.out.println("This cell is blank..");
                        equalCells = true;
                    }
                    break;
                    
                case XSSFCell.CELL_TYPE_BOOLEAN:
                    if (cell1.getBooleanCellValue() == cell2
                            .getBooleanCellValue()) {
                        equalCells = true;
                    }
                    break;
                case XSSFCell.CELL_TYPE_ERROR:
                    if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                        equalCells = true;
                    }
                    break;
                default:
                    if (cell1.getStringCellValue().equalsIgnoreCase(cell2
                            .getStringCellValue())) {
                    	System.out.println("cell1.getStringCellValue()---------------------->"+cell1.getStringCellValue());
                    	
                        equalCells = true;
                    }
                    break;
                }
                
 //           	}
            	
//            	catch(Exception e)
//            	{
//            		cell1.setCellType(Cell.CELL_TYPE_STRING);
//            		cell2.setCellType(Cell.CELL_TYPE_STRING);
//            		if(cell1.getStringCellValue().equalsIgnoreCase(cell2.getStringCellValue()))
//            		{
//            			System.out.println(cell1.getStringCellValue()+"  ---Somehow Exception occurred ---  "+cell2.getStringCellValue());
//            			equalCells = true;
//            		}
//            	}
            } 
            else 
            {
                return false;
            }
        } 
        else 
        {
            return false;
        }
        return equalCells;
    }
    
    
    
 // Compare Two Rows
    public static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) 
    {
        if((row1 == null) && (row2 == null)) 
        {
            return true;
        } 
        else if((row1 == null) || (row2 == null)) 
        {
            return false;
        }
        
        int firstCell1 = row1.getFirstCellNum();
        int lastCell1 = row1.getLastCellNum();
        boolean equalRows = true;
        
        // Compare all cells in a row
        for(int i=firstCell1; i <= lastCell1; i++) 
        {
            XSSFCell cell1 = row1.getCell(i);
            XSSFCell cell2 = row2.getCell(i);
            if(!compareTwoCells(cell1, cell2)) 
            {
                equalRows = false;
                System.out.println("       Cell "+i+" - Not Equal");
                break;
            }
            else 
            {
                System.out.println("       Cell "+i+" - Equal");
            }
        }
        return equalRows;
    }
    
    
 // Compare Two Sheets
    public static boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2)
    {
        int firstRow1 = sheet1.getFirstRowNum();
        int lastRow1 = sheet1.getLastRowNum();
        boolean equalSheets = true;
        for(int i=firstRow1; i <= lastRow1; i++) {
            
            System.out.println("\n\nComparing Row "+i);
            
            XSSFRow row1 = sheet1.getRow(i);
            XSSFRow row2 = sheet2.getRow(i);
            if(!compareTwoRows(row1, row2)) 
            {
                equalSheets = false;
                System.out.println("Row "+i+" - Not Equal");
                break;
            } 
            else 
            {
                System.out.println("Row "+i+" - Equal");
            }
        }
        return equalSheets;
    }
    
    public void compareExcelFiles(String ExpectedExcelFile, String ActualExcelFile) 
    {
        try {
            // get input excel files
        	copyExcel(ExpectedExcelFile);
            FileInputStream excellFile1 = new FileInputStream(new File(ExpectedExcelFile));
            copyExcel(ActualExcelFile);
            FileInputStream excellFile2 = new FileInputStream(new File(ActualExcelFile));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);

            // Get first sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            XSSFSheet sheet2 = workbook2.getSheetAt(0);

            // Compare sheets
            if(compareTwoSheets(sheet1, sheet2)) {
                System.out.println("\n\nThe two excel sheets are Equal");
            } else {
                System.out.println("\n\nThe two excel sheets are Not Equal");
            }
            
            //close files
            excellFile1.close();
            excellFile2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }  
    
    public void copyExcel(String fileName)
	{
		InputStream excelFileToRead = null;
		try
		{
			excelFileToRead = new FileInputStream(fileName);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		XSSFWorkbook wb = null;
	
		try 
		{
			wb = new XSSFWorkbook(excelFileToRead);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}


		XSSFSheet ws=wb.getSheetAt(0);
		ws.setForceFormulaRecalculation(true);
	
	
		int rowNum = ws.getLastRowNum() + 1;
        int colNum = ws.getRow(0).getLastCellNum();

        
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(ws.getSheetName());
        sheet.createRow(0);
        // Read the headers first. Locate the ones you need
        XSSFRow rowHeader = ws.getRow(0);
        for (int j = 0; j < colNum; j++) 
        {
            XSSFCell cell = rowHeader.getCell(j);
            String cellValue = cellToString(cell);
            sheet.getRow(0).createCell(j).setCellValue(cellValue);          
        }
        
        
        for (int i = 1; i < rowNum; i++) 
        {
             
             sheet.createRow(i);
             for (int j = 0; j < colNum; j++) 
 	         {
            	 XSSFRow row = ws.getRow(i);
            	 XSSFCell cell = row.getCell(j);
 	             String cellValue = cellToString(cell);
 	             sheet.getRow(i).createCell(j).setCellValue(cellValue);
 	         }
         }
        
        try 
        {
			excelFileToRead.close();
		} 
        catch (IOException e)
        {
			e.printStackTrace();
		}
        
        FileOutputStream output_file = null;
		try 
		{
			output_file = new FileOutputStream(new File(fileName));

			workbook.write(output_file);
			
			output_file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
       
	}
    
    public static String cellToString(XSSFCell cell) 
	{
        int type;
        Object result = null;
        try
        {
	        type = cell.getCellType();
        }
        catch(Exception e)
        {
        	type = 3;
        }
        switch (type) {

        case XSSFCell.CELL_TYPE_NUMERIC:
        	cell.setCellType(Cell.CELL_TYPE_STRING);
        	if(!cell.getStringCellValue().equals(""))
        	{
        		result = Double.parseDouble(cell.getStringCellValue());
        	}
        	else
        	{
        		result = cell.getStringCellValue();
        	}
            break;
        case XSSFCell.CELL_TYPE_STRING:
            result = cell.getStringCellValue();
            break;
        case XSSFCell.CELL_TYPE_BLANK:
            result = "";
            break;
        case XSSFCell.CELL_TYPE_FORMULA:
            result = cell.getCellFormula();
            break;
        default:
        	result = "";
        }
        if(result== null)
        {
        	return "";
        }
        else
        {
        	return result.toString();
        }
	        
    }
    
}