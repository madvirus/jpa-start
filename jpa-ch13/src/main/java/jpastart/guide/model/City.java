package jpastart.guide.model;

import javax.persistence.*;

@Entity
public class City {
    @Id
    @TableGenerator(name = "idgen",
            table = "id_gen",
            pkColumnName = "entity",
            pkColumnValue = "city",
            valueColumnName = "nextid",
            initialValue = 0,
            allocationSize = 1)
    @GeneratedValue(generator = "idgen")
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address.zipcode", column = @Column(name = "ct_zip")),
            @AttributeOverride(name = "address.address1", column = @Column(name = "ct_addr1")),
            @AttributeOverride(name = "address.address2", column = @Column(name = "ct_addr2"))
    })
    private ContactInfo contactInfo;

    protected City() {
    }

    public City(String name) {
        this(name, null);
    }

    public City(String name, ContactInfo contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
