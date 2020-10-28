package pl.edu.pjwstk.skmapi.model;


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

    public int countPeople() {
        int iterator = 0;
        for (Compartment compartment : compartments) {
            for (Human human : compartment.getHumans()) {
                iterator++;
            }
        }
        return iterator;
    }

    public double getPercentageOfUsage() {
        return (100.0 * countPeople() / capability);
    }


    public boolean isWaiting() {
        if (this.pauseCount == 2) {
            this.pauseCount--;
            return true;
        } else if (this.pauseCount == 1) {
            this.pauseCount--;
            if (this.toGdynia == false) {
                this.setToGdynia(true);
            } else {
                this.setToGdynia(false);
            }
            return true;
        } else
            return false;
    }

    public void moveSkm() {
        if (isWaiting()) {
            return;
        } else {
            this.station = this.station.move(this.toGdynia);

            if (this.station.isFirst() || this.station.isLast()) {
                this.pauseCount += 2;
            }
            int randAmount = random.nextInt(8 - 2) + 2;
            for (int i = 0; i < randAmount; i++) {
                this.addPassengers();
            }
            this.removePassengers();
        }
        return;
    }

    public void addPassengers() {
        for (Compartment compartment : compartments) {
            if (compartment.addHuman(new Human(this.station.randomNextStation(this)))) {
                break;
            }

        }
    }

    public void removePassengers() {
        for (int i = 0; i < compartments.size(); i++) {

            compartments.get(i).removeHuman(new Human(this.station));
        }
    }


}
