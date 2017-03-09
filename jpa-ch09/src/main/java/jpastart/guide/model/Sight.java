package jpastart.guide.model;

import jpastart.common.model.Address;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

@Entity
@SecondaryTable(
        name = "sight_detail",
        pkJoinColumns = @PrimaryKeyJoinColumn(
                name = "sight_id",
                referencedColumnName = "id")
)
public class Sight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Embedded
    private Address korAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipcode", column = @Column(name = "eng_zipcode")),
            @AttributeOverride(name = "address1", column = @Column(name = "eng_addr1")),
            @AttributeOverride(name = "address2", column = @Column(name = "eng_addr2"))
    })
    private Address engAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name="hoursOfOperation",
                    column = @Column(name = "hours_op", table="sight_detail")),
            @AttributeOverride(name="holidays", column = @Column(table="sight_detail")),
            @AttributeOverride(name="facilities", column = @Column(table="sight_detail"))
    })
    private SightDetail detail;

    @ElementCollection
    @CollectionTable(
            name = "sight_rec_item",
            joinColumns = @JoinColumn(name = "sight_id"))
    @org.hibernate.annotations.OrderBy(clause = "name asc")
    //@javax.persistence.OrderBy("name ASC")
    private Set<RecItem> recItems;

    public Sight() {
    }

    public Sight(String name, Address korAddress, Address engAddress) {
        this.name = name;
        this.korAddress = korAddress;
        this.engAddress = engAddress;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getKorAddress() {
        return korAddress;
    }

    public Address getEngAddress() {
        return engAddress;
    }

    public SightDetail getDetail() {
        return detail;
    }

    public void setDetail(SightDetail detail) {
        this.detail = detail;
    }

    public Set<RecItem> getRecItems() {
        return recItems;
    }

    public void setRecItems(Set<RecItem> recItems) {
        this.recItems = recItems;
    }
}
