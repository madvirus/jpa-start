package jpastart.guide.model2;

import jpastart.reserve.model2.User2;

import javax.persistence.*;

@Entity
@Table(name = "user_best_sight")
public class UserBestSight2 {
    @Id
    @Column(name = "email")
    private String email;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User2 user;

    private String title;
    private String description;

    public UserBestSight2() {
    }

    public UserBestSight2(User2 user, String title, String description) {
        this.email = user.getEmail();
        this.user = user;
        this.title = title;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public User2 getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
