package com.bridgelab;

import java.util.List;

import com.bridgelabz.*;
//import com.bridgelabz.jsonreader.Util;
import com.bridgelabz.controller.Gareportresponse;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class GoogleAnalyticReporting {

	public static void main(String[] args) {
		Csvfilecreator cf = new Csvfilecreator();
		GAreportsummary1reader gsr = new GAreportsummary1reader();
		initializeAnalyticsReporting inr = new initializeAnalyticsReporting();
		try {
			// initialising analytic report
			AnalyticsReporting service = inr.initializeAnalyticsReporting();
			
			// reading metric,dimension,and filter and placing into list
			List[] summaryresponse = gsr.GAreportsummary1();
			
			// passing summaryresponse into initializeAnalyticsReporting
			inr.getlistofelement(summaryresponse);
			
			// getting response from initializeAnalyticsReporting
			GetReportsResponse response = inr.getReport(service);
			
			//printing response 
			System.out.println(response);
			
			//assigning response into variable k2
			GetReportsResponse k2 = response;
			//creating csvfilr
			cf.responsetaker(k2.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}