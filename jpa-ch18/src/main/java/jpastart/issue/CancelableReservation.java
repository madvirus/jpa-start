package jpastart.issue;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CR")
public class CancelableReservation extends VisitReservation {
    public void cancel() {
        this.assign(null, null);
    }
}
