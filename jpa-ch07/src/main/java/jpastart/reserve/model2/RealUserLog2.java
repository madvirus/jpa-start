package jpastart.reserve.model2;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "real_user_log2")
public class RealUserLog2 {
    @Id
    @Column(name = "review_id")
    private Long reviewId;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Review2 review;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "used_date")
    private Date usedDate;

    public RealUserLog2(Review2 review, Date usedDate) {
        this.reviewId = review.getId();
        this.review = review;
        this.usedDate = usedDate;
    }

    public RealUserLog2() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Review2 getReview() {
        return review;
    }

    public Date getUsedDate() {
        return usedDate;
    }
}
