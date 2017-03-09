package jpastart.issue;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("AP")
public class Appeal extends Issue {
    private String response;

    public Appeal() {}

    public Appeal(Date issueDate, String customerName, String customerCp, String content,
                  String response) {
        super(issueDate, customerName, customerCp, content);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
