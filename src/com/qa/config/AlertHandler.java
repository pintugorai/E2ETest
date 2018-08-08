package com.qa.config;

import com.qa.base.Driver;

/**
 * Handles Alerts and do alert related functionalities
 * @author Chiranjit
 *
 */
public class AlertHandler
{
	/**
	 * Accepts the alert - OK, Yes
	 */
	public void acceptAlert()
	{
		Driver.driver.switchTo().alert().accept();
	}
	
	/**
	 * Dismisses the alert - Close button
	 */
	public void dismissAlert()
	{
		Driver.driver.switchTo().alert().dismiss();
	}
	
	/**
	 * Read the text data within alert message
	 * @return String of text data
	 */
	public String getTextInAlert()
	{
		return Driver.driver.switchTo().alert().getText();
	}
}
