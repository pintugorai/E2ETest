package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class AdjustUsageJSONHandler
{
	public class OrderItemDetails
	{
		public String date,consumedAmnt,ppuAmnt;
		
		public OrderItemDetails(JSONObject jsonObject)
		{
			this.date=jsonObject.get("date").toString();
			this.consumedAmnt=jsonObject.get("consumedAmnt").toString();
			this.ppuAmnt=jsonObject.get("ppuAmnt").toString();
		}
	}
	

	public OrderItemDetails[] getOfferTieredItemDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemList");
				OrderItemDetails offerItemDetails[]=new OrderItemDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					offerItemDetails[i]=new OrderItemDetails(childObject);
				}
				
				return offerItemDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
}

