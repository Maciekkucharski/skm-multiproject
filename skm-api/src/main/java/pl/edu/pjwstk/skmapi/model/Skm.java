package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.repository.SkmRepository;
import pl.edu.pjwstk.skmapi.services.DbEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "skms")
public class Skm implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pause_count")
    private Integer pauseCount;
    @Column(name = "to_gdynia")
    private Boolean toGdynia;

    @OneToMany(mappedBy = "skm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compartment> compartments;

    @Column(name = "station")
    private Stations station;

    @Transient
    private Random random = new Random();

    public Boolean getToGdynia() {
        return toGdynia;
    }

    public void setToGdynia(Boolean toGdynia) {
        this.toGdynia = toGdynia;
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

    public void setPauseCount(Integer pauseCount) {
        this.pauseCount = pauseCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPauseCount() {
        return pauseCount;
    }

    @Override
    public Long getId() {
        return id;
    }


    public Skm(Boolean toGdynia, Long ID, List<Compartment> compartments, Stations station) {
        this.toGdynia = toGdynia;
        this.id = ID;
        this.compartments = compartments;
        this.station = station;
    }

    public Skm() {
    }

    public int getCapacity() {
        int sum = 0;
        for (Compartment compartment : compartments) {
            sum += compartment.getLimit();
        }
        return sum;
    }

    public int countPeople() {
        int sum = 0;
        for (Compartment compartment : compartments) {
            sum += compartment.numberOfPassengers();
        }
        return sum;
    }

    public double getPercentageOfUsage() {
        return (100.0 * countPeople() / getCapacity());
    }

    public boolean isWaiting(SkmRepository skmRepository, Skm skm) {
        if (this.pauseCount == 2) {
            this.pauseCount--;
            skm.setPauseCount(this.pauseCount);
            skmRepository.save(skm);
            return true;
        } else if (this.pauseCount == 1) {
            this.pauseCount--;
            skm.setPauseCount(this.pauseCount);
            skmRepository.save(skm);
            if (this.toGdynia == false) {
                this.setToGdynia(true);
                skm.setToGdynia(this.toGdynia);
                skmRepository.save(skm);
            } else {
                this.setToGdynia(false);
                skm.setToGdynia(this.toGdynia);
                skmRepository.save(skm);
            }
            return true;
        } else
            return false;
    }

    public void moveSkm(SkmRepository skmRepository, Long skmId) {
        Skm currentSkm = skmRepository.findById(skmId).orElse(null);
        if (isWaiting(skmRepository, currentSkm)) {
            return;
        } else {
            this.station = this.station.move(this.toGdynia);
            currentSkm.setStation(station);
            skmRepository.save(currentSkm);
            if (this.station.isFirst() || this.station.isLast()) {
                this.pauseCount += 2;
                currentSkm.setPauseCount(pauseCount);
                skmRepository.save(currentSkm);
                return;
            }
        }
    }

    public Compartment getFirstFreeCompartment() {
        for (Compartment compartment : this.getCompartments()) {
            if (compartment.humans.size() < compartment.getLimit()) {
                return compartment;
            }
        }
        return null;
    }

}
