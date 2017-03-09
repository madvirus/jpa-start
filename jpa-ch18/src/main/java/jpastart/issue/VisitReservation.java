package jpastart.issue;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("VR")
public class VisitReservation extends Issue {
    @Column(name = "assignee_emp_id")
    private String assigneeEngineerId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_date")
    private Date scheduleDate;

    public VisitReservation() {}

    public VisitReservation(Date issueDate, String customerName, String customerCp, String content,
                            String assigneeEngineerId, Date scheduleDate) {
        super(issueDate, customerName, customerCp, content);
        this.assigneeEngineerId = assigneeEngineerId;
        this.scheduleDate = scheduleDate;
    }

    public String getAssigneeEngineerId() {
        return assigneeEngineerId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void assign(String empId, Date scheduleDate) {
        this.assigneeEngineerId = empId;
        this.scheduleDate = scheduleDate;
    }
}
