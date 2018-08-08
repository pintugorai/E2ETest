package com.qa.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.Driver;
import com.qa.resources.Logger;
import com.qa.resources.PropertyReader;
import com.qa.utils.TestUtils;

import org.openqa.selenium.JavascriptExecutor;

public class Browser {

	private DriverSettings driverSetting;
	private int timeToWait = 1; //30
	private final String defaultBrowser = "firefox";
	private static String baseURL;
	private static String browserName = null;

	public Browser()
	{
		PropertyReader pr;
		try
		{
			pr = new PropertyReader();
			baseURL=pr.getURL();
			browserName=pr.getBrowserName();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if(browserName == null)
			browserName = defaultBrowser;

		driverSetting = new DriverSettings();
		driverSetting.setUpDriver(browserName, timeToWait);
	}

	public Browser(String browserName)
	{
		driverSetting = new DriverSettings();
		driverSetting.setUpDriver(browserName, timeToWait);
	}

	public Browser(String browserName, int timeUnitInSecond)
	{
		driverSetting = new DriverSettings();
		timeToWait = timeUnitInSecond;
		driverSetting.setUpDriver(browserName, timeUnitInSecond);
	}

	public void close()
	{
		try
		{
			driverSetting.closeDriver();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void navigateTo()
	{
		navigateTo(baseURL);

		Element.WaitForReady();
	}

	/**
	 * Go to the URL mentioned
	 * @param URL : String containing URL of the location
	 */
	public void navigateTo(String URL)
	{
		System.out.println("Navigating to : " + URL);
		Logger.log("Navigating to : " + URL);
		
		Driver.driver.get(URL);
		if(true)
		{
			try
			{
				System.out.println("Running Java-script");
				Driver.driver.navigate().to("javascript:document.getElementById('overridelink').click()");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

		}
	}

	public void refress()
	{
		Driver.driver.navigate().refresh();
	}

	/**
	 * Read the URL of the current page
	 * @return : String URL
	 */
	public String getCurrntUrl()
	{
		return Driver.driver.getCurrentUrl();
	}

	/**
	 * DON'T USE IT NOW. THIS METHOD IS NOT IMPLEMENTED COMPLETELY.
	 * ------------------------------------------------------------
	 * Takes Screen-shot of the current page.
	 */
	public void takeScreenShot()
	{
		File scrFile = ((TakesScreenshot)Driver.driver).getScreenshotAs(OutputType.FILE);
		try
		{
			FileUtils.copyFile(scrFile, new File("\\screenshot.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void setWait(int milisec)
	{
		driverSetting.setWait(milisec);
	}

	public void setDefaultWait()
	{
		driverSetting.setWait(timeToWait);
	}

	public void waitForElement(Element element, long maxTimeoutMiliSec) throws Exception
	{
		if(maxTimeoutMiliSec<=0)
		{
			throw new Exception("In-correct time out enetered");
		}

		int maxCount=(int) (maxTimeoutMiliSec/(30*1000));

		int count=0;
		do
		{
			try
			{
				if(element.isDisplayed()==true)
				{
					return;
				}
			}
			finally
			{
				;
			}
			TestUtils.delayStatic(5000);
			count++;
		}
		while(count < maxCount);
	}

	public String getWindowHandel()
	{
		return driverSetting.getCurrentWindowhandel();
	}

	public Set<String> getallWindowHandels()
	{
		return driverSetting.getallWindowHandels();
	}

	public void switchToWindow(String handel)
	{
		driverSetting.switchToWindow(handel);
	}
	public void closeWindow()
	{
		driverSetting.closeDriver();
	}
	
	public String executeScripts(String msg, Element el)
	{
		return ((JavascriptExecutor)Driver.driver).executeScript(msg, el).toString();
	}

	
	public void navigateToSignUp()
	{
		int pos = baseURL.indexOf("/escm/");
		String url = baseURL.substring(0, pos) + "/escm/signup";
		navigateTo(url);
	}
	
	public ArrayList<String> switchToWindow()
	{
		ArrayList<String> tabs = new ArrayList<String> (Driver.driver.getWindowHandles());
		return tabs;
	}
	
	public Browser(boolean flag)
	{
		;
	}
	
	public void switchToFrame(Element el)
	{
		driverSetting.switchToFrame(el);
	}
	
	public void switchToParentFrame()
	{
	   driverSetting.switchToParentFrame();
	}
}
