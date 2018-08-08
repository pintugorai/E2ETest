package com.qa.utils;

import java.util.Random;

public class RandomData
{
	/**
	 * Gives randomly created number in string format of specified length
	 * @param length : length of the string of number
	 * @return String containing only numeric data
	 */
	public String getRandomNum(int length)
	{
		Random rand = new Random ();
		String outputString = "";
		for(int i= 0; i < length; i++)
		{
			outputString+=( rand.nextInt(8) + 1)+"";
		}
		return outputString;
	}
	
	/**
	 * Gives randomly created string data of specified length
	 * @param length : length of the string
	 * @return String data
	 */
	public String getRandomString(int length)
	{
		String randNum = getRandomNum(length);
		String output = "";
		for(int i=0; i<randNum.length(); i++)
		{
			output += (char)(Character.getNumericValue(randNum.charAt(i)) +'a') + "";
		}
		return output;
	}
	
	/**
	 * Creates randomly user-suffix containing only alphabets 
	 * @return String
	 */
	public String getRandomUserSuffix()
	{
		return getRandomString(3) + "." + getRandomString(3);
	}
	
	/**
	 * Creates randomly email-ID containing only alphabets 
	 * @return String
	 */
	public String getRandomEmailID()
	{
		return getRandomString(4) + "@" + getRandomString(3)+"." + getRandomString(3);
	}
	
	/**
	 * Create random string of alpha-numeric data with alphabets
	 * and digits appearing alternatively
	 * @param length : length of the string required
	 * @return String containing alpha-numeric data
	 */
	public String getRandomAlfaNumeric(int length)
	{
		String outputString = "";
		for(int i=0; i<length; i++)
		{
			outputString+= (i%2==0) ? getRandomString(1) : getRandomNum(1);
		}
		return outputString;
	}
	
	/**
	 * Create random string of alpha-numeric data with alphabets
	 * and digits ending with special character '#' appearing alternatively
	 * @param length : length of the string required
	 * @return String containing alpha-numeric data
	 */
	public String getRandomAlfaNumericSpcChar(int length)
	{
		return getRandomAlfaNumeric(length - 1) + "#";
	}
	
	public String getUnicodeString()
	{
		return "à¤¹à¤¿à¤¨à¥�à¤¦à¥€ Ã†Ã¥Ã£ÃƒÃ¼ÃœÃªÃŠÃ©Ã‰ Ã‡ à§¨  wd trÃ©ma æ±‰å­— æ¼¢å­— ÃŸ";
	}
	
	public String getUnicodeSpCharString()
	{
		return "à¤¹à¤¿à¤¨à¥�à¤¦à¥€ Ã†Ã¥Ã£ÃƒÃ¼ÃœÃªÃŠÃ©Ã‰ Ã‡ à§¨ wd's trÃ©ma æ±‰å­— æ¼¢å­— ÃŸ, %$# @`";
	}
	
	// ÙˆÛ�Ø§ÚºØŸ URDU There? Ù© ARABIC tis3a(9) ØªØ£ØªÙŠ ARABIC COME
	public String getRtoLString()
	{
		return "ØªØ£ØªÙŠ Ù© ÙˆÛ�Ø§ÚºØŸ";
	}
}
