package jpastart.issue;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "issue")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type")
@DiscriminatorValue("IS")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issue_date")
    private Date issueDate;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_cp")
    private String customerCp;
    private String content;
    private boolean closed;

    public Issue() {
    }

    public Issue(Date issueDate, String customerName, String customerCp, String content) {
        this.issueDate = issueDate;
        this.customerName = customerName;
        this.customerCp = customerCp;
        this.content = content;
        this.closed = false;
    }

    public Long getId() {
        return id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerCp() {
        return customerCp;
    }

    public String getContent() {
        return content;
    }

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        this.closed = true;
    }
}
