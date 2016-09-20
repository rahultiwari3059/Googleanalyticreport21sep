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

	public void responsetaker(String response){
		int temp1 = 0, temp2 = 0, temp3 = 0;
		Csvfilecreator js = new Csvfilecreator();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> values1 = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(response);
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			// covering report array into JSONArray
			JSONArray reportarray = (JSONArray) jsonObject.get("reports");
			// System.out.println(reportarray);
			// System.out.println(reportarray.size());
			for (int j = 0; j < reportarray.size(); j++) {
				JSONObject obj3 = (JSONObject) reportarray.get(j);
				// System.out.println(obj3);
				JSONObject dataobject = (JSONObject) obj3.get("data");
				// System.out.println(dataobject);
				JSONArray rowarray = (JSONArray) dataobject.get("rows");
				// System.out.println(rowarray);
				// System.out.println(rowarray.size());
				temp1 = rowarray.size();
				System.out.println(temp1);
				for (int i = 0; i < rowarray.size(); i++) {

					JSONObject rowobject = (JSONObject) rowarray.get(i);
					// System.out.println(rowobject);
					JSONArray metricarray = (JSONArray) rowobject.get("metrics");
					// System.out.println(metricarray);
					// System.out.println(metricarray.size());
					temp2 = metricarray.size();
					for (int k = 0; k < metricarray.size(); k++) {
						JSONObject metricobject = (JSONObject) metricarray.get(k);
						// System.out.println(metricobject);
						JSONArray valuesarray = (JSONArray) metricobject.get("values");
						// System.out.println(valuesarray);
						String valuestring = JSONArray.toJSONString(valuesarray);
						// System.out.println(valuestring);
						valuestring = valuestring.substring(valuestring.indexOf("[") + 2, valuestring.indexOf("]") - 1);
						// System.out.println(valuestring);
						values1.add(valuestring);
					}
					JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");
					// System.out.println("dimension="+dimensionsarray.size());
					temp3 = dimensionsarray.size();
					for (int l = 0; l < dimensionsarray.size(); l++) {
						values.add((String) dimensionsarray.get(l));
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		js.createCsv(values, values1, temp1, temp2, temp3);
	}

	public void createCsv(ArrayList<String> list, ArrayList<String> list1, int temp1, int temp2, int temp3) {
int k=0;
int p=0;
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
			for (i = 0; i < 1000; i++) {
				
				for (j = 0; j < temp3; j++)
				{
					k++;
					System.out.println(list.get(k));
					bw.append(list.get(k));
					bw.append("^");
				}
				for (int m = 0; m < temp2; m++)
				{
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
