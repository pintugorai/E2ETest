package com.qa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OrderItemJsonHandler 
{
   public class ItemDetails
   {
	   public String itemName,value,discountAmount,itemSubtotal;
	   public boolean operation,textbox,dropdown,checkbox, isdiscount;
	   public ItemDetails(JSONObject childObject)
	   {
		   this.itemName=childObject.get("itemName").toString();
		   this.value=childObject.get("value").toString();
		   this.operation=Boolean.valueOf(childObject.get("operation").toString());
		   this.textbox=Boolean.valueOf(childObject.get("textbox").toString());
		   this.dropdown=Boolean.valueOf(childObject.get("dropdown").toString());
		   this.checkbox=Boolean.valueOf(childObject.get("checkbox").toString());
		   try
		   {
			   this.isdiscount=Boolean.valueOf(childObject.get("isdiscount").toString());
		   }
		   catch (Exception e)
		   {
			   isdiscount = false;
			   e.printStackTrace();
		   }
		   try
		   {
			   this.discountAmount=childObject.get("discountAmount").toString();
		   }
		   catch (Exception e)
		   {
			   discountAmount = "";
			   e.printStackTrace();
		   }
		   try
		   {
			   this.itemSubtotal=childObject.get("itemSubtotal").toString();
		   }
		   catch (Exception e)
		   {
			   itemSubtotal = "";
			   e.printStackTrace();
		   }
		   
	   }
   }
	
   public ItemDetails[] itemDetails(String jsonString) throws Exception
   {
	   try{
			JSONParser parser=new JSONParser();
			JSONObject parentJsonObject=new JSONObject();
			parentJsonObject=(JSONObject) parser.parse(jsonString);
			JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemList");
			ItemDetails itemDetails[]=new ItemDetails[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++)
			{
				JSONObject childObject=(JSONObject) jsonArray.get(i);
				itemDetails[i]=new ItemDetails(childObject); // 
			}
			
			return itemDetails;
			
	     }
	    catch(Exception e)
	    {
		  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
	    }
	    
   }
   
   public List<HashMap<String, String>> getItemSummaryFromJSON(String jsonData) throws Exception
   {
	   System.out.println(jsonData);
	   try
	   {
			JSONParser parser=new JSONParser();
			JSONObject parentJsonObject=new JSONObject();
			parentJsonObject=(JSONObject) parser.parse(jsonData);
			JSONArray jsonArray = (JSONArray) parentJsonObject.get("itemSummary");
			HashMap<String, String> list = new HashMap<String, String>();
			List<HashMap<String, String>> listOfRow = new ArrayList<>();
			System.out.println("Json size: " + jsonArray.size());
			for(int i=0;i<jsonArray.size();i++)
			{
				System.out.println("incr");
				JSONObject childObject=(JSONObject) jsonArray.get(i);
				list = new HashMap<String, String>();
				list.put("name", childObject.get("name").toString());
				list.put("quantity", childObject.get("quantity").toString());
				list.put("itemSubtotal", childObject.get("itemSubtotal").toString());
				
				listOfRow.add(list);
			}
			System.out.println(listOfRow.size());
			return listOfRow;
	    }
	    catch(Exception e)
	    {
	    	throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
	    }
   }
}
