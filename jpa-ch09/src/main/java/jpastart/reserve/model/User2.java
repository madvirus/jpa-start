package jpastart.reserve.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User2 {

    @Id @Basic
    private String email;
    @Basic private String name;

    @Basic @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @ElementCollection
    @CollectionTable(
            name = "user_keyword",
            joinColumns = @JoinColumn(name = "user_email"))
    @Column(name = "keyword")
    @org.hibernate.annotations.SortNatural
    //@org.hibernate.annotations.SortComparator(StringComparator.class)
    private SortedSet<String> keywords = new TreeSet<>();

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

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(SortedSet<String> keywords) {
        this.keywords = keywords;
    }
}
