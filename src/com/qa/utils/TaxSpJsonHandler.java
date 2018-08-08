package com.qa.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.JsonParseException;

public class TaxSpJsonHandler
{
	public class TaxConfigDetails
	{
		public String taxManagementSys,taxCodeOnEditItem;
		public boolean enableTax,enableTaxForResller,applyTax,purposeOrder,editItem;
		private TaxConfigDetails(JSONObject childObject)
		{
			this.enableTax = Boolean.valueOf(childObject.get("enableTax").toString());
			this.enableTaxForResller = Boolean.valueOf(childObject.get("enableTaxForResller").toString());
			this.applyTax = Boolean.valueOf(childObject.get("applyTax").toString());
			this.purposeOrder = Boolean.valueOf(childObject.get("purposeOrder").toString());
			this.editItem = Boolean.valueOf(childObject.get("editItem").toString());
			this.taxManagementSys = childObject.get("taxManagementSys").toString();
			this.taxCodeOnEditItem = childObject.get("taxCodeOnEditItem").toString();
		}
	}
	
	public TaxConfigDetails taxConfigDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONObject jsonObject = (JSONObject) parentJsonObject.get("taxConfig");
				TaxConfigDetails taxConfigDetails=new TaxConfigDetails(jsonObject);
				return taxConfigDetails;
			}
		   catch(Exception e)
			{
				throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
			}
		
	   }
	  
}
