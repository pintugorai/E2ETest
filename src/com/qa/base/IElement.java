package com.qa.base;

/**
 * Element Interface for common methods
 * @author Pintu Gorai
 *
 */
public interface IElement
{
	/**
	 * Clicks on the element
	 */
	public void click();
	
	/**
	 * Check if the element exists. 
	 * @return if exists then returns true else returns false. 
	 */
	public boolean isExists();
	
	/**
	 * Check if the element is displayed.
	 * @return if displayed then returns true else returns false. 
	 */
	public boolean isDisplayed();

	/**
	 * Read text data from the element
	 * @return String data of the text of the elements
	 */

	public boolean isEnabled();

	public String read();
	
	/**
	 * Write the string data in the element.
	 * @param s : Text to be written
	 */
	public void write(String s);
	
	/**
	 * use to clean a text field
	 */
	
	public void clear();
	
	}
