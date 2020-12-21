package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.services.DbEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "compartments")
public class Compartment implements DbEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int limit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skm_id")
    private Skm skm;
    @Transient
    public List<Human> humans;


    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
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

    public Compartment(int limit) {
        this.humans = new ArrayList<>();
        this.limit = limit;
    }


    @Override
    public Long getId() {
        return null;
    }
}
