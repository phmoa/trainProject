package com.project.train.entities;

import java.util.List;

public class Customer {
	
	//fields
	int customerId;
	int totalConstInCents;
	List<Trip> trips;
	char station;
	
	//constructors
	public Customer() {
		super();
	}

	public Customer(int customerId, List<Trip> trips) {
		super();
		this.customerId = customerId;
		this.trips = trips;
	}

	//getters & setters
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getTotalConstInCents() {
		return totalConstInCents;
	}

	public void setTotalConstInCents(int totalConstInCents) {
		this.totalConstInCents = totalConstInCents;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public char getStation() {
		return station;
	}

	public void setStation(char station) {
		this.station = station;
	}
	
	
}
