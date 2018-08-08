package com.qa.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ensimtest.config.Element;
import com.ensimtest.config.ElementSet;
import com.google.gson.JsonParseException;

public class IsnRankJsonHandler 
{

	 public class RankDetails
	   {
		   public String rank1,rank2,rank3,rank4,rank5; 
		   public RankDetails(JSONObject childObject)
		   {
			   this.rank1=childObject.get("rank1").toString();
			   this.rank2=childObject.get("rank2").toString();
			   this.rank3=childObject.get("rank3").toString();
			   this.rank4=childObject.get("rank4").toString();
			   this.rank5=childObject.get("rank5").toString();
		   }
	   }
	 
	 
	 public RankDetails[] rankList(String jsonString) throws Exception
	   {
		 try{
				JSONParser parser=new JSONParser();
				JSONObject parentJsonObject=new JSONObject();
				parentJsonObject=(JSONObject) parser.parse(jsonString);
				JSONArray jsonArray = (JSONArray) parentJsonObject.get("RankList");
				RankDetails rankDetails[]=new RankDetails[jsonArray.size()];
				for(int i=0;i<jsonArray.size();i++)
				{
					JSONObject childObject=(JSONObject) jsonArray.get(i);
					rankDetails[i]=new RankDetails(childObject); // 
				}
				
				return rankDetails;
				
		     }
		    catch(Exception e)
		    {
			  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
		    }
		    
	   }
}

