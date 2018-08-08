package com.qa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ensimtest.config.Element;
import com.ensimtest.config.ElementSet;
import com.google.gson.JsonParseException;

public class IsnJsonHandler 
{

	 public class RankLabelDetails
	   {
		   public String rank,label; 
		   public RankLabelDetails(JSONObject childObject)
		   {
			   this.rank=childObject.get("rank").toString();
			   this.label=childObject.get("label").toString();
		   }
	   }
	 
	 
	 public RankLabelDetails [] isnList(String jsonString) throws Exception
	   {
		 try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("IsnList");
				RankLabelDetails rankLabelDetails[]=new RankLabelDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					rankLabelDetails[i]=new RankLabelDetails(childObject); // 
				}
				
				return rankLabelDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	 
	 public class IsnMultiLabelDetails
	   {
		 List<String> values;
		   public String lines;
		   public HashMap<String, HashMap<String,String>> lineItemMap = new HashMap<String, HashMap<String,String>>();
		   public IsnMultiLabelDetails(JSONObject childObject)
		   {   
			   this.lines=childObject.get("Lines").toString().replace("[","").replace("]","").replace("},","};");
			   System.out.println(this.lines);
			   String[] lineItemList = this.lines.split(";");
			   System.out.println("length:"+lineItemList.length);
			   for(int i=0;i<lineItemList.length;i++){
				   String[] allSubItemsList = lineItemList[i].replace("{","").replace("}","").split(",");
				   HashMap<String, String> fieldValueMap = new HashMap<String, String>();
				   for(int j=0;j<allSubItemsList.length;j++){
					   String[] subItem = allSubItemsList[j].split(":");
					   fieldValueMap.put(subItem[0].replace("\"", ""), subItem[1].replace("\"", ""));
				   }
				   this.lineItemMap.put(i+"", fieldValueMap);
			   }
		   }
	   }
	 public IsnMultiLabelDetails [] itemSlNos(String jsonString,String itemName) throws Exception
	   {
		 try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ItemSlNos");
				IsnMultiLabelDetails isnMultiLabelDetails[]=new IsnMultiLabelDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					isnMultiLabelDetails[i]=new IsnMultiLabelDetails((JSONObject)childObject.get(itemName)); 
				}
				
				return isnMultiLabelDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
}
