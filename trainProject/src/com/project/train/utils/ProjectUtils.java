package com.project.train.utils;

import java.util.List;

import com.project.train.entities.Customer;

public class ProjectUtils {

	// inputs keys
	public final static String CUSTOMER_ID = "customerId";
	public final static String UNIX_TIME_STAMP = "unixTimestamp";
	public final static String STATION = "station";

	// stations
	public final static char STATION_A = 'A';
	public final static char STATION_B = 'B';
	public final static char STATION_C = 'C';
	public final static char STATION_D = 'D';
	public final static char STATION_E = 'E';
	public final static char STATION_F = 'F';
	public final static char STATION_G = 'G';
	public final static char STATION_H = 'H';
	public final static char STATION_I = 'I';

	//prices
	public final static int PRICE_FOR_ZONE_1_2 = 240;
	public final static int PRICE_FOR_ZONE_3_4 = 200;
	public final static int PRICE_FOR_ZONE_1_2_FROM_TO_ZONE_3 = 280;
	public final static int PRICE_FOR_ZONE_1_2_FROM_TO_ZONE_4 = 300;
	
	//json structor response
	public final static String COMMA = ",";
	public final static String QUOTE = "\"";
	public final static String SLASH_COMMA = "\",";
	public final static String LINE_BREAK = "\n";
	
	public final static String JSON_HEADER = "{\n\n \"customerSummaries\" : [ {";
	public final static String JSON_CUSTOMER_ID = "\n\n    \"customerId\" : ";
	public final static String JSON_TOTAL_COST_IN_CENT = "\n\n    \"totalCostInCents\" : ";
	public final static String JSON_TRIPS = "\n\n   \"trips\" : [ {\n";
	public final static String JSON_STATION_START = "\n     \"stationStart\" : ";
	public final static String JSON_STATION_END = "\n\n     \"stationEnd\" : ";
	public final static String JSON_STARTED_JOURNEY_AT = "\n\n     \"startedJourneyAt\" :";
	public final static String JSON_COST_IN_CENTS = "\n\n     \"costInCents\" : ";
	public final static String JSON_ZONE_FROM = "\n\n     \"zoneFrom\" : ";
	public final static String JSON_ZONE_TO = "\n\n     \"zoneTo\" : ";
	public final static String JSON_END_TRIP = "\n   } ]";
	public final static String JSON_NEXT_TRIP = "\n   }, { \n";
	public final static String JSON_NEXT_CUSTOMER = "\n\n }, {";
	public final static String JSON_END= "\n\n } ]\n\n}";

	public static String createJsonResponse(List<Customer> listCustumers) {
		StringBuilder sb = new StringBuilder();
		sb.append(ProjectUtils.JSON_HEADER);
		for (int i = 0; i < listCustumers.size(); i++) {
			sb.append(ProjectUtils.JSON_CUSTOMER_ID).append(listCustumers.get(i).getCustomerId() + ProjectUtils.COMMA);
			sb.append(ProjectUtils.JSON_TOTAL_COST_IN_CENT)
					.append(listCustumers.get(i).getTotalConstInCents() + ProjectUtils.COMMA);
			sb.append(ProjectUtils.JSON_TRIPS);
			for (int j = 0; j < listCustumers.get(i).getTrips().size(); j++) {
				sb.append(ProjectUtils.JSON_STATION_START).append(ProjectUtils.QUOTE
						+ listCustumers.get(i).getTrips().get(j).getStationStart() + ProjectUtils.SLASH_COMMA);
				sb.append(ProjectUtils.JSON_STATION_END).append(ProjectUtils.QUOTE
						+ listCustumers.get(i).getTrips().get(j).getStationEnd() + ProjectUtils.SLASH_COMMA);
				sb.append(ProjectUtils.JSON_STARTED_JOURNEY_AT)
						.append(listCustumers.get(i).getTrips().get(j).getStartedJourneyAt() + ProjectUtils.COMMA);
				sb.append(ProjectUtils.JSON_COST_IN_CENTS)
						.append(listCustumers.get(i).getTrips().get(j).getConstInCents() + ProjectUtils.COMMA);
				sb.append(ProjectUtils.JSON_ZONE_FROM)
						.append(listCustumers.get(i).getTrips().get(j).getZoneFrom() + ProjectUtils.COMMA);
				sb.append(ProjectUtils.JSON_ZONE_TO)
						.append(listCustumers.get(i).getTrips().get(j).getZoneTo() + ProjectUtils.LINE_BREAK);
				if (j == listCustumers.get(i).getTrips().size() - 1) {
					sb.append(ProjectUtils.JSON_END_TRIP);
				} else {
					sb.append(ProjectUtils.JSON_NEXT_TRIP);

				}
			}
			if (i != listCustumers.size() - 1) {
				sb.append(ProjectUtils.JSON_NEXT_CUSTOMER);

			}
		}
		sb.append(ProjectUtils.JSON_END);
		return sb.toString();
	}

}
