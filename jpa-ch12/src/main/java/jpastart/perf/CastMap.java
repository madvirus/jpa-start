package jpastart.perf;

import javax.persistence.*;

@Entity
@Table(name = "perf_person")
public class CastMap {

    @Id
    private CastMapId id;

    @ManyToOne
    @JoinColumn(name = "performance_id", insertable = false, updatable = false)
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;

    public CastMap() {
    }

    public CastMap(Performance performance, Person person) {
        this.id = new CastMapId(performance.getId(), person.getId());
        this.performance = performance;
        this.person = person;
    }

    public CastMapId getId() {
        return id;
    }

    public Performance getPerformance() {
        return performance;
    }

    public Person getPerson() {
        return person;
    }
}
