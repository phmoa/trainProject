package com.project.train.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.train.entities.Station;
import com.project.train.entities.Trip;

public class TripsUtils {

	// construct list trips by customer
	public static Map<Integer, List<Trip>> getListTripsByCustomer(Map<Integer, List<Station>> listStationsBycustomer) {
		try {
			List<Trip> listTrips = new ArrayList<Trip>();
			boolean isStationStartSetted = false;
			boolean isStationEndSetted = false;
			Set<Integer> keyCustomer = listStationsBycustomer.keySet();
			for (int key : keyCustomer) {
				List<Station> liststations = listStationsBycustomer.get(key);
				char stationStart = ' ';
				char stationEnd = ' ';
				char zoneStart = ' ';
				char zoneEnd = ' ';
				int tripCost = 0;
				String startedJourneyAt = "";

				for (int i = 0; i < liststations.size(); i++) {

					if (isStationStartSetted && !isStationEndSetted) {
						stationEnd = liststations.get(i).getIdStation();
						isStationEndSetted = true;
					}
					if (!isStationStartSetted && !isStationEndSetted) {
						stationStart = liststations.get(i).getIdStation();
						isStationStartSetted = true;
						startedJourneyAt = liststations.get(i).getTimeUnix();
					}
					if (isStationStartSetted && isStationEndSetted) {
						// get the Zone
						zoneStart = ZonesUtils.getZoneFromStation(stationStart, stationEnd);
						zoneEnd = ZonesUtils.getZoneFromStation(stationEnd, stationStart);
						try {
							if (zoneStart == ' ' || zoneEnd == ' ') {
								throw new Exception();
							}
						} catch (Exception e) {
							System.out.println("Problem occured while traiting trips : Station unknown");
							System.exit(0);
							return null;
						}

						// get the price of the trip
						tripCost = getTripCost(zoneStart, zoneEnd);
						try {
							if (tripCost == -1) {
								throw new Exception();
							}
						} catch (Exception e) {
							System.out.println("Problem occured while traiting trips : Cost unknown");
							System.exit(0);
							return null;
						}

						Trip t = new Trip(key, stationStart, stationEnd, startedJourneyAt, tripCost, zoneStart,
								zoneEnd);
						listTrips.add(t);
						isStationStartSetted = false;
						isStationEndSetted = false;
					}
				}
			}
			return listTrips.stream().collect(Collectors.groupingBy(Trip::getCustumerId));
		} catch (Exception e) {
			System.out.println("Problem occured while traiting trips : /n" + e.toString());
			System.exit(0);
			return null;
		}
	}

	// get the price of the trip
	private static int getTripCost(char zoneStart, char zoneEnd) {
		try {
			String zone1_2 = "12";
			String zone3_4 = "34";
			String zone3 = "3";
			String zone4 = "4";

			if (zoneStart == zoneEnd) {
				return 0;
			}

			if (zone1_2.contains(String.valueOf(zoneStart)) && zone1_2.contains(String.valueOf(zoneEnd))) {
				return ProjectUtils.PRICE_FOR_ZONE_1_2;
			}
			if (zone3_4.contains(String.valueOf(zoneStart)) && zone3_4.contains(String.valueOf(zoneEnd))) {
				return ProjectUtils.PRICE_FOR_ZONE_3_4;
			}
			if ((zone1_2.contains(String.valueOf(zoneStart)) && zone3.contains(String.valueOf(zoneEnd)))
					|| (zone3.contains(String.valueOf(zoneStart)) && zone1_2.contains(String.valueOf(zoneEnd)))) {
				return ProjectUtils.PRICE_FOR_ZONE_1_2_FROM_TO_ZONE_3;
			}
			if ((zone1_2.contains(String.valueOf(zoneStart)) && zone4.contains(String.valueOf(zoneEnd)))
					|| (zone4.contains(String.valueOf(zoneStart)) && zone1_2.contains(String.valueOf(zoneEnd)))) {
				return ProjectUtils.PRICE_FOR_ZONE_1_2_FROM_TO_ZONE_4;
			}
		} catch (Exception e) {
			System.out.println("Problem occured while getting the cost of the trip : /n" + e.toString());
			System.exit(0);
		}
		return -1;
	}
	
}
