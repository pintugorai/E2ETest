package com.qa.utils;

//import java.io.IOException;

import com.ensimtest.resource.Logger;

/**
 * Be careful about this class as this will lead the change of date & time in the system.
 * This codes need to be run in 'Administrator' mode.
 * @author Chiranjit
 *
 */
public class DateTimeChangeHandler
{
	/**
	 * WARNING: This will change the system date & time!
	 * It Takes both parameter of date and time. But if one is provided NULL,
	 * the method does not run that.
	 * @param newDate: 'MM-dd-yy'
	 * @param newTime: 'hh:mm:ss'
	 */
	public void changeDateTime(String newDate, String newTime)
	{
		Logger.log("Going to change date-time to: " + newDate + ": " + newTime);
		TestUtils utility = new TestUtils();
		String osProp = utility.getOSInfo().toLowerCase();
		//System.out.println("<<---------"+ osProp);
		if(osProp.contains("windows"))
		{
			changeDateTimeInWindows(newDate, newTime);
		}
		else
		{
			changeDateTimeInLinux(newDate, newTime);
		}
	}
	
	private void changeDateTimeInLinux(String newDate, String newTime)
	{
		// TODO Auto-generated method stub
	}
	public static boolean xecuteCmd( String command )
	{
		try 
		{
			Process p = Runtime.getRuntime().exec( command );	
			Thread.sleep(2000);
			//p.destroy();
		} catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void changeDateTimeInWindows(String newDate, String newTime)
	{
		String cmdFirstPartForTime = "runas /profile /user:Administrator /savecred \"cmd.exe /c time ";
		String cmdFirstPartForDate = "runas /profile /user:Administrator /savecred \"cmd.exe /c date ";
		String closeCMD = "runas /profile /user:Administrator /savecred \"cmd.exe /c taskkill /f /im cmd.exe\"";
		boolean isDateChanged = newDate != null?xecuteCmd(cmdFirstPartForDate + newDate):false;
		boolean isTimeChanged = newTime != null?xecuteCmd(cmdFirstPartForTime + newTime):false;
		xecuteCmd(closeCMD);

		System.out.println("isDateChanged -->" + isDateChanged);
		System.out.println("isTimeChanged -->" + isTimeChanged);
		
		//System.out.println("isDateChanged --> " + newDate != null?false:xecuteCmd(newDate) + "isTimeChanged --> " + newTime != null?false:xecuteCmd(newTime));

/*		if(newDate != null)
		{
			try
			{
			//	Process p = Runtime.getRuntime().exec("cmd /C date " + newDate);
				Process p = Runtime.getRuntime().exec("runas /profile /user:Administrator /savecred \"cmd.exe /c time " + newDate);

			}
			catch (Exception e)
			{
				System.out.println(e);
				Logger.log(e.getMessage());
			}
		}
		
		if(newTime != null)
		{
			try
			{
				Runtime.getRuntime().exec("runas /profile /user:Administrator /savecred \"cmd.exe /c time " + newTime);
			}
			catch (Exception e)
			{
				System.out.println(e);
				Logger.log(e.getMessage());
			}
*/		}

/*	private void changeDateTimeInWindows(String newDate, String newTime)
	{
		if(newDate != null)
		{
			try
			{
			//	Process p = Runtime.getRuntime().exec("cmd /C date " + newDate);
				Process p = Runtime.getRuntime().exec("runas /profile /user:Administrator /savecred \"cmd.exe /c time " + newDate);

			}
			catch (Exception e)
			{
				System.out.println(e);
				Logger.log(e.getMessage());
			}
		}
		
		if(newTime != null)
		{
			try
			{
				Runtime.getRuntime().exec("runas /profile /user:Administrator /savecred \"cmd.exe /c time " + newTime);
			}
			catch (Exception e)
			{
				System.out.println(e);
				Logger.log(e.getMessage());
			}
		}
	}*/
}
