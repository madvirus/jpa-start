package jpastart.member;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "personal_member")
public class PersonalMember extends Member {
    private String email;

    public PersonalMember() {
    }

    public PersonalMember(String id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
