package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OrderTirePricingJsonHandler 
{
	
	public class TiredPriceDetail
	{
		public String BillingName,RangeMin,RangeMax,UnitePrice,minimum,maximum;
		
		public TiredPriceDetail(JSONObject jsonObject)
		{
			this.BillingName=jsonObject.get("BillingName").toString();
			this.RangeMin=jsonObject.get("RangeMin").toString();
			this.RangeMax=jsonObject.get("RangeMax").toString();
			this.UnitePrice=jsonObject.get("UnitePrice").toString();
			this.maximum=jsonObject.get("maximum").toString();
			this.minimum=jsonObject.get("minimum").toString();
					
		}
		
	}
	
	public class OfferTieredItemDetails
	{
		public String ItemName,PurchaseOption,Type,Billingfreq,PriceCode,Tired_Type,ppuLimit,trialQuantity;
		public boolean EnabledisOnitem,pricewithdiscoundPer,pricewithdiscound,multicurency,isTrialAllow;
		public TiredPriceDetail tiredPriceDetail[];
		public CustomPurchaseOptionDetails customerPurchaseOption;
		
		public OfferTieredItemDetails(JSONObject jsonObject)
		{
			   this.ppuLimit="N/A";
			   this.isTrialAllow=false;
			   this.trialQuantity="N/A";
			   this.multicurency=Boolean.parseBoolean(jsonObject.get("multicurency").toString());
			   this.ItemName=jsonObject.get("ItemName").toString();
			   this.PurchaseOption=jsonObject.get("PurchaseOption").toString();
			   this.Type=jsonObject.get("Type").toString();
			   this.Billingfreq=jsonObject.get("Billingfreq").toString();
			   this.PriceCode=jsonObject.get("PriceCode").toString();
			   this.Tired_Type=jsonObject.get("Tired_Type").toString();
			   this.EnabledisOnitem=Boolean.parseBoolean(jsonObject.get("EnabledisOnitem").toString());
			   this.pricewithdiscoundPer=Boolean.parseBoolean(jsonObject.get("pricewithdiscoundPer").toString());
			   this.pricewithdiscound=Boolean.parseBoolean(jsonObject.get("pricewithdiscound").toString());
			   
			   if(jsonObject.containsKey("customerPurchaseOptnDetails"))
			   {
				   this.customerPurchaseOption=new CustomPurchaseOptionDetails((JSONObject) jsonObject.get("customerPurchaseOptnDetails"));
			   }
			   
			   if(jsonObject.containsKey("ppuLimit"))
			   {
				   this.ppuLimit=jsonObject.get("ppuLimit").toString();
			   }
			   if(jsonObject.containsKey("isTrialAllow"))
			   {
				   this.isTrialAllow=Boolean.valueOf(jsonObject.get("isTrialAllow").toString());
				   if(jsonObject.containsKey("trialQuantity"))
				   {
					   this.trialQuantity=jsonObject.get("trialQuantity").toString();
				   }
				   
			   }
			   
			   JSONArray priceInfo=(JSONArray) jsonObject.get("Prices");
			   tiredPriceDetail=new TiredPriceDetail[priceInfo.size()];
			   
			   for(int i=0;i<priceInfo.size();i++)
			   {
				   JSONObject childObject=(JSONObject) priceInfo.get(i); 
				   tiredPriceDetail[i]= new TiredPriceDetail(childObject);
			   }
			   
		}
	}
	

	public OfferTieredItemDetails[] getOfferTieredItemDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemList");
				OfferTieredItemDetails offerTieredItemDetails[]=new OfferTieredItemDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					offerTieredItemDetails[i]=new OfferTieredItemDetails(childObject);
				}
				
				return offerTieredItemDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
}
