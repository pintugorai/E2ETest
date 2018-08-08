package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.JsonParseException;

public class IsnLabelNameJsonHandler 
{

	 public class LabelNameDetails
	   {
		   public String label1,label2,label3,label4,label5; 
		   public LabelNameDetails(JSONObject childObject)
		   {
			   this.label1=childObject.get("label1").toString();
			   this.label2=childObject.get("label2").toString();
			   this.label3=childObject.get("label3").toString();
			   this.label4=childObject.get("label4").toString();
			   this.label5=childObject.get("label5").toString();
		   }
	   }
	 
	 
	 public LabelNameDetails[] labelNameList(String jsonString) throws Exception
	   {
		 try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("LabelNameList");
				LabelNameDetails labelNameDetails[]=new LabelNameDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					labelNameDetails[i]=new LabelNameDetails(childObject);  
				}
				
				return labelNameDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
}

