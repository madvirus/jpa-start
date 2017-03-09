package jpastart.perf;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Performance {
    @Id
    private String id;
    private String name;

    public Performance() {
    }

    public Performance(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
