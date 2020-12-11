package pl.edu.pjwstk.skmapi.model;

import java.util.*;

public class Simulation {
    private List<Skm> skms;
    private List<Long> skmIDs;
    private Random random = new Random();

    public Simulation(int x, int y, int z) {
        Set<Stations> startingStationsSet = new TreeSet<>();

        while (startingStationsSet.size() < x) {
            startingStationsSet.add(Stations.number[random.nextInt(Stations.numberOfEnums)]);
        }
        List<Stations> StartingStationList = convertSetToList(startingStationsSet);
        this.skmIDs = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            this.skmIDs.add(new Long(i));
        }
        this.skms = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            List<Compartment> compartments = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                compartments.add(new Compartment(z));
            }
            skms.add(new Skm(false, skmIDs.get(i), compartments, StartingStationList.get(i), y * z));
        }
    }

    public static <T> List<T> convertSetToList(Set<T> set) {
        List<T> list = new ArrayList<>();
        for (T t : set)
            list.add(t);
        return list;
    }

    public List<Skm> getSkms() {
        return skms;
    }

    public void move() {
        for (Skm skm : skms) {
            skm.moveSkm();
        }
    }

    public List<Object> getSkmsIDs() {
        List<Object> list = new ArrayList<>();
        skms.stream().forEach(skm -> list.add(skms.indexOf(skm)));
        return list;
    }

    public List<Object> getCompartments(int skmId) {
        List<Object> list = new ArrayList<>();
        Skm skm = skms.get(skmId);
        Skm chosenSkm = skms.get(skmId);
        skm.getCompartments().stream().forEach(section -> list.add(chosenSkm.getCompartments()));
        return list;
    }


}
