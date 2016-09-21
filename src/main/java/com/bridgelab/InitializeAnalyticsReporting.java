package com.bridgelab;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;

public class InitializeAnalyticsReporting {
	@SuppressWarnings("rawtypes")
	List[] summaryresponse = null;
	@SuppressWarnings("rawtypes")
	List dimension = null;
	@SuppressWarnings("rawtypes")
	List metric = null;
	@SuppressWarnings("rawtypes")
	List dimensionfilter1 = null;
	private static final String APPLICATION_NAME = "Appystore test app";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String KEY_FILE_LOCATION = "/home/bridgeit/Desktop/springexp/HelloAnalytics/AppyGAReports-35a6c523765c.p12";
	private static final String SERVICE_ACCOUNT_EMAIL = "appystorereport@appygareports.iam.gserviceaccount.com";
	private static final String VIEW_ID = "ga:111820853";
	@SuppressWarnings("unused")
	private static final Object POST_INDEX_PATH = null;

	public AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
				.setServiceAccountScopes(AnalyticsReportingScopes.all()).build();
		// getting access token
		String refreshToken = null;
		credential.setRefreshToken(refreshToken);
		credential.refreshToken();
		// printing access token
		// System.out.println(credential.getAccessToken());
		// System.out.println(u.callURL("https://www.googleapis.com/analytics/v3/data/ga?ids=ga%3A111820853&start-date=2016-09-02&end-date=2016-09-05&metrics=ga%3AtotalEvents&dimensions=ga%3AeventCategory%2Cga%3Adimension1%2Cga%3Adate&filters=ga%3AeventCategory%3D%3DApp%20Open%3Bga%3Adimension15%3D%3D10&access_token="+credential.getAccessToken()));

		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		// Construct the Analytics Reporting service object.
		return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public void getlistofelement(List[] summary) {
		summaryresponse = summary;
		metric = summaryresponse[0];
		dimension = summaryresponse[1];
		dimensionfilter1 = summaryresponse[2];

		for (int r = 0; r < dimensionfilter1.size(); r++) {
			System.out.println(dimensionfilter1.get(r));
		}
	}

	public GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
	
		// Creating the DateRange object.
		DateRange dateRange = new DateRange();
		dateRange.setStartDate("2016-09-02");
		dateRange.setEndDate("2016-09-03");

		// creating object of metric ArrayList
		ArrayList<Metric> metriclist = new ArrayList<Metric>();
		metriclist.clear();
		for (int j = 0; j < metric.size(); j++) {
			// Creating the Metrics object.
			Metric metric3 = new Metric();
			// adding metric into metric ArrayList
			metriclist.add(metric3.setExpression((String) metric.get(j)));
		}

		// Creating the Dimensions ArrayList.
		Dimension dimens;
		ArrayList<Dimension> dimensList = new ArrayList<Dimension>();
		dimensList.clear();
		for (int i = 0; i < dimension.size(); i++) {
			// Creating the Dimensions object.
			dimens = new Dimension();
			// adding dimension after setting name into the dimension ArrayList
			dimensList.add(dimens.setName((String) dimension.get(i)));
		}


		// creating object of DimensionFilter arrayList
		ArrayList<DimensionFilter> dimensfilterList = new ArrayList<DimensionFilter>();
		dimensfilterList.clear();
		for (int k = 0; k < dimensionfilter1.size(); k++) {
			// created DimensionFilter object
			DimensionFilter dimensionFilter = new DimensionFilter();
			// taking DimensionFilter and converting into String 
						String dimensionfilter = (String) dimensionfilter1.get(k);
			String s = "==";
			String s1 = "=@:";
			// checking whether inside DimensionFilter exact or partial operator
			// Available
			if (dimensionfilter.contains(s)) {
				//Splitting the DimensionFilter
				String[] words = dimensionfilter.split("==");
				// adding into dimensfilterList after setting the parameter
				dimensfilterList.add(dimensionFilter.setDimensionName(words[0]).setOperator("EXACT")
						.setExpressions(Arrays.asList(words[1])));
				System.out.println("equals");
			} else {
				String[] words = dimensionfilter.split("=@:");
				dimensfilterList.add(dimensionFilter.setDimensionName(words[0]).setOperator("PARTIAL")
						.setExpressions(Arrays.asList(words[1])));
				System.out.println("at the rate");
			}

		}
		// creating DimensionFilterClause object
		DimensionFilterClause dimensionFilterPathClause = new DimensionFilterClause();
		// making ArrayList of DimensionFilterClause
		ArrayList<DimensionFilterClause> dmfilterclauselist = new ArrayList<DimensionFilterClause>();
		// adding dimFilters to it
		dmfilterclauselist.add(dimensionFilterPathClause.setFilters(dimensfilterList).setOperator("AND"));
		System.out.println(dmfilterclauselist);
		// Creating the ReportRequest object.
		ReportRequest request = new ReportRequest()
				.setViewId(VIEW_ID)
				.setDateRanges(Arrays.asList(dateRange))
				.setMetrics(metriclist).setDimensions(dimensList)
				.setDimensionFilterClauses(dmfilterclauselist);

		ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		// Creating the GetReportsRequest object.
		GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
		// Calling the batchGet method.
		GetReportsResponse response = service.reports().batchGet(getReport).execute();

		// Returning the response.
		return response;
	}

}
