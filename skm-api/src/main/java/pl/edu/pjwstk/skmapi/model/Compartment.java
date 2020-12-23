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

    @Column(name = "limitation")
    private Integer limit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skm_id")
    private Skm skm;
    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Human> humans;

    public Skm getSkm() {
        return skm;
    }

    public void setSkm(Skm skm) {
        this.skm = skm;
    }

    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Compartment() {
    }

    public Compartment(int limit) {
        this.humans = new ArrayList<>();
        this.limit = limit;
    }

    public void removePassenger(Human human) {
        while(humans.remove(human));
    }

    public Human addPassenger(Stations station) {
        Human human = new Human(station, this);
        this.humans.add(human);
        return human;
    }

    public void removePassengers() {
        if (!humans.isEmpty())
        humans.forEach(h->removePassenger(new Human(skm.getStation())));
    }

    public int numberOfPassengers(){
        return this.getHumans().size();
    }

    @Override
    public Long getId() {
        return id;
    }
}
