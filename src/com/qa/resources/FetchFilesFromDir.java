package com.qa.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchFilesFromDir
{
	private String _orderImportDir = "orderImportCSV\\";
	private String _userImportDir = "userImportCSV\\";
	private String _orgImportDir = "orgImportCSV\\";
	private String _downloadDir = "downloadSampleCSV\\";
	private String _usageImportDir = "usageImportCSV\\";
	private String _offerImportDir = "offerImport\\";
	private String _resellerType2Dir = "ResellerType2TstCase\\";
	private String _BundleServiceOfferImport = "BundleServiceOfferImport\\";
	private String _taxDir = "tax\\";
	private String _serviceImportDir = "serviceImport\\";
	private String _orderExportDir = "orderExport\\";
	private String _offerVersionDir = "OfferVersioning\\";
	private String _paymentRefundDir = "PaymentRefundCSV\\";
	private String _resellerType3Dir = "ResellerType3TstCase\\";
	private String _resellerType1Dir = "ResellerType1TstCase\\";
	private String _itemImportDir = "ItemImport\\";
	private String _itemExportDir = "ItemExport\\";
	private String _itemAssociationOfferImport = "ItemAssociationOfferImport\\";
	private String _fileAttachment = "FileAttachment\\";
	
	public String getFilePath(TestDataDirType dirType, String filename)
	{
		String returnPath = "";
		switch (dirType)
		{
		 	case ResellerType2:
		 		returnPath = ResReference.testData + _resellerType2Dir + filename;
		 		break;
			
			case OrderImport: 
				returnPath = ResReference.testData + _orderImportDir + filename;
				break;
	
			case UserImport:
				returnPath = ResReference.testData + _userImportDir + filename;
				break;
				
			case OrgImport:
				returnPath = ResReference.testData + _orgImportDir + filename;
				break;
				
			case DownloadSample:
				returnPath = ResReference.testData + _downloadDir + filename;
				break;
				
			case UsageImport:
				returnPath = ResReference.testData + _usageImportDir + filename;
				break;
				
			case OfferImport:
				returnPath = ResReference.testData + _offerImportDir + filename;
				break;
				

			case BundleServiceOfferImport:
				returnPath = ResReference.testData + _BundleServiceOfferImport + filename;
				break;

			case Tax:
				returnPath = ResReference.testData + _taxDir + filename;
				break;
				
			case ServiceImport:
				returnPath = ResReference.testData + _serviceImportDir + filename;
				break;
				
			case OrderExport:
				returnPath = ResReference.testData + _orderExportDir + filename;
				break;
				
			case OfferVersioning: 
				returnPath = ResReference.testData + _offerVersionDir + filename;
				break;
				
			case PaymentRefund: 
				returnPath = ResReference.testData + _paymentRefundDir + filename;
				break; 
				
			case ResellerType3:
		 		returnPath = ResReference.testData + _resellerType3Dir + filename;
		 		break;
		 		
			case ResellerType1:
		 		returnPath = ResReference.testData + _resellerType1Dir + filename;
		 		break;
		 		
			case ItemImport:
		 		returnPath = ResReference.testData + _itemImportDir + filename;
		 		break;
		 		
			case ItemExport:
		 		returnPath = ResReference.testData + _itemExportDir + filename;
		 		break;	
		 		
			case ItemAssociationOfferImport:
		 		returnPath = ResReference.testData + _itemAssociationOfferImport + filename;
		 		break;
		 		
			case FileAttachment:
		 		returnPath = ResReference.testData + _fileAttachment + filename;
		 		break;	
				
			default:
				break;
		}
		
		// Return absolute path
		return System.getProperty("user.dir") + "\\" + returnPath;
	}
	
	public List<?> getCSVofFile(String filepath)
	{
		try {
			HashMap<String, String> row = null;
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String []header = br.readLine().split(",");
			String newHash ;
			List<HashMap<String, String>> csvList = new ArrayList<HashMap<String, String>>();
			while((newHash = br.readLine()) != null)
			{
				row = new HashMap<String, String>();
				String[] itemValue = newHash.split(",");
			    for(int i=0;i<itemValue.length;i++)
			    {
			    	row.put(header[i], itemValue[i]);
			    	//System.out.println(row.get(header[i]));
			    }
			    csvList.add(row);   
			}
			
			// Closing BufferedReader
			try
			{
				if(br!=null)
				{
					br.close();
				}
			}
			catch(IOException ee)
			{
				System.out.println("Unable to close reader : " + ee);
			}
			return csvList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}