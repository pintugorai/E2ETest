package com.qa.utils;

import org.json.simple.JSONObject;

public class CustomPurchaseOptionDetails 
{

	public String minimumValue,maximumValue,quantityInterval,defaultvalue;
	public boolean allowDecimal,allowDownsize,allowZeroQuantity;
	
	CustomPurchaseOptionDetails(JSONObject purchaseOptions)
	{
		this.minimumValue=purchaseOptions.get("minimumValue").toString();
		this.maximumValue=purchaseOptions.get("maximumValue").toString();
		this.quantityInterval=purchaseOptions.get("quantityInterval").toString();
		this.defaultvalue=purchaseOptions.get("defaultvalue").toString();
		this.allowDecimal=Boolean.valueOf(purchaseOptions.get("allowDecimal").toString());
		this.allowDownsize=Boolean.valueOf(purchaseOptions.get("allowDownsize").toString());
		this.allowZeroQuantity=Boolean.valueOf(purchaseOptions.get("allowZeroQuantity").toString());
	}
	
	
	
}
