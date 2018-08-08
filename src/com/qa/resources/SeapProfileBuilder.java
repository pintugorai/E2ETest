package com.qa.resources;
import com.google.gson.JsonParseException;

public class SeapProfileBuilder
{
	public class SeapProfiles
	{
		public String action, name, value, enabled;
	}
	
	public SeapProfiles[] getSeapProfiles(String jsonData)
	{
		SeapProfiles []seapProfiles;
		try
		{
			JSONParser parser=new JSONParser();
			JSONObject parentJsonObject=new JSONObject();
			parentJsonObject=(JSONObject) parser.parse(jsonData);
			JSONArray jsonArray = (JSONArray) parentJsonObject.get("");
			seapProfiles=new SeapProfiles[jsonArray.size()];
			for(int i=0;i<jsonArray.size();i++)
			{
				//JSONObject childObject=(JSONObject) jsonArray.get(i);
				
				//itemDetails[i]=new ItemDetails(childObject); // 
			}
			
			//return itemDetails;
			
	     }
	    catch(Exception e)
	    {
		  throw new JsonParseException("Problem in Json string Parsing:--  "+e.getStackTrace());
	    }
		return seapProfiles;
	}
}
