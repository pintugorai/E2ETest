package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class PricingVerifyJSONHandler
{
	
	public class TiredPriceDetail
	{
		public String netPrice;
		
		public TiredPriceDetail(JSONObject jsonObject)
		{
			this.netPrice=jsonObject.get("netPrice").toString();
		}
		
	}
	
	public class OfferItemDetails
	{
		public String ItemName;
		public TiredPriceDetail tiredPriceDetail[];
		
		public OfferItemDetails(JSONObject jsonObject)
		{
			   this.ItemName=jsonObject.get("ItemName").toString();
			  
			   JSONArray priceInfo=(JSONArray) jsonObject.get("Prices");
			   tiredPriceDetail=new TiredPriceDetail[priceInfo.size()];
			   
			   for(int i=0;i<priceInfo.size();i++)
			   {
				   JSONObject childObject=(JSONObject) priceInfo.get(i); 
				   tiredPriceDetail[i]= new TiredPriceDetail(childObject);
			   }
			   
		}
	}
	

	public OfferItemDetails[] getOfferTieredItemDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemList");
				OfferItemDetails offerTieredItemDetails[]=new OfferItemDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					offerTieredItemDetails[i]=new OfferItemDetails(childObject);
				}
				
				return offerTieredItemDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
}

