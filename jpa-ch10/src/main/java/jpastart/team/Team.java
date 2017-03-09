package jpastart.team;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {
    @Id
    private String id;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="team_id")
    private Set<Player> players = new HashSet<>();

    public Team() {}

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }
}
