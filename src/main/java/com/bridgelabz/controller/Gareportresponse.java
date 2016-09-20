package com.bridgelabz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;

public class Gareportresponse {
	
	private  final String VIEW_ID = "ga:111820853";
	public  GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
		System.out.println("hello");
		// Create the DateRange object.
		DateRange dateRange = new DateRange();
		dateRange.setStartDate("2016-09-02");
		dateRange.setEndDate("2016-09-03");
		// Create the Metrics object.
		System.out.println("hello");
		Metric metric1 = new Metric().setExpression("ga:totalEvents");

	/*	Metric metric1 = new Metric().setExpression("ga:sessions");
	 *  Metric metric2 = new Metric() .setExpression("ga:screenviews");
		 Metric metric3 = new Metric() .setExpression("ga:exits"); 
		 Metric metric4 = new Metric() .setExpression("ga:exitRate");*/
		 

		// adding metric into metric ArrayList
		ArrayList<Metric> metriclist = new ArrayList<Metric>();
		metriclist.add(0, metric1);
		/*metriclist.add(1,metric2); 
		metriclist.add(2,metric3);
		metriclist.add(3,metric4);*/


		

		// Create the Dimensions object.
		Dimension dimens = new Dimension();
		Dimension dimens1 = new Dimension();
		Dimension dimens2 = new Dimension();
		ArrayList<Dimension> dimensList = new ArrayList<Dimension>();
		dimensList.add(dimens.setName("ga:eventCategory"));
		dimensList.add(dimens2.setName("ga:date"));
		dimensList.add(dimens1.setName("ga:dimension1"));
		//dimensList.add(dimens1.setName("ga:dimension8"));

		// created DimensionFilter object
		DimensionFilter dimensionFilter = new DimensionFilter();
		/*dimensionFilter.setDimensionName("ga:screenName").setOperator("EXACT").setExpressions(Arrays.asList("Product Page"));*/
		dimensionFilter.setDimensionName("ga:eventCategory").setOperator("EXACT").setExpressions(Arrays.asList("App Reopen"));
		dimensionFilter.setDimensionName("ga:dimension15").setOperator("EXACT").setExpressions(Arrays.asList("10"));
		dimensionFilter.setDimensionName("ga:dimension14").setOperator("PARTIAL").setExpressions(Arrays.asList("true"));
		System.out.println("hello");

		


		// creating DimensionFilterClause object
		DimensionFilterClause dimensionFilterPathClause = new DimensionFilterClause();
		// making ArrayList of DimensionFilterClause
		ArrayList<DimensionFilterClause> dmfilterclauselist = new ArrayList<DimensionFilterClause>();
		// adding dimFilters to it
		dmfilterclauselist.add(dimensionFilterPathClause.setFilters(Arrays.asList(dimensionFilter)));
		// Create the ReportRequest object.
		ReportRequest request = new ReportRequest().setViewId(VIEW_ID).setDateRanges(Arrays.asList(dateRange))
				.setMetrics(metriclist).setDimensions(dimensList).setDimensionFilterClauses(dmfilterclauselist);

		ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		// Create the GetReportsRequest object.
		GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
		// Call the batchGet method.
		GetReportsResponse response = service.reports().batchGet(getReport).execute();
		// Return the response.

		return response;
	}

}
