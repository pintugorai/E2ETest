package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OfferItemDetailsJsonHandler 
{

	 public class OfferItemDetails
	   {
		   public String ItemName,PurchaseOption,Type,Billingfreq,comissionPer,installmentTime,ProfitMergin,BillingName,minimum,maximum,discoundOnUnitPrice,unitPriceValue,PriceCode,ppuLimit,trialQuantity;
		   public boolean Commissionable,Unitprice,Installment,EnabledisOnitem,pricewithdiscoundPer,pricewithdiscound,multicurency,isTrialAllow;
		   public CustomPurchaseOptionDetails customerPurchaseOption;
		   
		   
		   public OfferItemDetails(JSONObject childObject)
		   {
			   this.ppuLimit="N/A";
			   this.isTrialAllow=false;
			   this.trialQuantity="N/A";
			   this.ItemName=childObject.get("ItemName").toString();
			   this.PurchaseOption=childObject.get("PurchaseOption").toString();
			   this.PriceCode=childObject.get("PriceCode").toString();
			   this.Type=childObject.get("Type").toString();
			   this.Billingfreq=childObject.get("Billingfreq").toString();
			   this.Commissionable=Boolean.valueOf(childObject.get("Commissionable").toString());
			   this.comissionPer=childObject.get("comissionPer").toString();
			   this.Unitprice=Boolean.valueOf(childObject.get("Unitprice").toString());
			   this.Installment=Boolean.valueOf(childObject.get("Installment").toString());
			   this.installmentTime=childObject.get("installmentTime").toString();
			   this.EnabledisOnitem=Boolean.valueOf(childObject.get("EnabledisOnitem").toString());
			   this.ProfitMergin=childObject.get("ProfitMergin").toString();
			   this.BillingName=childObject.get("BillingName").toString();
			   this.minimum=childObject.get("minimum").toString();
			   this.maximum=childObject.get("maximum").toString();
			   this.pricewithdiscoundPer=Boolean.valueOf(childObject.get("pricewithdiscoundPer").toString());
			   this.pricewithdiscound=Boolean.valueOf(childObject.get("pricewithdiscound").toString());
			   this.discoundOnUnitPrice=childObject.get("discoundOnUnitPrice").toString();
			   this.unitPriceValue=childObject.get("unitPriceValue").toString();
			   this.multicurency=Boolean.valueOf(childObject.get("multicurency").toString());
			   if(childObject.containsKey("customerPurchaseOptnDetails"))
			   {
			   this.customerPurchaseOption=new CustomPurchaseOptionDetails((JSONObject) childObject.get("customerPurchaseOptnDetails"));  
			   }
			   
			   if(childObject.containsKey("ppuLimit"))
			   {
				   this.ppuLimit=childObject.get("ppuLimit").toString();
			   }
			   if(childObject.containsKey("isTrialAllow"))
			   {
				   this.isTrialAllow=Boolean.valueOf(childObject.get("isTrialAllow").toString());
				   if(childObject.containsKey("trialQuantity"))
				   {
					   this.trialQuantity=childObject.get("trialQuantity").toString();
				   }
				   
			   }
			   
			   
		   }
		   
		  
	   }
	
	  	public OfferItemDetails[] offerItemDetailsDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemList");
				OfferItemDetails offerItemDetails[]=new OfferItemDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					
					offerItemDetails[i]= new OfferItemDetails(childObject);

				}
				
				return offerItemDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
	
}
