package com.bridgelab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class Csvfilecreator {
	static int i = 0, j = 0, k = 0;

	public void responsetaker(String response) {
		int temp1 = 0, temp2 = 0, temp3 = 0;
		// making object of Csvfilecreator
		Csvfilecreator js = new Csvfilecreator();
		// creating values ArrayList
		ArrayList<String> values = new ArrayList<String>();
		// creating values1 ArrayList
		ArrayList<String> values1 = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(response);
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			// covering report array into JSONArray
			JSONArray reportarray = (JSONArray) jsonObject.get("reports");
			// reading report JSONArray 
			for (int j = 0; j < reportarray.size(); j++) {
				// getting first object and converting into JSONObject
				JSONObject obj3 = (JSONObject) reportarray.get(j);
				// making JSONObject of data
				JSONObject dataobject = (JSONObject) obj3.get("data");
				// making JSONArray of rows
				JSONArray rowarray = (JSONArray) dataobject.get("rows");
				// storing row JSONArray size into temp1
				temp1 = rowarray.size();
				// reading rows JSONArray
				for (int i = 0; i < rowarray.size(); i++) {
					// getting first object and converting into JSONObject
					JSONObject rowobject = (JSONObject) rowarray.get(i);
					// making metrics JSONArray
					JSONArray metricarray = (JSONArray) rowobject.get("metrics");
					// storing metric JSONArray size into temp2
					temp2 = metricarray.size();
					// iterating metric JSONArray
					for (int k = 0; k < metricarray.size(); k++) {
						//// getting first object and converting into JSONObject
						JSONObject metricobject = (JSONObject) metricarray.get(k);
						// making values JSONArray
						JSONArray valuesarray = (JSONArray) metricobject.get("values");
						// converting JSONArray into JSONString
						String valuestring = JSONArray.toJSONString(valuesarray);
						// making subString
						valuestring = valuestring.substring(valuestring.indexOf("[") + 2, valuestring.indexOf("]") - 1);
						// adding into value1 ArrayList
						values1.add(valuestring);
					}
					JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");

					temp3 = dimensionsarray.size();
					for (int l = 0; l < dimensionsarray.size(); l++) {
						// adding into value ArrayList
						values.add((String) dimensionsarray.get(l));
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// calling createCsv method by passing argument
		js.createCsv(values, values1, temp1, temp2, temp3);
	}

	public void createCsv(ArrayList<String> list, ArrayList<String> list1, int temp1, int temp2, int temp3) {
		int k = 0;
		int p = 0;
		try {
			// initializing the boolean value
			boolean b = false;
			// creating the new csv file
			File file = new File("/home/bridgeit/Music/appopen.csv");
			// checking whether file already existing or not
			if (!file.exists()) {
				b = true;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			// if file doesn't exists, then create it and appending values
			if (b) {
				file.createNewFile();
				bw.append("androidid");
				bw.append("^");
				bw.append("name");
				bw.append("^");
				bw.append("date");
				bw.append("^");
				bw.append("totalevents");
				bw.append("^");
				bw.newLine();
			}
			for (i = 0; i < temp1; i++) {

				for (j = 0; j < temp3; j++) {
					k++;
					System.out.println(list.get(k));
					bw.append(list.get(k));
					bw.append("^");
				}
				for (int m = 0; m < temp2; m++) {
					p++;
					bw.append(list1.get(p));
					System.out.println(list1.get(p));
				}
				bw.newLine();
			}

		} catch (Exception e) {
		}
		// if operation is completed then printing done
		System.out.println("Done");
	}
}
