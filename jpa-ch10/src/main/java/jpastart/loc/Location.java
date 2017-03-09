package jpastart.loc;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {
    @Id
    private String id;
    private String name;

    @OneToMany
    @JoinTable(name = "loc_eng",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id")
    )
    @OrderColumn(name = "list_idx")
    private List<Engineer> engineers = new ArrayList<>();

    public Location() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Engineer> getEngineers() {
        return engineers;
    }
}
