package jpastart.reserve.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id @Basic
    private String email;
    @Basic private String name;

    @Basic @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    protected User() {
    }

    public User(String email, String name, Date createDate) {
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
}
