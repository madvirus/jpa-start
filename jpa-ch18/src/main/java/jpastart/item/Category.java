package jpastart.item;

import jpastart.common.DomainModel;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "creationIp", column = @Column(name = "creation_ip")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "crt_dtm"))
})
public class Category extends DomainModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
