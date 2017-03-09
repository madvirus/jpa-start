package jpastart.perf;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Performance {
    @Id
    private String id;
    private String name;
    @ManyToMany
    @JoinTable(name = "perf_person",
            joinColumns = @JoinColumn(name = "performance_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> cast = new HashSet<>();

    public Performance() {}
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

    public Set<Person> getCast() {
        return cast;
    }

    public void addCast(Person person) {
        this.cast.add(person);
    }

    public void removeCast(Person person) {
        this.cast.remove(person);
    }
}
