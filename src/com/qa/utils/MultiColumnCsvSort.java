package com.qa.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class MultiColumnCsvSort
{
    //private final String COLUMN_SEPARATOR = ",";

    private class CSVStruct
    {
    	public List<List<String>> lines;
    	public String header;
    }
    
    public List<HashMap<String, String>> sortingAlgo(String inputcsvPath, int... indices) throws Exception
    {
    	System.out.println("In side sortingAlgo......");
    	
        CSVStruct csvStruct = readCsv(new FileInputStream(inputcsvPath));


      //**************************************
        System.out.println("After reading CSV========================");
        Iterator<List<String>> iterator = csvStruct.lines.iterator();
        while (iterator.hasNext()) {
        	List<String> list = iterator.next();
              Iterator<String> string = list.iterator();
              while(string.hasNext()){
                  System.out.print("	"+string.next().toString());
               }
              System.out.println("");
        }
      //**************************************

        
        Comparator<List<String>> comparator = createComparator(indices);
        
        Collections.sort(csvStruct.lines, comparator);
        //**************************************
        System.out.println("After sorting========================");
        iterator = csvStruct.lines.iterator();
        while (iterator.hasNext()) {
        	List<String> list = iterator.next();
              Iterator<String> string = list.iterator();
              while(string.hasNext()){
                  System.out.print("	"+string.next().toString());
               }
              System.out.println("");
        }
        //**************************************
        return getHashedOutput(csvStruct);
        
//        String outputcsvPath = inputcsvPath + "x";
//        
//        new File(outputcsvPath).deleteOnExit();
//        
//        writeCsv(csvStruct.header, csvStruct.lines, new FileOutputStream(outputcsvPath));
//        
//        return getHashedOutput(outputcsvPath);
    }
    
    private List<HashMap<String, String>> getHashedOutput(CSVStruct struct)
    {
    	System.out.println("In side getHashedOutput......");
    	List<HashMap<String, String>> csvList = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> row = null;
    	String []header = struct.header.split(",");
    	System.out.println("In side getHashedOutput......total no of lines= "+struct.lines.size());
    	//String newHash ;
    	int i = 0;
 	    while(i<struct.lines.size())
	    {
	    	row = new HashMap<String, String>();
	    	//Pattern ihh = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");;
	    	//String[] itemValue = new String[struct.lines.get(i).size()];
	    	System.out.println("In side getHashedOutput......each line size= "+struct.lines.get(i).size());
	    	for(int j=0;j<struct.lines.get(i).size();j++)
	        {
	        	row.put(header[j], struct.lines.get(i).get(j).replace("\"", ""));
	        	System.out.println("In side getHashedOutput......header= "+header[j]+" Value= "+struct.lines.get(i).get(j));
	        }
	    	csvList.add(row);
	    	i++;
	    }
    	return csvList;
    }
    
//    public List<HashMap<String, String>> getHashedOutput(String outputcsvPath) throws Exception
//    {
//    	List<HashMap<String, String>> csvList = new ArrayList<HashMap<String, String>>(); 
//		HashMap<String, String> row = null;
//		BufferedReader br = new BufferedReader(new FileReader(outputcsvPath));
//	    String []header = br.readLine().split(",");
//	    String newHash ;
//	    while((newHash = br.readLine()) != null)
//	    {
//	    	row = new HashMap<String, String>();
//	    	Pattern p = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");;
//	    	String[] itemValue = p.split(newHash);
//	    	
//	    	for(int i=0;i<itemValue.length;i++)
//	        {
//	        	row.put(header[i], itemValue[i].replace("\"", ""));
//	        	//System.out.println(row.get(header[i]));
//	        }
//	        csvList.add(row);
//	    }
//	    closeFile(br);
//	    return csvList;
//    }

    private CSVStruct readCsv(InputStream inputStream) throws IOException
    {
    	System.out.println("In side readCsv......");
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(inputStream));
        
        CSVStruct csvStruct = new CSVStruct();
        csvStruct.lines = new ArrayList<List<String>>();
        csvStruct.header = reader.readLine();
        
        String line = "";

        while (true)
        {
            line = reader.readLine();
            if (line == null)
            {
                break;
            }
            Pattern p = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
	    	String[] itemValue = p.split(line);
            List<String> list = Arrays.asList(itemValue);
            csvStruct.lines.add(list);
        }
        closeFile(reader);
        return csvStruct;
    }
    
    private void closeFile(BufferedReader reader)
    {
    	try
        {
        	if(reader!=null)
        		reader.close();
        }
        catch(IOException es)
        {
        	System.out.println("Unable to close BufferedReader instance");
        }
    }

//    private void writeCsv(String header, List<List<String>> lines, OutputStream outputStream) throws IOException
//    {
//        Writer writer = new OutputStreamWriter(outputStream);
//        writer.write(header+"\n");
//        for (List<String> list : lines)
//        {
//            for (int i = 0; i < list.size(); i++)
//            {
//                writer.write(list.get(i));
//                if (i < list.size() - 1)
//                {
//                    writer.write(COLUMN_SEPARATOR);
//                }
//            }
//            writer.write("\n");
//        }
//        writer.close();
//
//    }

    private <T extends Comparable<? super T>> Comparator<List<T>> createComparator(int... indices)
    {
        return createComparator(MultiColumnCsvSort.<T>naturalOrder(), indices);
    }

    private static <T extends Comparable<? super T>> Comparator<T>naturalOrder()
    {
        return new Comparator<T>()
        {
            @Override
            public int compare(T t0, T t1)
            {
                return t1.compareTo(t0);
            }
        };
    }

    private <T> Comparator<List<T>> createComparator(
        final Comparator<? super T> delegate, final int... indices)
    {
        return new Comparator<List<T>>()
        {
            @Override
            public int compare(List<T> list0, List<T> list1)
            {
                for (int i = 0; i < indices.length; i++)
                {
                    T element0 = list0.get(indices[i]);
                    T element1 = list1.get(indices[i]);
                    int n = delegate.compare(element0, element1);
                    if (n != 0)
                    {
                        return n;
                    }
                }
                return 0;
            }
        };
    }
}
