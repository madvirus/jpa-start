package jpastart.loc2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location2 {
    @Id
    private String id;
    private String name;

    @OneToMany
    @OrderColumn(name = "list_idx")
    @JoinColumn(name = "location_id")
    private List<Engineer2> engineers = new ArrayList<>();

    public Location2() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Engineer2> getEngineers() {
        return engineers;
    }
}
