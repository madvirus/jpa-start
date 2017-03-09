package jpastart.loc_map;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Location3 {

    @Id
    private String id;
    private String name;

    @OneToMany
    @JoinTable(name = "loc_eng3",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id")
    )
    @MapKeyColumn(name = "map_key")
    private Map<String, Engineer3> engineers = new HashMap<>();

    public Location3() {}

    public Location3(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Engineer3 getMainCharge() {
        return engineers.get("MAIN");
    }

    public Engineer3 getSubCharge() {
        return engineers.get("SUB");
    }

    public void setMainCharge(Engineer3 engineer) {
        engineers.put("MAIN", engineer);
    }

    public void setSubCharge(Engineer3 engineer) {
        engineers.put("SUB", engineer);
    }

    public void clearCharge() {
        engineers.clear();
    }

}
