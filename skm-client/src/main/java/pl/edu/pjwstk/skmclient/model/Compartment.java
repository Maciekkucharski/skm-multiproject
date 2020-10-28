package pl.edu.pjwstk.skmclient.model;

import java.util.ArrayList;
import java.util.List;

public class Compartment {
    private int limit;
    public List<Human> humans;


    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
    }

    public Compartment(int limit) {
        this.humans = new ArrayList<>();
        this.limit = limit;
    }

    public boolean addHuman(Human human) {
        if (humans.size() < limit) {
            humans.add(human);
            return true;
        } else {
            return false;
        }
    }

    public void removeHuman(Human human) {
        int temp = this.humans.size();
        for (int i = 0; i < temp; i++) {
            humans.remove(human);
        }
        return;
    }

    public Compartment() {
    }
}
