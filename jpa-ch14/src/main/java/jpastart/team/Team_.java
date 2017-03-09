package jpastart.team;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Team.class)
public class Team_ {
    public static volatile SingularAttribute<Team, String> id;
    public static volatile SingularAttribute<Team, String> name;
    public static volatile SetAttribute<Team, Player> players;
}
