package com.qa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.SkipException;

import com.ensimtest.config.Element;
import com.ensimtest.resource.TestConfigHandler;
import com.ensimtest.resource.XLSFileReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils
{
	/**
	 * This gives a delay for milisec
	 * @param milisec : time unit in milisec
	 */
	public static void delay(int milisec)
	{
		/*try
		{
			Thread.sleep(2*milisec);
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}*/
		Element.WaitForReady();
	}
	public static void delayStatic(int milisec){
		try
		{
			Thread.sleep(2*milisec);
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}
	}

	public static void checkSuitRunnable(Object className)
	{
		String suiteFilePath = "resources\\testdata\\TestSuite.xlsx";
		XLSFileReader xlsFileReader=new XLSFileReader(suiteFilePath);
		TestConfigHandler testConfigHandler=new TestConfigHandler();
		if( testConfigHandler.isSuiteRunnable(xlsFileReader, className.getClass().getSimpleName())==false)
		{
			xlsFileReader=null;
			testConfigHandler=null;
			throw new SkipException("Test Suite "+className.getClass().getSimpleName()+" is runnable for this build/test cycle");
		}
	}
	
	/**
	 * Returns the Unicode character array in which both
	 * strings got the difference
	 * @param s1: String 1
	 * @param s2: String 2
	 * @return : array of unicode character as form of int
	 */
	public int[] compareUnicodeTexts(String s1, String s2)
	{
		s1 = s1.trim();
		s2 = s2.trim();
		int count = 0;
		for(int i=0; i<s1.length(); i++)
		{
			count = 0;
			for(int j=0; j<s2.length(); j++)
			{
				//System.out.println("s1 " + s1.codePointAt(i+j) + " s2 = " + s2.codePointAt(j));
				if( s1.codePointAt(i+j)==s2.codePointAt(j) )
				{
					//System.out.println(s2.codePointAt(j) + "<<");
					++count;
				}
				else
				{
					//System.out.println(count);
					if(count > 0)
						return new int[]{s1.codePointAt(i+j), s2.codePointAt(j)};
					else
						break;
				}
			}
		}
		return new int[]{0,0};
	}
	
	
	//=============================================Start of date updater
	/**
	 * 
	 * updates current system date by give seconds
	 * @param secOffset
	 * @author SubhaKundu
	 *
	 */
 	public class DateUpdater
 	{
 		public String year,month,date,hour,minute,second;
 		
 		public DateUpdater()
 		{
 			year = month = date = hour = minute = second = "";
 		}
 		
 		/**
 		 * Get updated date-timestamp
 		 * @param secondToIncrease : Seconds to increase in date-timestamp
 		 */
 		public DateUpdater(int secondToIncrease)
 		{
 			String dateString;
 			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
 			Date dcurrent = new Date();
 			dateString = dcurrent.toString();
 			//System.out.println("current date time..."+sdf.format(dcurrent)+"...in String format... "+dateString);
 			Date dRevised = new Date(System.currentTimeMillis()+ secondToIncrease*1000);
 			dateString = sdf.format(dRevised).toString();
 			//System.out.println("revised date time..."+sdf.format(dRevised)+"...in String format... "+dateString);
 			String []info = dateString.split("-");
 			year = info[0];
		  	month = info[1];
		 	date = info[2];
		  	hour = info[3];
		  	minute = info[4];
		  	second = info[5];
		  //	System.out.println("inside DateUpdater..."+date+"/"+month +"/"+year+"/"+ hour+"/"+ minute);
 		}
 	} 
	//==================================================End of date updater
 	
 	public boolean matchPattern(String lineToCheckForPatternMatch, String pattern)
 	{
 		Pattern regexFound = Pattern.compile(pattern);
		Matcher matchToFind = regexFound.matcher(lineToCheckForPatternMatch);
		return matchToFind.find();
 	}
	
 	public String getOSInfo()
 	{
 		return System.getProperty("os.name");
 	}
}
