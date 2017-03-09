package jpastart.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ent_member")
public class EntMember extends Member {
    @Column(name = "comp_id")
    private String companyId;

    public EntMember() {
    }

    public EntMember(String id, String name, String companyId) {
        super(id, name);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }
}
