package jpastart.reserve.model2;

import jpastart.guide.model2.UserBestSight2;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User2 {

    @Id @Basic
    private String email;
    @Basic private String name;

    @Basic @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @OneToOne(mappedBy = "owner")
    private MembershipCard2 card;

    @OneToOne(mappedBy = "user")
    private UserBestSight2 bestSight;

    protected User2() {
    }

    public User2(String email, String name, Date createDate) {
        this.email = email;
        this.name = name;
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void issue(MembershipCard2 memCard) {
        memCard.assignTo(this);
        this.card = memCard;
    }

    public MembershipCard2 getCard() {
        return card;
    }

    public UserBestSight2 createBestSight(String title, String desc) {
        this.bestSight = new UserBestSight2(this, title, desc);
        return bestSight;
    }

    public UserBestSight2 getBestSight() {
        return bestSight;
    }

    public void setBestSight(UserBestSight2 bestSight) {
        this.bestSight = bestSight;
    }
}
