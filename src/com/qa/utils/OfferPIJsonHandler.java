package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonParseException;

public class OfferPIJsonHandler 
{
	  public class PIDetails
	   {
		   public String Rank,resourceName,resourceID,mandetory,visibilityInOrder,defaultValue; 
		   public boolean export;
		   public PIDetails(String Rank,String ResourceName,String ResourceID,String Mandetory,String VisibilityInOrder,String DefaultValue,String export)
		   {
			   this.Rank=Rank;
			   this.resourceName=ResourceName;
			   this.resourceID=ResourceID;
			   this.mandetory=Mandetory;
			   this.visibilityInOrder=VisibilityInOrder;
			   this.defaultValue=DefaultValue;
			   this.export=Boolean.valueOf(export);
			   			   
		   }
	   }
	
	  	public PIDetails[] piDetails(String jsonString) throws Exception
	   {
		   try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("ProvInfoLst");
				PIDetails pIDetails[]=new PIDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					pIDetails[i]=new PIDetails(childObject.get("Rank").toString(),childObject.get("ResourceName").toString(),childObject.get("ResourceID").toString(),childObject.get("Mandetory").toString(),childObject.get("VisibilityInOrder").toString(),childObject.get("DefaultValue").toString(),childObject.get("Export").toString());
				}
				
				return pIDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
	
}
