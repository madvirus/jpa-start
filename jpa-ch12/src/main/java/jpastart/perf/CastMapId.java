package jpastart.perf;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CastMapId implements Serializable {
    @Column(name = "performance_id")
    private String performanceId;
    @Column(name = "person_id")
    private String personId;

    public CastMapId() {}
    public CastMapId(String performanceId, String personId) {
        this.performanceId = performanceId;
        this.personId = personId;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public String getPersonId() {
        return personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CastMapId castMapId = (CastMapId) o;
        return Objects.equals(performanceId, castMapId.performanceId) &&
                Objects.equals(personId, castMapId.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(performanceId, personId);
    }
}
