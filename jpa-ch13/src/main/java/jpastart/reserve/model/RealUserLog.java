package jpastart.reserve.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "real_user_log")
public class RealUserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "used_date")
    private Date usedDate;

    public RealUserLog(Review review, Date realUsingDate) {
        this.review = review;
        this.usedDate = realUsingDate;
    }

    public RealUserLog() {}

    public Long getId() {
        return id;
    }

    public Review getReview() {
        return review;
    }

    public Date getUsedDate() {
        return usedDate;
    }
}
