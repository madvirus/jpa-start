package jpastart.attach;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "local_file")
public class LocalFile extends AttachFile {
    private String path;

    public LocalFile() {
    }

    public LocalFile(String path) {
        this.path = path;
    }

    public LocalFile(String id, String name, Date uploadDate, String path) {
        super(id, name, uploadDate);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
