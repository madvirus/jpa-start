package jpastart.reserve.repository;

import jpastart.jpa.EMF;
import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.Review;

import javax.persistence.TypedQuery;
import java.util.List;

public class ReviewRepository {
    public List<Review> findByHotel(Hotel hotel, int startRow, int maxResults) {
        TypedQuery<Review> query = EMF.currentEntityManager()
                .createQuery("select r from Review r " +
                        "where r.hotel = :hotel order by r.id desc", Review.class);
        query.setParameter("hotel", hotel);
        query.setFirstResult(startRow);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }
}
