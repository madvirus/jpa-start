package jpastart.reserve.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Review.class)
public class Review_ {
    public static SingularAttribute<Review, String> id;
    public static SingularAttribute<Review, Hotel> hotel;
    public static SingularAttribute<Review, Integer> rate;
    public static SingularAttribute<Review, String> comment;
    public static SingularAttribute<Review, Date> ratingDate;
}
