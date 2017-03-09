package jpastart.item;

import jpastart.common.Money;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    private String id;
    private String name;
    private Money price;

    public Item() {
    }

    public Item(String id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
