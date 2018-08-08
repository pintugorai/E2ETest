package com.qa.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EASPatternMatcher 
{

	public boolean isPatternMatches(String actualMessage,String pattern)
	{
		System.out.println(actualMessage);
		System.out.println(pattern);
		Pattern ptn = Pattern.compile(pattern);
		Matcher matcher = ptn.matcher(actualMessage); 
		return matcher.matches();
		
	}
	
	
}
