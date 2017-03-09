package jpastart.reserve.model;

import jpastart.common.model.Address;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.*;

@Entity @Table(name = "hotel")
public class Hotel2 {
    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(
            name = "hotel_property2",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    @MapKeyColumn(name = "prop_name")
    private Map<String, PropValue> properties = new HashMap<>();

//    @SortNatural
//    private SortedMap<String, PropValue> properties = new TreeMap<>();

    //@org.hibernate.annotations.OrderBy(clause = "prop_name asc")
    //private Map<String, PropValue> properties = new LinedHashMap<>();

    protected Hotel2() {
    }

    public Hotel2(String id, String name, Grade grade, Address address) {
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

    public void addProperty(String name, PropValue value) {
        properties.put(name, value);
    }

    public Map<String, PropValue> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, PropValue> properties) {
        this.properties = properties;
    }
}
