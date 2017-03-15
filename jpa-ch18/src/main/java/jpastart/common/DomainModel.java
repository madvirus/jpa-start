package jpastart.common;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class DomainModel {
    @Id
    private String id;

    @Column(name = "crt_dtm")
    private Date creationDate;
    @Column(name = "crt_empid")
    private String creationEmpId;
    @Column(name = "crt_ip")
    private String creationIp;

    public DomainModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationEmpId() {
        return creationEmpId;
    }

    public void setCreationEmpId(String creationEmpId) {
        this.creationEmpId = creationEmpId;
    }

    public String getCreationIp() {
        return creationIp;
    }

    public void setCreationIp(String creationIp) {
        this.creationIp = creationIp;
    }
}
