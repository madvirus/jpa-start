package jpastart.perf_bi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {
    @Id
    private String id;
    private String name;

    @ManyToMany(mappedBy = "cast")
    private Set<Performance> perfs = new HashSet<>();

    public Person() {}
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Performance> getPerfs() {
        return perfs;
    }

    public void setPerfs(Set<Performance> perfs) {
        this.perfs = perfs;
    }

    public void addPerformance(Performance perf) {
        this.perfs.add(perf);
    }

    public void removePerformance(Performance perf) {
        this.perfs.remove(perf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (id == null) return false;
        Person person = (Person) o;
        return id.equals(person.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
