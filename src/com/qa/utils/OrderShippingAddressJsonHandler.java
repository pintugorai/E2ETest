package com.qa.utils;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ensimtest.module.orders.OrderShippingAddressContainer.ShippingAddressRow;
import com.ensimtest.utils.OrderItemJsonHandler.ItemDetails;
import com.google.gson.JsonParseException;

public class OrderShippingAddressJsonHandler {
	public class OrderShippingAddressDetails{
		public String fieldName,fieldValue;
		public boolean operation,textBox,dropDown,isMandatory;
		public OrderShippingAddressDetails(String fieldName,String fieldValue,String operation,String isMandatory,String textBox,String dropDown){
			this.fieldName=fieldName;
			this.fieldValue=fieldValue;
			this.operation=Boolean.valueOf(operation);
			this.isMandatory=Boolean.valueOf(isMandatory);
			this.textBox=Boolean.valueOf(textBox);
			this.dropDown=Boolean.valueOf(dropDown);
		}
	}
	public OrderShippingAddressDetails[] orderShippingAddress(String jsonString) throws Exception
	{
		try {
			JSONParser parser=new JSONParser();
			JSONObject parentJsonObject=new JSONObject();
			parentJsonObject=(JSONObject) parser.parse(jsonString);
			JSONArray jsonArray = (JSONArray) parentJsonObject.get("OrderShippingAddress");
			OrderShippingAddressDetails shippingAddressDetails[]=new OrderShippingAddressDetails[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++)
			{
				JSONObject childObject=(JSONObject) jsonArray.get(i);
				shippingAddressDetails[i]=new OrderShippingAddressDetails(childObject.get("fieldName").toString(),childObject.get("fieldValue").toString(),childObject.get("operation").toString(),childObject.get("isMandatory").toString(),childObject.get("textbox").toString(),childObject.get("dropdown").toString());
			}
			return shippingAddressDetails;				
		}
		catch(Exception e) {
			throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		}
		    
	}
	   public String[] getShippingAddressJSONs(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ShippingAddress");
				String shippingAddressJSON[]=new String[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					//JSONObject childObject=(JSONObject) jsonArray.get(i);
					shippingAddressJSON[i]=jsonArray.get(i).toString(); // 
				}
				
				return shippingAddressJSON;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	   public String getShippingAddressFromJSON(String jsonString,String node) throws Exception{
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONObject childObject=(JSONObject) parentJsonObject.get(node);
				return childObject.toString();
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
	   }
	   public String getFieldValueOfShippingAddress(String jsonString,String fieldName) throws Exception{
		   try{
			   OrderShippingAddressDetails[] orderShippingAddressDetails=null;
			   orderShippingAddressDetails=orderShippingAddress(jsonString);
			   for(OrderShippingAddressDetails details:orderShippingAddressDetails){
				   if(details.fieldName.equalsIgnoreCase(fieldName)){
					   return details.fieldValue;
				   }
			   }
		   } catch (Exception e){
			   throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace()); 
		   }
		   return null;
		    
	   }

}
