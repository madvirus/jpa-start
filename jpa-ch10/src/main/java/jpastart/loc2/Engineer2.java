package jpastart.loc2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Engineer2 {
    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id", updatable = false, insertable = false)
    private Location2 location;

    public Engineer2() {
    }

    public Engineer2(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location2 getLocation() {
        return location;
    }

    public void setLocation(Location2 location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this.id == null) return false;
        Engineer2 engineer = (Engineer2) o;
        return Objects.equals(id, engineer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
