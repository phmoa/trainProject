package com.project.train.entities;

public class Station {

	//fields
	int idCutomer;
	char idStation;
	String timeUnix;

	//constructors
	public Station() {
		super();
	}

	public Station(int idCutomer) {
		super();
		this.idCutomer = idCutomer;
	}
    //getters & setters
	public int getIdCutomer() {
		return idCutomer;
	}

	public void setIdCutomer(int idCutomer) {
		this.idCutomer = idCutomer;
	}

	public char getIdStation() {
		return idStation;
	}

	public void setIdStation(char idStation) {
		this.idStation = idStation;
	}
	
	public String getTimeUnix() {
		return timeUnix;
	}

	public void setTimeUnix(String timeUnix) {
		this.timeUnix = timeUnix;
	}
	

}
