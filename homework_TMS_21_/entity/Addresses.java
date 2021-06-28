package by.tms.homework_TMS_21_.entity;

import java.util.Objects;

public class Addresses {
    private int id;
   private String street;

    public Addresses(int id, String street ) {
        this.id=id;
        this.street = street;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Addresses{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Addresses)) return false;
        Addresses addresses = (Addresses) o;
        return Objects.equals(street, addresses.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street);
    }
}
