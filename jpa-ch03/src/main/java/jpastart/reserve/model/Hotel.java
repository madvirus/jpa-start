package jpastart.reserve.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Hotel {
    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    protected Hotel() {
    }

    public Hotel(String id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }
}
