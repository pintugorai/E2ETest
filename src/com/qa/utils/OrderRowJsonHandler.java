package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OrderRowJsonHandler 
{

	public class OrderRows
	{
		public String orderNO,orgId,offerName,orderStatus;

		public OrderRows(JSONObject childObject)
		{
			this.orderNO=childObject.get("OrderID").toString();
			this.orgId=childObject.get("OrgID").toString();
			this.offerName=childObject.get("OfferName").toString();
			this.orderStatus=childObject.get("Status").toString();
		}

	}


	public OrderRows[] getOrderRowsDetails(String jsonString) throws Exception
	{
		try{
			JSONParser parser=new JSONParser();
			JSONObject parentJsonObject=new JSONObject();
			parentJsonObject=(JSONObject) parser.parse(jsonString);
			JSONArray jsonArray = (JSONArray) parentJsonObject.get("SearchResultRows");
			OrderRows orderRows[]=new OrderRows[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++)
			{
				System.out.println("parsing the json file...");
				JSONObject childObject=(JSONObject) jsonArray.get(i);
				orderRows[i]=new OrderRows(childObject);
			}

			return orderRows;

		}
		catch(Exception e)
		{
			throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		}

	}


}
