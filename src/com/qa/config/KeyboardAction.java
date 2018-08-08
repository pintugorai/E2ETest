package com.qa.config;
import org.openqa.selenium.Keys;

public class KeyboardAction
{
	public enum Key
	{
		ENTER
	}
	
	public String getKeyAsString(Key key)
	{
		switch(key)
		{
		case ENTER:
			return Keys.RETURN.toString();
		default:
			return null;
		}
	}
}
