package jpastart.reserve.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import java.util.Set;

@StaticMetamodel(User.class)
public class User_ {
    public static SingularAttribute<User, String> email;
    public static SingularAttribute<User, String> name;
    public static SingularAttribute<User, Date> createDate;
    public static SetAttribute<User, Set> keywords;

}
