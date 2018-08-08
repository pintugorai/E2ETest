package com.qa.resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CSVvalueReader
{
	BufferedReader br;String[] header;
	
	/**
	 * 
	 * @param path of CSV
	 * @return List<HashMap<String, String>> 
	 */
	public List<HashMap<String, String>> csvValueReader(String path)
	{
		List<HashMap<String, String>> csvList = new ArrayList<HashMap<String, String>>(); 
		HashMap<String, String> row = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
		} catch (UnsupportedEncodingException e3) {
			e3.printStackTrace();
		} catch (FileNotFoundException e3) {
			e3.printStackTrace();
		}
	    
		try {
			header = br.readLine().split(",");
			System.out.println("lengeth of header = " + header.length );
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    String newHash ;
	    try {
			while((newHash = br.readLine()) != null)
			{
				row = new HashMap<String, String>();
				Pattern p = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");;
				String[] itemValue = p.split(newHash);
				//System.out.println("fields : "+itemValue.length);
				for(int i = 0; i<itemValue.length; i++)
				{
					//System.out.println("fields : "+itemValue[i]);
				}
				for(int i=0;i<itemValue.length;i++)
			    {
			    	row.put(header[i], itemValue[i].replace("\"", ""));
			    	//System.out.println(row.get(header[i]));
			    }
			    csvList.add(row);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	    try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	    return csvList;
	}

	public String[] getStringContent(List<HashMap<String,String>> csvAsHashedList, String[] header) {
		
		Set<String> keys = csvAsHashedList.get(0).keySet();
		for(int i=0; i<header.length; i++)
		{
			keys.remove(header[i].trim());
		}
		
		String rowsOfFile = "";

		for(int row=0; row<csvAsHashedList.size(); row++)
		{
			String rowToForm = "";
			for(String key : keys)
			{
				rowToForm = rowToForm + csvAsHashedList.get(row).get(key);
			}
			rowsOfFile = rowsOfFile + System.lineSeparator() + rowToForm;
		}

		return rowsOfFile.split(System.lineSeparator());
	}
	
	public int getCSVheaderLength(String path) throws IOException
	{
		br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
		header = br.readLine().split(",");
		br.close();
		return header.length ;
	}
}
