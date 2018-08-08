package com.qa.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


//import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.qa.base.Driver;
import com.qa.resources.FetchFilesFromDir;
import com.qa.resources.PropertyReader;
import com.qa.resources.TestDataDirType;
import com.qa.utils.TestUtils;



public class DriverSettings
{
	/**
	 * Overloaded browser setup with browser name and time-out being
	 * sent as parameter
	 * @param browserName : name of the browser (like: firefox, ie, chrome) case insensitive
	 * @param timeUnitInSecond : Timeout second for implicit time-out
	 */
	public void setUpDriver(String browserName, int timeUnitInSecond)
	{
		DesiredCapabilities cap = null;
		switch(browserName.trim().toLowerCase())
		{
		case "firefox" : 
			cap = DesiredCapabilities.firefox();
			Driver.driver = new FirefoxDriver(cap);
			break;
		case "ie" :
			System.setProperty("webdriver.ie.driver", "resources\\thirdparty\\IEDriverServerx32.exe");
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability("ie.ensureCleanSession", true);
			Driver.driver = new InternetExplorerDriver(cap);
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", "resources\\thirdparty\\MicrosoftWebDriver.exe");
			//DesiredCapabilities browser1 = DesiredCapabilities.edge();
			//browser1.setBrowserName(DesiredCapabilities.edge().getBrowserName());
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			Driver.driver = new EdgeDriver(capabilities);
			break;
		case "chrome" :
			System.setProperty("webdriver.chrome.driver", "resources\\thirdparty\\chromedriver.exe");
			//added by subha
			Map<String, Object> prefs = new HashMap<String, Object>();
			FetchFilesFromDir fetch = new FetchFilesFromDir();
			try
			{
				System.out.println(new File(fetch.getFilePath(TestDataDirType.DownloadSample, "")).getCanonicalFile());
				prefs.put("download.default_directory", new File(fetch.getFilePath(TestDataDirType.DownloadSample, "")).getCanonicalFile().toString());
				prefs.put("safebrowsing.enabled", "true"); 
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			//prefs.put("download.default_directory", fetch.getFilePath(TestDataDirType.DownloadSample, ""));
			//end of subha
			cap = DesiredCapabilities.chrome();
			//added by subha
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			//end of subha
			Driver.driver = new ChromeDriver(cap);
			break;
//		case "safari":
//			SafariOptions options = new SafariOptions();
//			options.setUseCleanSession(true);
//			Driver.driver = new SafariDriver(options); 
//			break;
//		case "opera":
//			break;
//		case "edge" :
//			break;
		default : 
			cap = DesiredCapabilities.firefox();
			break;
		}
		setWait(timeUnitInSecond);
		setBrowserAtMaxSize();
	}
	
	/**
	 * Sets the implicit time out as parameter
	 * @param timeUnitInSecond : Timeout second for implicit time-out
	 */
	protected void setWait(int timeUnitInSecond)
	{
		Driver.driver.manage().timeouts().implicitlyWait(timeUnitInSecond, TimeUnit.SECONDS);
	}
	
	/**
	 * Sets the browser at maximum size of the screen
	 */
	protected void setBrowserAtMaxSize()
	{
		Driver.driver.manage().window().maximize();
//		Dimension d = Driver.driver.manage().window().getSize();
//		Dimension dd = new Dimension(d.width-50, d.height-50);
//		Driver.driver.manage().window().setSize(dd);
	}
	
	/**
	 * Closes the driver, deletes all cookies
	 */
	public void closeDriver()
	{
		//Driver.driver.manage().deleteAllCookies();
		Driver.driver.close();
		Driver.driver.quit();
	}
	
	public void closeWindow()
	{
		Driver.driver.close();
	}
	
	public String getCurrentWindowhandel()
	{
		return Driver.driver.getWindowHandle();
	}
	
	public Set<String> getallWindowHandels()
	{
		return Driver.driver.getWindowHandles();
	}
	
	public void switchToWindow(String handel)
	{
		Driver.driver.switchTo().window(handel);
	}
	
	public static void syncSeapMode(String userJSON)
	{
		Driver.driver.close();
		Driver.driver.quit();
		TestUtils.delay(6000);
		
//		DesiredCapabilities cap = null;
//		cap = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile();
		
		try {
			profile.addExtension(new File("resources\\thirdparty\\modify_headers-0.7.1.1-fx.xpi"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		profile.setPreference("modifyheaders.config.active", true);
		profile.setPreference("modifyheaders.config.alwaysOn", true);
		profile.setPreference("modifyheaders.headers.count",7);
		
		
		profile.setPreference("modifyheaders.headers.action0", "Add");
		profile.setPreference("modifyheaders.headers.name0", "iv-user");
		profile.setPreference("modifyheaders.headers.value0", "adminpra");
		profile.setPreference("modifyheaders.headers.enabled0", true);
		
		profile.setPreference("modifyheaders.headers.action1", "Add");
		profile.setPreference("modifyheaders.headers.name1", "mail");
		profile.setPreference("modifyheaders.headers.value1", "hjjhg@fg.bhu");
		profile.setPreference("modifyheaders.headers.enabled1", true);
		
		profile.setPreference("modifyheaders.headers.action2", "Add");
		profile.setPreference("modifyheaders.headers.name2", "iv-groups");
		profile.setPreference("modifyheaders.headers.value2", "'SERVICEPROVIDER SA'");
		profile.setPreference("modifyheaders.headers.enabled2", true);

		profile.setPreference("modifyheaders.headers.action3", "Add");
		profile.setPreference("modifyheaders.headers.name3", "cn");
		profile.setPreference("modifyheaders.headers.value3", "sp");
		profile.setPreference("modifyheaders.headers.enabled3", true);
		
		profile.setPreference("modifyheaders.headers.action4", "Add");
		profile.setPreference("modifyheaders.headers.name4", "sn");
		profile.setPreference("modifyheaders.headers.value4", "admin");
		profile.setPreference("modifyheaders.headers.enabled4", true);
		
		profile.setPreference("modifyheaders.headers.action5", "Add");
		profile.setPreference("modifyheaders.headers.name5", "preferredLanguage");
		profile.setPreference("modifyheaders.headers.value5", "en");
		profile.setPreference("modifyheaders.headers.enabled5", true);
		
		profile.setPreference("modifyheaders.headers.action6", "Add");
		profile.setPreference("modifyheaders.headers.name6", "globalid");
		profile.setPreference("modifyheaders.headers.value6", "1234admin");
		profile.setPreference("modifyheaders.headers.enabled6", true);
		
		Driver.driver = new FirefoxDriver(profile);
		
		PropertyReader pr;
		String urlSeap = "";
		try
		{
			pr = new PropertyReader();
			urlSeap=pr.getURL();
		}
		catch (IOException e)
		{
			System.out.println("Url not found");
			e.printStackTrace();
		}
		DriverSettings settings = new DriverSettings();
		settings.setWait(30);
		settings.setBrowserAtMaxSize();
		Driver.driver.get(urlSeap);
	}
	
	public void switchToFrame(Element el)
	 {
	  Driver.driver.switchTo().frame(el.getWebElement());
	  
	 }
	
	public void switchToParentFrame() {
		Driver.driver.switchTo().parentFrame();
	}
	
}