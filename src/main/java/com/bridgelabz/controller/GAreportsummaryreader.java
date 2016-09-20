package com.bridgelabz.controller;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GAreportsummaryreader {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		ArrayList<String> dimentionarraylist = new ArrayList<String>();
		ArrayList<String> metricarraylist = new ArrayList<String>();
		ArrayList<String> dimensionfilterarraylist = new ArrayList<String>();
		try {
			Object obj = parser
					.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/GAreportsummary.JSON"));
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
		
			// reading GAReportInfoarray
			JSONArray GAReportInfoarray = (JSONArray) jsonObject.get("GAReportInfo");
			
			for (int i = 0; i < GAReportInfoarray.size(); i++) {
				JSONObject GAReportInfoobject = (JSONObject) GAReportInfoarray.get(i);
				// converting GAID into string and printing same 
				String gaid = (String) GAReportInfoobject.get("GAID");
				System.out.println("gaid="+gaid);
				// converting GAdiscription into string and printing same 
				String GAdiscription = (String) GAReportInfoobject.get("GAdiscription");
				System.out.println("GAdiscription="+GAdiscription);
				
				//making metric array
				JSONArray metricarray = (JSONArray) GAReportInfoobject.get("metric");
				System.out.println("the metrics are ");
				//reading the metric array
				for (int k = 0; k < metricarray.size(); k++) {
					System.out.println(metricarray.get(k));
					metricarraylist.add((String) metricarray.get(k));
				}
				
				//making dimension jsonarray
				JSONArray dimensionsarray = (JSONArray) GAReportInfoobject.get("dimension");
				System.out.println("the dimension are ");

				// reading the dimention array 
				for (int j = 0; j < dimensionsarray.size(); j++) {
					System.out.println(dimensionsarray.get(j));
					dimentionarraylist.add((String) dimensionsarray.get(j));
				}
				System.out.println("the dimensionfilter are ");

				// making dimensionfilter into JSONArray
				JSONArray dimensionfilterarray = (JSONArray) GAReportInfoobject.get("dimensionfilter");
				// reading the dimensionfilterarray 
				for (int l = 0; l < dimensionfilterarray.size(); l++) {
					System.out.println(dimensionfilterarray.get(l));
					dimensionfilterarraylist.add((String) dimensionfilterarray.get(l));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
