package jpastart.guide.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class City {
    @Id
    @TableGenerator(name = "idgen",
            table = "id_gen",
            pkColumnName = "entity",
            pkColumnValue = "city",
            valueColumnName = "nextid",
            initialValue = 0,
            allocationSize = 1)
    @GeneratedValue(generator = "idgen")
    private Long id;

    private String name;

    protected City() {
    }

    public City(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
