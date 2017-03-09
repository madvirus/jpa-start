package jpastart.guide.model;

import javax.persistence.*;
import java.util.*;

@Entity @Table(name = "itinerary")
public class Itinerary2 {
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
    @AttributeOverride(name = "site", column = @Column(name = "SITE"))
    private List<SiteInfo> sites;

    public Itinerary2() {}

    public Itinerary2(String name, String description, List<SiteInfo> sites) {
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

    public List<SiteInfo> getSites() {
        return sites;
    }

    public void changeSites(List<SiteInfo> sites) {
        this.sites = sites;
    }

    public void clearSites() {
        this.sites.clear();
    }
}
