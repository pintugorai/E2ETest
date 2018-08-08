package com.qa.config;

import java.util.List;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.interactions.HasInputDevices; 
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.qa.base.Driver;
import com.qa.base.ElementType;
import com.qa.base.IElement;
import com.qa.resources.Logger;
import com.qa.utils.ScreenShotManager;
import com.qa.utils.TestUtils;
/**
 * This implements IElements along with some additional methods
 * @author Chiranjit
 *
 */
public class Element implements IElement
{
	private static int loopCount = 50;
	private static int MAXIMUM_WAIT_TIME = 3500;
	private WebElement webElement;
	private String elementName;
	private ElementType type;
	private ElementHandler handler;
	
	private ScreenShotManager screenShot = new ScreenShotManager();
	
	public static boolean isPageLoaded ()
	{	
		for ( int i = 0; i < loopCount ; i++ )
		try {
			   if (((JavascriptExecutor) Driver.driver).executeScript("return document.readyState;").equals("complete")) 
			   {
				   System.out.println("Waiting for the page to load completely ...........");
				   return true;
			   }
		} catch (Exception e) {
			try {
				Thread.sleep(MAXIMUM_WAIT_TIME);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			continue;
		}
		return false;
	}

	public Element()
	{
		type = null;
		handler = new ElementHandler();
	}

	/**
	 * This sets the elements with element types(ID, XPath, etc.) and elements names
	 * @param elementType : Type of element to identify the element as string
	 * @param elementName : Value related to the type
	 */
	protected void setElement(String elementType, String elementName)
	{
		this.elementName = elementName;
		this.type = handler.getElementType(elementType);;
	}

	/**
	 * This sets the elements with element types(ID, XPath, etc.) and elements names
	 * @param elementType : Type of element to identify the element
	 * @param elementName : Value related to the type
	 */
	protected void setElement(ElementType elementType, String elementName)
	{
		this.elementName = elementName;
		this.type = elementType;
	}

	/**
	 * This sets the elements with String array data
	 * @param elementInfo : String(0) contains key, String(1) contains value 
	 */
	protected void setElement(String []elementInfo)
	{
		setElement(elementInfo[0], elementInfo[1]);
	}

	protected void setElement(WebElement element)
	{
		this.webElement = element;
	}

	protected WebElement getWebElement()
	{
		if(this.type==null)
			return webElement;
		else
			return getReloadedElement(elementName, type);
	}

	protected Element getElement()
	{
		return this;
	}
	
	private void bringToFront(WebElement webElement)
	{
		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		handler.moveMouseToElement(webElement);
	}

	@Override
	public void click() {
//		try 
//		{
//			Thread.sleep(60 * 3 * 1000);
//		} 
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
		 isPageLoaded();

		screenShot.takeContinuousScreenShot();
		
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clicking on : " + instance);
		 if (isPageLoaded())
		 {
		for(int i=0; i<loopCount; i++)
		{
			try
			{
				
				webElement = getReloadedElement(elementName, type);
				//bringToFront(webElement);
				webElement.click();
				TestUtils.delay(1);
				break;
			}
			catch(Exception e)
			{
				System.out.println(">>" + elementName);
				System.out.println("loop continued .....");

				try {
					Thread.sleep(MAXIMUM_WAIT_TIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		}
	}
	
	public void clickE() {
//		try 
//		{
//			Thread.sleep(60 * 3 * 1000);
//		} 
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clicking on : " + instance);
		 if (isPageLoaded())
		 {
		for(int i=0; i<loopCount; i++)
		{
			try
			{
				
				webElement = getReloadedElement(elementName, type);
				webElement.click();
				break;
			}
			catch(ElementNotVisibleException e)
			{
				System.out.println("loop continued .....");

				try {
					Thread.sleep(MAXIMUM_WAIT_TIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		}
		
	}

	@Override
	public boolean isExists() {
		
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Checking existance of " + instance);
		
		try
		{
			webElement = getReloadedElement(elementName, type);
			return webElement.isDisplayed() && webElement.isEnabled();
		}
		catch(ElementNotFoundException e)
		{
			return false;
		}
	}

	@Override
	public boolean isDisplayed() {
		
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Checking if " + instance + " is Displayed");
		
		try
		{
			webElement = getReloadedElement(elementName, type);
			//bringToFront(webElement);
			if(webElement == null)
			{
				System.out.println("null element");
				return false;
			}
			else
			{
				return webElement.isDisplayed();
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public String read() {
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Reading text of " + instance);
		
		webElement = getReloadedElement(elementName, type);
		return webElement.getText();
	}

	@Override
	public void write(String msg) {
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Writing on " + instance + " With value: " + msg);
		
		webElement = getReloadedElement(elementName, type);
		//bringToFront(webElement);
		webElement.sendKeys(msg);
	}


	public void clearAndWrite(String msg) {
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clearing and writing on " + instance + " With value: " + msg);
		
		webElement = getReloadedElement(elementName, type);
		webElement.clear();
		TestUtils.delay(1000);
		webElement.sendKeys(msg);
	}


	/**
	 * Returns attribute value based on the attribute name of the element
	 * @param attributeName : name of the attribute associated with the element
	 * @return : returns the value as String
	 */
	public String getAttributeValue(String attributeName)
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Reading attribute: " + attributeName + " of " + instance);
		
		webElement = getReloadedElement(elementName, type);
		String s = webElement.getAttribute(attributeName);
		return s;
	}

	/**
	 * Selects value from the drop-down/list box
	 * @param data : the value to be selected
	 */
	protected void selectValue(String data)
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Selecting: " + data + " from drop-down: " + instance);
		
//		try 
//		{
//			Thread.sleep(60 * 3 * 1000);
//		} 
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
		
		webElement = handler.reloadElement(elementName, type);
		Select select = new Select(webElement);
		select.selectByValue(data);
	}

	/**
	 * Selects visible value from the drop-down/list box
	 * @param data : the value to be selected
	 */
	public void selectVisibleText(String data)
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Selecting: " + data + " from drop-down: " + instance);
		
		webElement = getReloadedElement(elementName, type);
		Select select = new Select(webElement);
		select.selectByVisibleText(data);
	}

	protected boolean isError()
	{
		if(getAttributeValue("class").equals("field required eas-error-msg"))
			return true;
		else
			return false;
	}

	/**
	 * Move the cursor on the element
	 */
	public void mouseHover()
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Mouse hovering on: " + instance);
		
		webElement = getReloadedElement(elementName, type);
		handler.moveMouseToElement(webElement);
		try
		{
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public boolean isEnabled() {
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Checking if " + instance + "is ENABLED");
		
		webElement = getReloadedElement(elementName, type);
		if(webElement == null)
			return false;
		else
			return webElement.isEnabled();
	}

	public boolean isSelected()
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Checking if " + instance + "is SELECTED");
		
		webElement = getReloadedElement(elementName, type);
		return webElement.isSelected();
	}

	@Override
	public void clear()
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clearing data from: " + instance);
		
		webElement = getReloadedElement(elementName, type);
		bringToFront(webElement);
		webElement.clear();
	}



	private WebElement getReloadedElement(String elementName, ElementType type)
	{
//		try 
//		{
//			Thread.sleep(60 * 3 * 1000);
//		} 
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		
		if(type==null)
		{
			return webElement;
		}
		else
		{
			try
			{
				
				WebElement element =  handler.reloadElement(elementName, type);
				
				return element;
			}
			catch(Exception e)
			{
				System.out.println(e);
				throw e;
			}
		}
	}


	public void check()
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Selecting Check-box: " + instance);
		
		webElement = getReloadedElement(elementName, type);
		bringToFront(webElement);
		moveMouse();
		if(webElement.isSelected()==false)
		{
			webElement.click();
		}
	}

	public void unCheck()
	{
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Selecting Check-box: " + instance);
		
		webElement = getReloadedElement(elementName, type);
		bringToFront(webElement);
		if(webElement.isSelected()==true)
		{
			webElement.click();
		}
	}

	protected boolean isEntityPresent(String entity_name)
	{

		List<WebElement> webElements=handler.getElements(elementName, type);

		for(int i=0;i<webElements.size();i++)
		{
			String s[]=webElements.get(i).getText().split("\n");
			if(entity_name.equals(s[0].trim()))
			{
				return true;
			}

		}
		return false;

	}

	public String getSelectedType()
	{
		webElement = getReloadedElement(elementName, type);
		Select selectElement=new Select(webElement);
		return selectElement.getFirstSelectedOption().getText();
	}


	public String[] getSelectedTypes()
	{
		String [] selectedElements=null;
		webElement = getReloadedElement(elementName, type);
		Select selectElement=new Select(webElement);
		List<WebElement> subElements= selectElement.getAllSelectedOptions();
		selectedElements = new String[subElements.size()];
		for(int i=0;i<subElements.size();i++)
		{
			selectedElements[i]=subElements.get(i).getText();

		}
		return selectedElements;
	}

	protected String selectedText()
	{
		webElement = handler.reloadElement(elementName, type);
		Select select = new Select(webElement);
		return select.getFirstSelectedOption().getText();
	}
	
	/**
	 * @author Dip
	 * @param length
	 */
	public void clearAllChars(int length)
	{		
	
		click();
		
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clearing text-field: " + instance);
		
		Actions action = new Actions(Driver.driver);
		
		for(int i=0; i<length; i++)
		{
			action.sendKeys(Keys.BACK_SPACE).build().perform();
		}
	}
	
	
	protected Element getParentElement()
	{
		ElementSet set = new ElementSet();
		
		setElement(getReloadedElement(elementName, type));
		Element SuperElement=set.getSubElement(getElement(),"Xpath","..");
		return SuperElement;
		
	}
	
	public String getTagName()
	{
		webElement = getReloadedElement(elementName, type);
		return webElement.getTagName();
	}
	
	/**
	 * This method is used to click on certain elements which are not clickable through click method
   */
	
	public void clickAT() {
		screenShot.takeContinuousScreenShot();
		
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clicking on : " + instance);
		
		webElement = getReloadedElement(elementName, type);
		webElement.sendKeys(Keys.RETURN);
	}
	
	public void clickATElement() {
		screenShot.takeContinuousScreenShot();
		
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clicking on : " + instance);
		
		webElement = getReloadedElement(elementName, type);
		JavascriptExecutor js = (JavascriptExecutor)Driver.driver; 
		js.executeScript("arguments[0].click();", webElement);
		TestUtils.delay(1);
	}
	
	public void focuse()
	{
		webElement = getReloadedElement(elementName, type);
		JavascriptExecutor js = (JavascriptExecutor)Driver.driver;
		js.executeScript("arguments[0].scrollIntoView(true);", webElement);
	}
	
	public void goUp()
	{
		webElement = getReloadedElement(elementName, type);
		JavascriptExecutor js = (JavascriptExecutor)Driver.driver;
		js.executeScript("window.scrollBy(0,-50000)", "");
		//webElement.sendKeys(Keys.ARROW_UP);
	}
	
	public void goDown(int segment)
	{
		webElement = getReloadedElement(elementName, type);
		JavascriptExecutor js = (JavascriptExecutor)Driver.driver;
		String toExecute = "window.scrollBy(0," + (500 * segment)+")";
		js.executeScript(toExecute, "");
	}

	 public void writeAfterClearing(String msg)
	 {
	  int length = read().length();
	  click();
	  Actions action = new Actions(Driver.driver);

	  String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
	  Logger.log("Writing on: " + instance + "with value: " + msg);
	  action.sendKeys(Keys.END).build().perform();
	  for(int i=0; i<length; i++)
	  {
	   action.sendKeys(Keys.BACK_SPACE).build().perform();
	  }
	 
	   action.sendKeys(msg).build().perform();
	 }
	 
	 private String getShortName(String fullname)
	 {
		 String shortName = fullname.replace("com.ensimtest.", "");
		 shortName = shortName.replace("module.", "");
		 shortName = shortName.replace("tests.", "");
		 return shortName;
	 }
	 
	 public void selectByIndex(int indexOfValueToBeSelected)
	 {
		 webElement = handler.reloadElement(elementName, type);
		 Select select = new Select(webElement);
		 select.selectByIndex(indexOfValueToBeSelected);
	 }
	 
	 public void doubleClick()
	 {
		 isPageLoaded();
		 screenShot.takeContinuousScreenShot();
			
		String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
		Logger.log("Clicking on : " + instance);
		
		for(int i=0; i< loopCount ; i++)
		{
			try
			{
				webElement = getReloadedElement(elementName, type);
				Actions action = new Actions(Driver.driver);
				action.doubleClick(webElement).perform();
				break;
			}
			catch(Exception e)
			{
				System.out.println(">>" + elementName);
				System.out.println("loop continued .....");

				try {
					Thread.sleep(MAXIMUM_WAIT_TIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	 }
	 
	 /**
		 * This method is used to click on certain elements which are not clickable through any other method
		 * of this class, basically the sendKeys method is giving problem in those cases.
	   */
		
		public void moveMouse() 
		{
			screenShot.takeContinuousScreenShot();
			
			String instance = getShortName(Thread.currentThread().getStackTrace()[2].getClassName());
			Logger.log("Clicking on : " + instance);
			
			webElement = getReloadedElement(elementName, type);
			
			Point pointElement = webElement.getLocation();
			
			int x = pointElement.getX();
			int y = pointElement.getY();
			
			(new Actions(Driver.driver)).moveToElement(webElement, x, y).build().perform();
			
		}
		public static boolean WaitForReady()
		{
		    
		    waitForPageLoaded();
		    for ( int i = 0; i < loopCount ; i++ ){
				try {
					Boolean isAjaxFinished = ((JavascriptExecutor) Driver.driver).executeScript("return jQuery.active > 0").toString().equalsIgnoreCase("false");
					System.out.println("Waiting for the ajax call to complete ...........status="+isAjaxFinished);    
				    if(!isAjaxFinished){
				    	Thread.sleep(MAXIMUM_WAIT_TIME);
				    	continue;
				    }
					return isAjaxFinished;
					
				} catch (Exception e) {
					try {
						Thread.sleep(MAXIMUM_WAIT_TIME);
					} catch (Exception e2) {
						// TODO: handle exception
					}
					continue;
				}
		    }
		    return false;
		}
		public static void waitForPageLoaded() {
			for ( int i = 0; i < loopCount ; i++ ){
				try {
					   if (((JavascriptExecutor) Driver.driver).executeScript("return document.readyState;").equals("complete")) 
					   {
						   System.out.println("Page loaded...........");
						   return;
					   }
					   System.out.println("waiting new impl ...........");
					   Thread.sleep(MAXIMUM_WAIT_TIME);
				} catch (Exception e) {
					try {
						Thread.sleep(MAXIMUM_WAIT_TIME);
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
				}
			}
			return;
	    }
		public static void waitTillOldElementStale (Element oldElement)
		{	
			for ( int i = 0; i < loopCount ; i++ ){
				try{
					ElementHandler hndl=new ElementHandler();
					hndl.getElement(oldElement.elementName, oldElement.type);
					try {
						Thread.sleep(MAXIMUM_WAIT_TIME);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				} catch(StaleElementReferenceException stale){
					return;
				} catch (Exception e) {
					// TODO: handle exception
					return;
				}
			}
		}
		public static void scrollToTop(){
			JavascriptExecutor js = (JavascriptExecutor)Driver.driver;
			js.executeScript("window.scrollTo(0, 0);");
		}
}

