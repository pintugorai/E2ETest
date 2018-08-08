package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.JsonParseException;

public class IsnMultiSlNoJsonHandler {
	 public class IsnMultiSlNoDetails
	   {
		   public String serialNo; 
		   public IsnMultiSlNoDetails(JSONObject childObject)
		   {
			   this.serialNo=childObject.get("serialNo").toString();
		   }
	   }
	 public IsnMultiSlNoDetails [] isnMultiSlNoLst(String jsonString) throws Exception
	   {
		 try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("IsnMultiSlNoLst");
				IsnMultiSlNoDetails isnMultiSlNoDetails[]=new IsnMultiSlNoDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					isnMultiSlNoDetails[i]=new IsnMultiSlNoDetails(childObject);
				}
				
				return isnMultiSlNoDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
}
