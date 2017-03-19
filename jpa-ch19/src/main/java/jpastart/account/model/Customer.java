package jpastart.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Customer {
    @Id
    private String id;
    @Version
    private Integer ver;
    @Column(name = "secret_code")
    private String secretCode;

    public Customer() {}
    public Customer(String id, String secretCode) {
        this.id = id;
        this.secretCode = secretCode;
    }

    public String getId() {
        return id;
    }

    public Integer getVer() {
        return ver;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void changeSecretCode(String newSecCode) {
        this.secretCode = newSecCode;
    }
}
