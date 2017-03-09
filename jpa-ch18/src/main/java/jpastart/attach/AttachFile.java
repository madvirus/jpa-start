package jpastart.attach;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attach_file")
@Inheritance(strategy = InheritanceType.JOINED)
public class AttachFile {
    @Id
    private String id;
    private String name;
    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    public AttachFile() {
    }

    public AttachFile(String id, String name, Date uploadDate) {
        this.id = id;
        this.name = name;
        this.uploadDate = uploadDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getUploadDate() {
        return uploadDate;
    }
}
