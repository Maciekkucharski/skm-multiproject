package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.services.DbEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "humans")
public class Human implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    String name;
    @Column(name = "surname")
    String surname;
    @Column(name = "endpoint")
    Stations endpoint;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compartment_id")
    private Compartment compartment;

    public Human(Stations endpoint, Compartment compartment) {
        this.name = "name";
        this.surname = "surname";
        this.endpoint = endpoint;
        this.compartment = compartment;
    }

    public Human(Stations endpoint) {
        this.name = "name";
        this.surname = "surname";
        this.endpoint = endpoint;
    }

    public Human() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Stations getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Stations endpoint) {
        this.endpoint = endpoint;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return endpoint == human.endpoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpoint);
    }

    @Override
    public Long getId() {
        return id;
    }
}
