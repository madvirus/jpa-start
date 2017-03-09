package jpastart.reserve.model;

import jpastart.common.model.Address;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@NamedNativeQuery(
        name = "Hotel.all",
        resultClass = Hotel.class,
        query = "select * from Hotel order by id asc")
@Entity
public class Hotel {
    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(
            name = "hotel_property",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    @MapKeyColumn(name = "prop_name")
    @Column(name = "prop_value")
    private Map<String, String> properties = new HashMap<>();

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

    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
