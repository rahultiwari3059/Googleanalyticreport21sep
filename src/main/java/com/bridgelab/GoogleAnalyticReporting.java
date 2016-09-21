package com.bridgelab;

import java.util.List;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class GoogleAnalyticReporting {

	public static void main(String[] args) {

		// creating object of Csvfilecreator class
		Csvfilecreator cf = new Csvfilecreator();

		// creating object of GAreportsummary1reader
		GAreportsummary1reader gsr = new GAreportsummary1reader();

		// creating object of initializeAnalyticsReporting
		InitializeAnalyticsReporting inr = new InitializeAnalyticsReporting();
		try {
			// initializing analytic report
			AnalyticsReporting service = inr.initializeAnalyticsReporting();

			// reading metric,dimension,and filter and placing into list
			List[] summaryresponse = gsr.GAreportsummary1();

			// passing summaryresponse into initializeAnalyticsReporting
			inr.getlistofelement(summaryresponse);

			// getting response from initializeAnalyticsReporting
			GetReportsResponse response = inr.getReport(service);

			// printing the response
			System.out.println(response);

			// assigning response into variable k2
			GetReportsResponse k2 = response;

			// creating csvfile
			cf.responsetaker(k2.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}