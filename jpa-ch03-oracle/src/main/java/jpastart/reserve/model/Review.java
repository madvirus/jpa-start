package jpastart.reserve.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hotel_review")
public class Review {
    @Id
    @SequenceGenerator(name = "review_seq_gen", sequenceName = "hotel_review_seq", allocationSize = 1)
    @GeneratedValue(generator = "review_seq_gen")
    private Long id;
    @Column(name = "hotel_id")
    private String hotelId;
    private int rate;
    @Column(name = "review_comment")
    private String comment;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rating_date")
    private Date ratingDate;

    protected Review() {
    }

    public Review(String hotelId, int rate, String comment, Date ratingDate) {
        this.hotelId = hotelId;
        this.rate = rate;
        this.comment = comment;
        this.ratingDate = ratingDate;
    }

    public Long getId() {
        return id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public int getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public Date getRatingDate() {
        return ratingDate;
    }
}
