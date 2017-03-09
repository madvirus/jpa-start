package jpastart.attach;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "cloud_file")
public class CloudFile extends AttachFile {
    private String provider;
    private String url;

    public CloudFile() {
    }

    public CloudFile(String id, String name, Date uploadDate, String provider, String url) {
        super(id, name, uploadDate);
        this.provider = provider;
        this.url = url;
    }

    public String getProvider() {
        return provider;
    }

    public String getUrl() {
        return url;
    }
}
