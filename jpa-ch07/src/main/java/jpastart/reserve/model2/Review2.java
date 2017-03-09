package jpastart.reserve.model2;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hotel_review2")
public class Review2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hotel_id")
    private String hotelId;
    private int rate;
    private String comment;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rating_date")
    private Date ratingDate;

    protected Review2() {
    }

    public Review2(String hotelId, int rate, String comment, Date ratingDate) {
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
