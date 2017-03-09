package jpastart.loc_map;

import jpastart.loc2.Engineer2;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Engineer3 {
    @Id
    private String id;
    private String name;

    public Engineer3() {}

    public Engineer3(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this.id == null) return false;
        Engineer3 engineer = (Engineer3) o;
        return Objects.equals(id, engineer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
