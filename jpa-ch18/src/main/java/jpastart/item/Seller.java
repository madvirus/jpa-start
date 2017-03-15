package jpastart.item;

import jpastart.common.DomainModel;

import javax.persistence.Entity;

@Entity
public class Seller extends DomainModel {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
