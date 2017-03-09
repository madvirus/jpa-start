package jpastart.team;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Player.class)
public class Player_ {
    public static SingularAttribute<Player, String> id;
    public static SingularAttribute<Player, String> name;
    public static SingularAttribute<Player, Integer> salary;
    public static SingularAttribute<Player, Team> team;
}
