package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class AdjustReservationQtyJSONHandler
{
	public class OrderItemDetailsReservQty
	{
		public String itemName,element,value;
		
		public OrderItemDetailsReservQty(JSONObject jsonObject)
		{
			this.itemName=jsonObject.get("itemName").toString();
			this.element=jsonObject.get("element").toString();
			this.value=jsonObject.get("value").toString();
		}
	}
	

	public OrderItemDetailsReservQty[] getOfferTieredItemDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemList");
				OrderItemDetailsReservQty offerItemDetails[]=new OrderItemDetailsReservQty[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					offerItemDetails[i]=new OrderItemDetailsReservQty(childObject);
				}
				
				return offerItemDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
}

