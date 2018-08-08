package com.qa.config;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.qa.base.Driver;
import com.qa.base.ElementType;


/**
 * Handles Element related functionalities like finding elements, 
 * mouse hovering, listing elements etc.
 * @author Chiranjit
 *
 */
public class ElementHandler
{
	private int waitInSeconds = 120;
	private static int MAXIMUM_WAIT_TIME = 3500;
	private static int loopCount = 50;
	/**
	 * It searches for the elements and if found more than one then returns first element
	 * @param elementName : value of the search property
	 * @param type : key attribute to look for the element
	 * @return first web-element if found
	 */
	
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

	protected WebElement reloadElement(String elementName, ElementType type)
	{
//		try 
//		{
//			Thread.sleep(60 * 3 * 1000);
//		} 
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
		return getElement(elementName, type);
	}
	
	/**
	 * It parse the element type and returns the ElementType data
	 * @param elementType
	 * @return ElementType
	 */
	public ElementType getElementType(String elementType)
	{
		 if (isPageLoaded())
		 {
			for(int i=0;i<loopCount;i++)
			{
				try
				{
					ElementType type = null;
					switch(elementType)
					{
					case "ClassName":
						type = ElementType.ClassName;
						break;
					case "ID":
						type = ElementType.ID;
						break;
					case "Xpath":
						type = ElementType.Xpath;
						break;
					case "Name":
						type = ElementType.Name;
						break;
					case "TagName":
						type = ElementType.TagName;
						break;
					}
					return type;
				}
				catch(Exception e)
				{
					System.out.println(">>" + elementType);
					System.out.println(e);
					try {
						Thread.sleep(MAXIMUM_WAIT_TIME);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			return null;
		 }
		 return null;
	}
	
	/**
	 * It searches for the elements and returns a List of WebElement(s)
	 * @param elementName : value of the search property
	 * @param type : key attribute to look for the element
	 * @return : List if WebElements found
	 */
	protected List<WebElement> getElements(String elementName, ElementType type)
	{
		 if (isPageLoaded())
		 {

		for(int i=0;i<loopCount;i++)
		{
			try
			{
				List<WebElement> list = null;
				switch(type)
				{
				case ClassName:
					list = Driver.driver.findElements(By.className(elementName));
					break;
				case ID:
					list = Driver.driver.findElements(By.id(elementName));
					break;
				case Xpath:
					//System.out.println(elementName);
					list = Driver.driver.findElements(By.xpath(elementName));
					break;
				case Name:
					list = Driver.driver.findElements(By.name(elementName));
					break;
				case TagName:
					list = Driver.driver.findElements(By.tagName(elementName));
					break;
					
				default:
					break;
				}
				return list;
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
		
		return null;
		 }
		return null;

	}
	
	protected List<WebElement> getElements(Element masterElement, String elementName, ElementType type)
	{
		 isPageLoaded();

		for(int i=0;i<loopCount;i++)
		{
			try
			{
				WebElement element = masterElement.getWebElement(); 
				List<WebElement> list = null;
				switch(type)
				{
				case ClassName:
					list = element.findElements(By.className(elementName));
					break;
				case ID:
					list = element.findElements(By.id(elementName));
					break;
				case Xpath:
					list = element.findElements(By.xpath(elementName));
					break;
				case Name:
					list = element.findElements(By.name(elementName));
					break;
				case TagName:
					list = element.findElements(By.tagName(elementName));
					break;
					
				default:
					break;
				}
				return list;
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
		return null;
	}
	
	/**
	 * It searches for the elements and if found more than one then returns first element
	 * @param elementName : value of the search property
	 * @param type : key attribute to look for the element
	 * @return first web-element in the List if found
	 */
	protected WebElement getElement(String elementName, ElementType type)
	{
		List<WebElement> list = getElements(elementName, type);
		if(list.size()==0)
			throw new ElementNotFoundException(elementName, type.toString(), elementName);
		else
		{
			return list.get(0);
		}
	}
	
	/**
	 * this is used for moving the cursor on the element
	 * @param element Web-Element on which cursor will move
	 */
	protected void moveMouseToElement(WebElement element)
	{
		Actions action = new Actions(Driver.driver);
		action.moveToElement(element).build().perform();
	}
	
	protected WebElement getElement(Element masterElement, String elementName, ElementType type)
	{
		List<WebElement> list = getElements(masterElement,elementName, type);
		if(list.size()==0)
			throw new ElementNotFoundException(elementName, type.toString(), elementName);
		else
		{
			return list.get(0);
		}
	}
	
	
	
//	protected WebElement getElementMod(Element masterElement, String elementName, ElementType type)
//	{
//		List<WebElement> list = getElements(masterElement,elementName, type);
//		if(list==null)
//		{
//			return null;
//		}
//		if(list.size()==0)
//			throw new ElementNotFoundException(elementName, type.toString(), elementName);
//		else
//		{
//			return list.get(0);
//		}
//	}

	/**
	 *To Scroll down
	 * @param scrlXpath : Xpath of the scroll bar
	 */
	public void scrollDown(String scrlXpath){
		Actions dragger = new Actions(Driver.driver);
		WebElement draggablePartOfScrollbar = Driver.driver.findElement(By.xpath(scrlXpath));

		// drag downwards
		int numberOfPixelsToDragTheScrollbarDown = 8;
	
		dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0,numberOfPixelsToDragTheScrollbarDown).release().perform();
		 
	}
	
	/**
	 *To Scroll Up
	 * @param scrlXpath : Xpath of the scroll bar
	 */
	public void scrollUp(String scrlXpath){
		Actions dragger = new Actions(Driver.driver);
		WebElement draggablePartOfScrollbar = Driver.driver.findElement(By.xpath(scrlXpath));

		// drag Upwards
		int numberOfPixelsToDragTheScrollbarDown = -50;
		for (int i=500;i>10;i=i+numberOfPixelsToDragTheScrollbarDown){
		dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0,numberOfPixelsToDragTheScrollbarDown).release().perform();
		}
		 
	}
	
}

