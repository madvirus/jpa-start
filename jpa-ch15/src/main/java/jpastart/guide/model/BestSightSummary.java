package jpastart.guide.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("select s.id, s.name, d.hours_op as hoursOperation " +
        "from Sight s, sight_detail d " +
        "where s.id = d.sight_id"
)
@Synchronize({"Sight", "sight_detail"})
public class BestSightSummary {
    @Id
    private Long id;
    private String name;
    private String hoursOperation;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHoursOperation() {
        return hoursOperation;
    }
}
