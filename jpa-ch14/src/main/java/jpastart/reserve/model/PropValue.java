package jpastart.reserve.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class PropValue {
    @Column(name = "prop_value")
    private String value;
    private String type;

    public PropValue() {
    }

    public PropValue(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropValue propValue = (PropValue) o;
        return Objects.equals(value, propValue.value) &&
                Objects.equals(type, propValue.type);
    }

}
