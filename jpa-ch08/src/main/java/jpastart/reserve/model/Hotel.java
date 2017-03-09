package jpastart.reserve.model;

import jpastart.common.model.Address;

import javax.persistence.*;

@Entity
public class Hotel {
    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Embedded
    private Address address;

    protected Hotel() {
    }

    public Hotel(String id, String name, Grade grade, Address address) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public Address getAddress() {
        return address;
    }

    public void changeAddress(Address newAddress) {
        this.address = newAddress;
    }

}
