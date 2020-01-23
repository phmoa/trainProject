package com.project.train.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.project.train.entities.Station;

public class ZonesUtils {

	// get list of all stations
	public static List<Station> getAllStationsInformations(File text) {
		List<Station> listStations = new ArrayList<Station>();
		Scanner scnr;
		try {
			scnr = new Scanner(text);
			int index = 0;
			String time = "";
			while (scnr.hasNextLine()) {
				String line = scnr.nextLine();
				if (line.contains(ProjectUtils.CUSTOMER_ID)) {
					String[] lineSplit = line.split(":");
					try {
						listStations.add(new Station(Integer.parseInt(lineSplit[1].replace(" ", "").replace(",", ""))));
					} catch (java.lang.NumberFormatException nbFormatExcep) {
						System.out.println("Problem occured while traiting stations : invalid customer id\\n");
						System.exit(0);
						return null;

					}
				}
				if (line.contains(ProjectUtils.UNIX_TIME_STAMP)) {
					String[] lineSplit = line.split(":");
					time = lineSplit[1].replaceAll(",", "");
					try {
						Integer.parseInt(time.trim());
					} catch (java.lang.NumberFormatException nbFormatExcep) {
						System.out.println("Problem occured while traiting stations : invalid time format \n");
						System.exit(0);
						return null;
					}
				}
				if (line.contains(ProjectUtils.STATION)) {
					String[] lineSplit = line.split(":");
					try {
						listStations.get(index).setIdStation(lineSplit[1].charAt(2));
					} catch (IndexOutOfBoundsException indexOutExcep) {
						System.out.println("Problem occured while traiting stations : invalid station value \n");
						System.exit(0);
						return null;
					}
					listStations.get(index).setTimeUnix(time);
					index++;
				}
			}

			return listStations;
		} catch (FileNotFoundException e) {
			System.out.println("Problem occured while reading the file : file not found");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Problem occured while traiting stations : /n" + e.toString());
			System.exit(0);
		}
		return null;
	}

	// get stations by customer
	public static Map<Integer, List<Station>> getStationsByCustomer(List<Station> listStations) {
		try {
			return listStations.stream().collect(Collectors.groupingBy(Station::getIdCutomer));
		} catch (Exception e) {
			System.out.println("Problem occured while getting stations by customer : /n" + e.toString());
			System.exit(0);
			return null;
		}
	}

	// get the zone from the station
	static char getZoneFromStation(char station, char stationEndPoint) {
		try {
			switch (station) {
			case ProjectUtils.STATION_A:
				return '1';
			case ProjectUtils.STATION_B:
				return '1';
			case ProjectUtils.STATION_D:
				return '2';
			case ProjectUtils.STATION_G:
				return '4';
			case ProjectUtils.STATION_H:
				return '4';
			case ProjectUtils.STATION_I:
				return '4';
			default:
				return getZone(station, stationEndPoint);
			}
		} catch (Exception e) {
			System.out.println("Problem occured while getting the zone from the station : /n" + e.toString());
			System.exit(0);
			return ' ';
		}
	}

	private static char getZone(char station, char stationEndPoint) {
		try {
			if (station != ProjectUtils.STATION_C && station != ProjectUtils.STATION_E
					&& station != ProjectUtils.STATION_F) {
				return ' ';
			}			
			String zone1_2 = "ABD";
			String zone3 = "CEF"; // Stations C,E and F are considered in zone 3 for constraints of pricing
			String zone4 = "GHI";
			String stationF = Character.toString(ProjectUtils.STATION_F);
			if (zone4.contains(String.valueOf(stationEndPoint)) && String.valueOf(station).equals(stationF)) {
				return '4';
			}
			if (zone4.contains(String.valueOf(stationEndPoint)) && !String.valueOf(station).equals(stationF)) {
				return '3';
			}
			if (zone1_2.contains(String.valueOf(stationEndPoint)) && !String.valueOf(station).equals(stationF)) {
				return '2';
			}
			if (zone1_2.contains(String.valueOf(stationEndPoint)) && String.valueOf(station).equals(stationF)) {
				return '3';
			}
			if (zone3.contains(String.valueOf(stationEndPoint))) {
				return '3';
			}
		} catch (Exception e) {
			System.out.println("Problem occured while getting the zone : /n" + e.toString());
			System.exit(0);
		}
		return ' ';
	}

}
