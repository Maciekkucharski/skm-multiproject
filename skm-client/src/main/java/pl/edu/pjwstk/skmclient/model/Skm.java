package pl.edu.pjwstk.skmclient.model;


import java.util.List;
import java.util.Random;

public class Skm {
    public int pauseCount = 0;
    public Boolean toGdynia;
    public int ID;
    public List<Compartment> compartments;
    public Stations station;
    public int capability;
    private Random random = new Random();

    public Boolean getToGdynia() {
        return toGdynia;
    }

    public void setToGdynia(Boolean toGdynia) {
        this.toGdynia = toGdynia;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public List<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(List<Compartment> compartments) {
        this.compartments = compartments;
    }

    public Stations getStation() {
        return station;
    }

    public void setStation(Stations station) {
        this.station = station;
    }

    public int getCapability() {
        return capability;
    }

    public void setCapability(int capability) {
        this.capability = capability;
    }

    public Skm(Boolean toGdynia, int ID, List<Compartment> compartments, Stations station, int capability) {
        this.toGdynia = toGdynia;
        this.ID = ID;
        this.compartments = compartments;
        this.station = station;
        this.capability = capability;
    }

    public Skm() {
    }







}
