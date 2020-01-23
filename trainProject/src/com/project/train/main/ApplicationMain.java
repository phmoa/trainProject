package com.project.train.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.train.entities.Customer;
import com.project.train.entities.Station;
import com.project.train.entities.Trip;
import com.project.train.utils.ProjectUtils;
import com.project.train.utils.TripsUtils;
import com.project.train.utils.ZonesUtils;

public class ApplicationMain {

	public static void main(String args[]) {

		File inFile = null;
		try {
			if (args.length == 2) {
				inFile = new File(args[0]);
			} else {
				System.out.println("Invalid input arguments");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("Problem occured while reading the input file");
		}

		List<Station> listStations = ZonesUtils.getAllStationsInformations(inFile);
		if (listStations != null) {
			Map<Integer, List<Station>> listStationsBycustomer = ZonesUtils.getStationsByCustomer(listStations);
			if (listStationsBycustomer != null) {
				Map<Integer, List<Trip>> listtrips = TripsUtils.getListTripsByCustomer(listStationsBycustomer);
				if (listtrips != null) {
					List<Customer> listCustumers = new ArrayList<Customer>();
					Set<Integer> keyCustomer = listtrips.keySet();
					for (int key : keyCustomer) {
						List<Trip> listTrip = new ArrayList<Trip>();
						Customer c = new Customer(key, listTrip);
						int costTotal = 0;
						List<Trip> listTrips = listtrips.get(key);
						for (Trip t : listTrips) {
							c.getTrips().add(t);
							costTotal = costTotal + t.getConstInCents();
						}
						c.setTotalConstInCents(costTotal);
						listCustumers.add(c);
					}
					String result = ProjectUtils.createJsonResponse(listCustumers);
					// Create result file
					try {
						File file = new File(args[1]);
						if (file.exists()) {
							file.delete();
						}
						file.createNewFile();
						BufferedWriter writer = null;
						writer = new BufferedWriter(new FileWriter(file));
						writer.write(result);

						writer.close();
						System.out.println("Successfully finished");
					} catch (IOException ex) {
						System.out.println("Problem occured while writing the output file");
					} catch (Exception e) {
						System.out.println(e.toString());
						System.exit(0);
					}

				} else {
					System.out.println("Problem occured while getting trips by customer");
					System.exit(0);
				}
			} else {
				System.out.println("Problem occured while getting stations");
				System.exit(0);
			}
		}
	}
}
