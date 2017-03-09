package jpastart.perf;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    private String id;
    private String name;

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
