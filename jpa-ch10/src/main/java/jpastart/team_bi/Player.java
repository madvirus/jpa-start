package jpastart.team_bi;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Player2")
@Table(name = "player")
public class Player {
    @Id
    @Column(name = "player_id")
    private String id;
    private String name;
    private int salary;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {
    }

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this.id == null) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
