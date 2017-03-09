package jpastart.reserve.model2;

import jpastart.reserve.model.AlreadyAssignedCardException;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "membership_card")
public class MembershipCard2 {
    @Id
    @Column(name = "card_number")
    private String number;

    @OneToOne
    @JoinColumn(name = "user_email")
    private User2 owner;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiry_date")
    private Date expiryDate;

    private boolean enabled;

    public MembershipCard2() {
    }

    public MembershipCard2(String number, User2 owner, Date expiryDate) {
        this.number = number;
        this.owner = owner;
        this.expiryDate = expiryDate;
        this.enabled = true;
    }

    public String getNumber() {
        return number;
    }

    public User2 getOwner() {
        return owner;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void assignTo(User2 owner) {
        if (this.owner != null)
            throw new AlreadyAssignedCardException();
        this.owner = owner;
    }

    public void cacenAssignment() {
        this.owner = null;
    }

    public void disable() {
        this.enabled = false;
    }
}
