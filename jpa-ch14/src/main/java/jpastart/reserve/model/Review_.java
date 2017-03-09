package jpastart.reserve.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Review.class)
public class Review_ {
    public static volatile SingularAttribute<Review, String> id;
    public static volatile SingularAttribute<Review, Hotel> hotel;
    public static volatile SingularAttribute<Review, Integer> rate;
    public static volatile SingularAttribute<Review, String> comment;
    public static volatile SingularAttribute<Review, Date> ratingDate;
}
