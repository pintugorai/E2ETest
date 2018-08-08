package com.qa.utils;

import java.io.IOException;

public class ServerManager
{
	/**
	 * Make sure %CATALINA_HOME% is set
	 */
	public void stopTomcat()
	{
		try
		{
			Process p = Runtime.getRuntime().exec("cmd /C catalina stop");
			p.waitFor();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		catch (InterruptedException e)
		{
			System.out.println("Interrrupted: " + e);
		}
	}
	
	/**
	 * Make sure %CATALINA_HOME% is set
	 */
	public void startTomcat()
	{
		try
		{
			Process p = Runtime.getRuntime().exec("cmd /C catalina start");
			p.waitFor();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		catch (InterruptedException e)
		{
			System.out.println("Interrrupted: " + e);
		}
	}
}
