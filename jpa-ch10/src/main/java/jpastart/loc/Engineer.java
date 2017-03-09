package jpastart.loc;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Engineer {
    @Id
    private String id;
    private String name;

    public Engineer() {
    }

    public Engineer(String id, String name) {
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
        Engineer engineer = (Engineer) o;
        return Objects.equals(id, engineer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
