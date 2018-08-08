package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OrderProvInfoJsonHandler 
{

	 public class ProvInfoDetails
	   {
		   public String itemName,value; 
		   public boolean operation,textbox,dropdown,checkbox;
		   public ProvInfoDetails(String provInfoName,String value,String operation,String textbox,String dropdown,String checkbox)
		   {
			   this.itemName=provInfoName;
			   this.value=value;
			   this.operation=Boolean.valueOf(operation);
			   this.textbox=Boolean.valueOf(textbox);
			   this.dropdown=Boolean.valueOf(dropdown);
			   this.checkbox=Boolean.valueOf(checkbox);
		   }
	   }
	 
	 
	 public ProvInfoDetails[] provInfoLst(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ProvInfoLst");
				ProvInfoDetails provInfoDetails[]=new ProvInfoDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					provInfoDetails[i]=new ProvInfoDetails(childObject.get("provInfoName").toString(),childObject.get("value").toString(),childObject.get("operation").toString(),childObject.get("textbox").toString(),childObject.get("dropdown").toString(),childObject.get("checkbox").toString());
				}
				
				return provInfoDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
	
}
