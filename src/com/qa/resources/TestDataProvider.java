package com.qa.resources;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.List;

//import java.util.HashMap;
import org.testng.annotations.DataProvider;

import com.ensimtest.resource.TestConfigHandler;

public class TestDataProvider {

	@DataProvider(name = "TestData")
	public static Object[][] fetchData(Method methodName)
	{
		
		String fileName = ResReference.testData+methodName.getDeclaringClass().getSimpleName().trim();
		// Check if xml exists
		File f = new File(fileName+".xml");
		if(f.exists())
		{
			// Get the xml data as per HashMap
			System.out.println("File existed");
			return null;
		}
		else
		{
			f = new File(fileName+".xlsx");
			if(f.exists())
			{
				XLSFileReader xlsRead=new XLSFileReader(fileName+".xlsx");
				TestConfigHandler tu=new TestConfigHandler();
				Object result[][] = tu.getData(xlsRead, methodName.getName());
				return getTrimmedRows(result);
				//return result;
			}
			else
			{
				return null;
			}
		}
	}
	
	private static Object[][] getTrimmedRows(Object[][] obj)
	{
		List<Object []> listOfFilled = new ArrayList<>();
		for(int i=0; i<obj.length; i++)
		{
			if(obj[i][0]!=null)
			{
				if(obj[i][0].toString().trim().equals("")==false)
				{
					listOfFilled.add(obj[i]);
				}
			}
		}
		
		// Form Object array
		Object[][] resultObj = new Object[listOfFilled.size()][];
		for(int i=0; i<listOfFilled.size(); i++)
		{
			resultObj[i] = listOfFilled.get(i);
		}
		
		return resultObj;
	}
}
