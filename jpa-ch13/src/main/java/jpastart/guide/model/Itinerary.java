package jpastart.guide.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "itinerary_site",
            joinColumns = @JoinColumn(name = "itinerary_id"))
    @OrderColumn(name = "list_idx")
    @Column(name = "site")
    private List<String> sites;

    public Itinerary() {}

    public Itinerary(String name, String description, List<String> sites) {
        this.name = name;
        this.description = description;
        this.sites = sites != null ? sites : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSites() {
        return sites;
    }

    public void changeSites(List<String> sites) {
        this.sites = sites;
    }

    public void clearSites() {
        sites.clear();
    }
}
