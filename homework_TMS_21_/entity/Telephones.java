package by.tms.homework_TMS_21_.entity;

import java.util.Objects;

public class Telephones {
    private int id;
    private String telephoneNumber;

    public Telephones(int id, String telephone) {
        this.id = id;
        this.telephoneNumber = telephone;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telephones)) return false;
        Telephones that = (Telephones) o;
        return Objects.equals(getTelephoneNumber(), that.getTelephoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTelephoneNumber());
    }

    @Override
    public String toString() {
        return "Telephones{" +
                "telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }
}
