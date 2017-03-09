package jpastart.guide.model;

import jpastart.reserve.model.User;

import javax.persistence.*;

@Entity
@Table(name = "user_best_sight")
public class UserBestSight {
    @Id
    @Column(name = "email")
    private String email;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    private String title;
    private String description;

    public UserBestSight() {
    }

    public UserBestSight(User user, String title, String description) {
        this.email = user.getEmail();
        this.user = user;
        this.title = title;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
