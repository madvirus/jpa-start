package jpastart.member;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "temp_member")
public class TempMember extends PersonalMember {
    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;

    public TempMember() {
    }

    public TempMember(String id, String name, String email, Date expireDate) {
        super(id, name, email);
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }
}
