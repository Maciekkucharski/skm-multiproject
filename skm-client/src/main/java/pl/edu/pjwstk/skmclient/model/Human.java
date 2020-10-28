package pl.edu.pjwstk.skmclient.model;

import java.util.Objects;

public class Human {
    String name;
    String surname;
    Stations endpoint;

    public Human(Stations endpoint) {
        this.name = "name";
        this.surname = "surname";
        this.endpoint = endpoint;
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

    public Human() {
    }

    public Stations getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Stations endpoint) {
        this.endpoint = endpoint;
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
}
