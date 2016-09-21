package com.bridgelab;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GAreportsummary1reader {
	public List[] GAreportsummary1() {
		JSONParser parser = new JSONParser();
		ArrayList<String> dimentionarraylist = new ArrayList<String>();
		ArrayList<String> metricarraylist = new ArrayList<String>();
		ArrayList<String> dimensionfilterarraylist = new ArrayList<String>();
		try {
			Object obj = parser
					.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/GAreportsummary1.JSON"));
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;

			// converting into JSONObject
			JSONArray GAReportInfoarray = (JSONArray) jsonObject.get("GAReportInfo");
			// reading GAReportInfoarray
			for (int i = 0; i < GAReportInfoarray.size(); i++) {
				JSONObject GAReportInfoobject = (JSONObject) GAReportInfoarray.get(i);
				// converting GAID into string and printing same
				String gaid = (String) GAReportInfoobject.get("GAID");
				System.out.println("gaid=" + gaid);
				// converting GAdiscription into string and printing same
				String GAdiscription = (String) GAReportInfoobject.get("GAdiscription");
				System.out.println("GAdiscription=" + GAdiscription);

				// making metric array
				JSONArray metricarray = (JSONArray) GAReportInfoobject.get("metric");

				// reading the metric array
				for (int k = 0; k < metricarray.size(); k++) {
					// adding into metric ArrayList
					metricarraylist.add((String) metricarray.get(k));
				}

				// making dimension JSONArray
				JSONArray dimensionsarray = (JSONArray) GAReportInfoobject.get("dimension");

				// reading the dimension array
				for (int j = 0; j < dimensionsarray.size(); j++) {
					dimentionarraylist.add((String) dimensionsarray.get(j));
				}

				// making DimensionFilter into JSONArray
				JSONArray dimensionfilterarray = (JSONArray) GAReportInfoobject.get("dimensionfilter");
				// reading the DimensionFilter JSONArray
				for (int l = 0; l < dimensionfilterarray.size(); l++) {
					// adding into DimensionFilter ArrayList
					dimensionfilterarraylist.add((String) dimensionfilterarray.get(l));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new List[] { metricarraylist, dimentionarraylist, dimensionfilterarraylist };
	}
}
