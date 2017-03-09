package jpastart.guide.model;

import jpastart.common.model.Address;

import javax.persistence.*;

@Embeddable
public class ContactInfo {
    @Column(name = "ct_phone")
    private String phone;
    @Column(name = "ct_email")
    private String email;

    @Embedded
    private Address address;

    public ContactInfo() {
    }

    public ContactInfo(String phone, String email, Address address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
