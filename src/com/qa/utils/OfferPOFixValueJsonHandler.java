package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OfferPOFixValueJsonHandler 
{

	  public class POFixDetails
	   {
		   public String resourceName,resourceID,quantity; 
		   public POFixDetails(String ResourceName,String ResourceID,String quantity)
		   {
			   this.resourceName=ResourceName;
			   this.resourceID=ResourceID;
			   this.quantity=quantity;
 		   }
	   }
	
	  	public POFixDetails[] piDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("POFixValues");
				POFixDetails poFixDetails[]=new POFixDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					poFixDetails[i]=new POFixDetails(childObject.get("ResourceName").toString(),childObject.get("ResourceID").toString(),childObject.get("quantity").toString());
				}
				
				return poFixDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
	
}
